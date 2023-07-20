package travel.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel.base.service.impl.BaseServiceImpl;
import travel.base.utils.ConvertUtils;
import travel.dto.CommentDto;
import travel.dto.PackagesDto;
import travel.dto.ScenicDto;
import travel.entity.CommentEntity;
import travel.entity.PackagesDetailedEntity;
import travel.entity.PackagesEntity;
import travel.entity.ScenicEntity;
import travel.exception.HttpException;
import travel.mapper.PackagesMapper;
import travel.service.CommentService;
import travel.service.PackagesDetailedService;
import travel.service.PackagesService;
import travel.service.ScenicService;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 套餐 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Service
public class PackagesServiceImpl extends BaseServiceImpl<PackagesMapper, PackagesEntity> implements PackagesService {
    @Autowired
    private PackagesDetailedService packagesDetailedService;
    @Autowired
    private ScenicService scenicService;
    @Autowired
    private CommentService commentService;

    @Override
    public QueryWrapper<PackagesEntity> queryWrapper(Map<String, Object> params) {
        QueryWrapper<PackagesEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(!StrUtil.isBlankIfStr(params.get("name")), PackagesEntity::getName, params.get("name"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("status")), PackagesEntity::getStatus, params.get("status"));
        return wrapper;
    }

    @Override
    public void handleList(List<PackagesEntity> list, Map<String, Object> params) {
        //套餐id集合
        List<Long> packagesIdList = list.stream().map(PackagesEntity::getId).collect(Collectors.toList());
        if (!packagesIdList.isEmpty()) {
            //套餐id-评论id
            Map<Long, List<CommentEntity>> commentEntityMap = commentService.lambdaQuery().eq(CommentEntity::getType, 2).in(CommentEntity::getItemId, packagesIdList)
                    .list().stream().collect(Collectors.groupingBy(CommentEntity::getItemId, Collectors.toList()));
            //套餐id-景点id
            Map<Long, List<Long>> packagesScenicIdMap = packagesDetailedService.lambdaQuery().in(PackagesDetailedEntity::getPackagesId, packagesIdList).list().stream()
                    .collect(Collectors.groupingBy(PackagesDetailedEntity::getPackagesId, Collectors.mapping(PackagesDetailedEntity::getScenicId, Collectors.toList())));
            //景点id集合
            List<Long> scenicIdList = packagesScenicIdMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
            if (!scenicIdList.isEmpty()) {
                //景点id-景点
                Map<Long, ScenicEntity> scenicEntityMap = scenicService.lambdaQuery().in(ScenicEntity::getId, scenicIdList).list()
                        .stream().collect(Collectors.toMap(ScenicEntity::getId, Function.identity()));
                //套餐id-景点集合
                Map<Long, List<ScenicEntity>> packagesScenicEntityMap = new HashMap<>();
                packagesScenicIdMap.forEach((key, value) -> {
                    //景点集合
                    List<ScenicEntity> scenicEntityList = new ArrayList<>();
                    if (!value.isEmpty()) {
                        value.parallelStream().forEachOrdered(id -> {
                            if (scenicEntityMap.containsKey(id)) {
                                scenicEntityList.add(scenicEntityMap.get(id));
                            }
                        });
                    }
                    packagesScenicEntityMap.put(key, scenicEntityList);
                });
                //添加套餐景点和评论
                list.parallelStream().forEachOrdered(entity -> {
                    //评论
                    if (commentEntityMap.containsKey(entity.getId())) {
                        List<CommentEntity> commentEntityList = commentEntityMap.get(entity.getId());
                        entity.setCommentList(ConvertUtils.sourceToTarget(commentEntityList, CommentDto.class));
                        int sum = commentEntityList.stream().mapToInt(CommentEntity::getScore).sum();
                        entity.setScore(sum / commentEntityList.size());
                    } else {
                        entity.setCommentList(new ArrayList<>());
                        entity.setScore(5);
                    }
                    //景点
                    if (packagesScenicEntityMap.containsKey(entity.getId())) {
                        List<ScenicEntity> scenicEntityList = packagesScenicEntityMap.get(entity.getId());
                        entity.setScenicList(ConvertUtils.sourceToTarget(scenicEntityList, ScenicDto.class));
                    } else {
                        entity.setScenicList(new ArrayList<>());
                    }
                    entity.setScenicIdList(packagesScenicIdMap.containsKey(entity.getId()) ? packagesScenicIdMap.get(entity.getId()) : new ArrayList<>());
                });
            }
        }
    }

    @Override
    public PackagesEntity queryInfo(Long id) {
        PackagesEntity entity = this.getById(id);
        List<CommentEntity> commentEntityList = commentService.lambdaQuery().eq(CommentEntity::getType, 2).eq(CommentEntity::getItemId, entity.getId()).list();
        List<Long> scenicIdList = packagesDetailedService.lambdaQuery().eq(PackagesDetailedEntity::getPackagesId, id).list().stream().map(PackagesDetailedEntity::getScenicId).collect(Collectors.toList());
        if (!commentEntityList.isEmpty()) {
            entity.setCommentList(ConvertUtils.sourceToTarget(commentEntityList, CommentDto.class));
            int sum = commentEntityList.stream().mapToInt(CommentEntity::getScore).sum();
            entity.setScore(sum / commentEntityList.size());
        } else {
            entity.setCommentList(new ArrayList<>());
            entity.setScore(5);
        }
        if (!scenicIdList.isEmpty()) {
            List<ScenicEntity> scenicEntityList = scenicService.lambdaQuery().in(ScenicEntity::getId, scenicIdList).list();
            entity.setScenicList(ConvertUtils.sourceToTarget(scenicEntityList, ScenicDto.class));
        } else {
            entity.setScenicList(new ArrayList<>());
        }
        entity.setScenicIdList(!scenicIdList.isEmpty() ? scenicIdList : new ArrayList<>());
        return entity;
    }

    @Override
    public PackagesEntity insert(PackagesDto packagesDto) {
        PackagesEntity entity = lambdaQuery()
                .eq(PackagesEntity::getName, packagesDto.getName())
                .one();
        if (entity != null) {
            throw new HttpException("新增数据失败！！！");
        }
        entity = ConvertUtils.sourceToTarget(packagesDto, PackagesEntity.class);
        this.save(entity);
        packagesDetailedService.insert(entity.getId(), packagesDto.getScenicIdList());
        return this.queryInfo(entity.getId());
    }

    @Override
    public PackagesEntity update(PackagesDto packagesDto) {
        PackagesEntity entity = this.queryInfo(packagesDto.getId());
        if (entity == null) {
            throw new HttpException("更新数据失败！！！");
        }
        BeanUtils.copyProperties(packagesDto, entity, "id");
        this.updateById(entity);
        packagesDetailedService.insert(entity.getId(), packagesDto.getScenicIdList());
        return entity;
    }

    @Override
    public void deleteBatch(List<Long> idList) {
        this.removeByIds(idList);
    }
}

package travel.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel.base.service.impl.BaseServiceImpl;
import travel.base.utils.ConvertUtils;
import travel.dto.CommentDto;
import travel.dto.ScenicDto;
import travel.entity.CommentEntity;
import travel.entity.PackagesDetailedEntity;
import travel.entity.PackagesEntity;
import travel.entity.ScenicEntity;
import travel.exception.HttpException;
import travel.mapper.ScenicMapper;
import travel.service.CommentService;
import travel.service.PackagesDetailedService;
import travel.service.ScenicService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 景点 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Service
public class ScenicServiceImpl extends BaseServiceImpl<ScenicMapper, ScenicEntity> implements ScenicService {
    @Autowired
    private PackagesDetailedService packagesDetailedService;
    @Autowired
    private CommentService commentService;

    @Override
    public QueryWrapper<ScenicEntity> queryWrapper(Map<String, Object> params) {
        QueryWrapper<ScenicEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(!StrUtil.isBlankIfStr(params.get("name")), ScenicEntity::getName, params.get("name"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("status")), ScenicEntity::getStatus, params.get("status"));
        return wrapper;
    }

    @Override
    public void handleList(List<ScenicEntity> list, Map<String, Object> params) {
        //景点id集合
        List<Long> scenicIdList = list.stream().map(ScenicEntity::getId).collect(Collectors.toList());
        if (!scenicIdList.isEmpty()) {
            //景点id-评论id
            Map<Long, List<CommentEntity>> commentEntityMap = commentService.lambdaQuery().eq(CommentEntity::getType, 1).in(CommentEntity::getItemId, scenicIdList)
                    .list().stream().collect(Collectors.groupingBy(CommentEntity::getItemId, Collectors.toList()));
            //添加景点评论
            list.parallelStream().forEachOrdered(entity -> {
                if (commentEntityMap.containsKey(entity.getId())) {
                    List<CommentEntity> commentEntityList = commentEntityMap.get(entity.getId());
                    entity.setCommentList(ConvertUtils.sourceToTarget(commentEntityList, CommentDto.class));
                    int sum = commentEntityList.stream().mapToInt(CommentEntity::getScore).sum();
                    entity.setScore(sum / commentEntityList.size());
                } else {
                    entity.setCommentList(new ArrayList<>());
                    entity.setScore(5);
                }
            });
        }
    }

    @Override
    public ScenicEntity queryInfo(Long id) {
        ScenicEntity entity = this.getById(id);
        List<CommentEntity> commentEntityList = commentService.lambdaQuery().eq(CommentEntity::getType, 1).eq(CommentEntity::getItemId, entity.getId()).list();
        if (!commentEntityList.isEmpty()) {
            entity.setCommentList(ConvertUtils.sourceToTarget(commentEntityList, CommentDto.class));
            int sum = commentEntityList.stream().mapToInt(CommentEntity::getScore).sum();
            entity.setScore(sum / commentEntityList.size());
        } else {
            entity.setCommentList(new ArrayList<>());
            entity.setScore(5);
        }
        return entity;
    }

    @Override
    public ScenicEntity insert(ScenicDto scenicDto) {
        ScenicEntity entity = lambdaQuery()
                .eq(ScenicEntity::getName, scenicDto.getName())
                .one();
        if (entity != null) {
            throw new HttpException("新增数据失败！！！");
        }
        entity = ConvertUtils.sourceToTarget(scenicDto, ScenicEntity.class);
        this.save(entity);
        return this.queryInfo(entity.getId());
    }

    @Override
    public ScenicEntity update(ScenicDto scenicDto) {
        ScenicEntity entity = this.queryInfo(scenicDto.getId());
        if (entity == null) {
            throw new HttpException("更新数据失败！！！");
        }
        BeanUtils.copyProperties(scenicDto, entity, "id");
        this.updateById(entity);
        return entity;
    }

    @Override
    public void deleteBatch(List<Long> idList) {
        int size = packagesDetailedService.lambdaQuery().in(PackagesDetailedEntity::getScenicId, idList).list().size();
        if (size > 0) {
            throw new HttpException("套餐中存在该景点！！！");
        }
        this.removeByIds(idList);
    }
}

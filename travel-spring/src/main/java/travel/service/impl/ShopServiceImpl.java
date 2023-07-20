package travel.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel.base.service.impl.BaseServiceImpl;
import travel.base.utils.ConvertUtils;
import travel.dto.ShopDto;
import travel.dto.UserDto;
import travel.entity.*;
import travel.exception.HttpException;
import travel.mapper.ShopMapper;
import travel.service.PackagesService;
import travel.service.ScenicService;
import travel.service.ShopService;
import travel.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Service
public class ShopServiceImpl extends BaseServiceImpl<ShopMapper, ShopEntity> implements ShopService {
    @Autowired
    private UserService userService;
    @Autowired
    private PackagesService packagesService;
    @Autowired
    private ScenicService scenicService;

    @Override
    public QueryWrapper<ShopEntity> queryWrapper(Map<String, Object> params) {
        QueryWrapper<ShopEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("type")), ShopEntity::getType, params.get("type"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("itemId")), ShopEntity::getItemId, params.get("itemId"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("userId")), ShopEntity::getUserId, params.get("userId"));
        wrapper.lambda().orderByDesc(ShopEntity::getUpdatedTime);
        return wrapper;
    }

    @Override
    public void handleList(List<ShopEntity> list, Map<String, Object> params) {
        List<Long> userId = list.stream().map(ShopEntity::getUserId).collect(Collectors.toList());
        Map<Long, UserEntity> userEntityMap = userId.isEmpty() ? new HashMap<>() : userService.lambdaQuery().in(UserEntity::getId, userId).list().stream()
                .collect(Collectors.toMap(UserEntity::getId, Function.identity()));
        List<Long> scenicIdList = list.stream().filter(entity -> entity.getType() == 1).map(ShopEntity::getItemId).collect(Collectors.toList());
        Map<Long, ScenicEntity> scenicEntityMap = scenicIdList.isEmpty() ? new HashMap<>() : scenicService.lambdaQuery().in(ScenicEntity::getId, scenicIdList).list().stream()
                .collect(Collectors.toMap(ScenicEntity::getId, Function.identity()));
        List<Long> packagesIdList = list.stream().filter(entity -> entity.getType() == 2).map(ShopEntity::getItemId).collect(Collectors.toList());
        Map<Long, PackagesEntity> packagesEntityMap = packagesIdList.isEmpty() ? new HashMap<>() : packagesService.lambdaQuery().in(PackagesEntity::getId, packagesIdList).list().stream()
                .collect(Collectors.toMap(PackagesEntity::getId, Function.identity()));
        list.parallelStream().forEachOrdered(entity -> {
            if(userEntityMap.containsKey(entity.getUserId())){
                UserEntity userEntity = userEntityMap.get(entity.getUserId());
                entity.setUserInfo(ConvertUtils.sourceToTarget(userEntity, UserDto.class));
            }
            if (entity.getType() == 1 && scenicEntityMap.containsKey(entity.getItemId())) {
                entity.setItemInfo(scenicEntityMap.get(entity.getItemId()));
            } else if (entity.getType() == 2 && packagesEntityMap.containsKey(entity.getItemId())) {
                entity.setItemInfo(packagesEntityMap.get(entity.getItemId()));
            } else {
                entity.setItemInfo(new Object());
            }
        });
    }

    @Override
    public ShopEntity queryInfo(Long id) {
        ShopEntity entity = this.getById(id);
        UserEntity userEntity = userService.lambdaQuery().eq(UserEntity::getId, entity.getUserId()).one();
        entity.setUserInfo(ConvertUtils.sourceToTarget(userEntity, UserDto.class));
        if (entity.getType() == 1) {
            entity.setItemInfo(scenicService.lambdaQuery().eq(ScenicEntity::getId, entity.getItemId()).one());
        } else if (entity.getType() == 2) {
            entity.setItemInfo(packagesService.lambdaQuery().eq(PackagesEntity::getId, entity.getItemId()).one());
        }
        return entity;
    }

    @Override
    public ShopEntity insert(ShopDto shopDto) {
        ShopEntity entity = lambdaQuery()
                .eq(ShopEntity::getUserId, shopDto.getUserId())
                .eq(ShopEntity::getItemId, shopDto.getItemId())
                .eq(ShopEntity::getType, shopDto.getType())
                .one();
        if (entity != null) {
            throw new HttpException("已添加过购物车！！！");
        }
        entity = ConvertUtils.sourceToTarget(shopDto, ShopEntity.class);
        this.save(entity);
        return this.queryInfo(entity.getId());
    }

    @Override
    public ShopEntity update(ShopDto shopDto) {
        ShopEntity entity = this.queryInfo(shopDto.getId());
        if (entity == null) {
            throw new HttpException("更新数据失败！！！");
        }
        BeanUtils.copyProperties(shopDto, entity, "id");
        this.updateById(entity);
        return entity;
    }

    @Override
    public void deleteBatch(List<Long> idList) {
        this.removeByIds(idList);
    }
}

package travel.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel.base.service.impl.BaseServiceImpl;
import travel.base.utils.ConvertUtils;
import travel.dto.OrderDetailedDto;
import travel.dto.OrderDto;
import travel.dto.UserDto;
import travel.entity.*;
import travel.exception.HttpException;
import travel.mapper.OrderMapper;
import travel.service.OrderService;
import travel.service.PackagesService;
import travel.service.ScenicService;
import travel.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-03-29
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, OrderEntity> implements OrderService {
    @Autowired
    private UserService userService;
    @Autowired
    private PackagesService packagesService;
    @Autowired
    private ScenicService scenicService;

    @Override
    public QueryWrapper<OrderEntity> queryWrapper(Map<String, Object> params) {
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(!StrUtil.isBlankIfStr(params.get("num")), OrderEntity::getNum, params.get("num"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("type")), OrderEntity::getType, params.get("type"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("status")), OrderEntity::getStatus, params.get("status"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("itemId")), OrderEntity::getItemId, params.get("itemId"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("userId")), OrderEntity::getUserId, params.get("userId"));
        wrapper.lambda().orderByDesc(OrderEntity::getUpdatedTime);
        return wrapper;
    }

    @Override
    public void handleList(List<OrderEntity> list, Map<String, Object> params) {
        List<Long> userIdList = list.stream().map(OrderEntity::getUserId).collect(Collectors.toList());
        Map<Long, UserEntity> userEntityMap = userIdList.isEmpty() ? new HashMap<>() : userService.lambdaQuery().in(UserEntity::getId, userIdList).list().stream()
                .collect(Collectors.toMap(UserEntity::getId, Function.identity()));
        List<Long> scenicIdList = list.stream().filter(entity -> entity.getType() == 1).map(OrderEntity::getItemId).collect(Collectors.toList());
        Map<Long, ScenicEntity> scenicEntityMap = scenicIdList.isEmpty() ? new HashMap<>() : scenicService.lambdaQuery().in(ScenicEntity::getId, scenicIdList).list().stream()
                .collect(Collectors.toMap(ScenicEntity::getId, Function.identity()));
        List<Long> packagesIdList = list.stream().filter(entity -> entity.getType() == 2).map(OrderEntity::getItemId).collect(Collectors.toList());
        Map<Long, PackagesEntity> packagesEntityMap = packagesIdList.isEmpty() ? new HashMap<>() : packagesService.lambdaQuery().in(PackagesEntity::getId, packagesIdList).list().stream()
                .collect(Collectors.toMap(PackagesEntity::getId, Function.identity()));
        list.parallelStream().forEachOrdered(entity -> {
            if(userEntityMap.containsKey(entity.getUserId())){
                UserEntity userEntity = userEntityMap.get(entity.getUserId());
                entity.setUserInfo(ConvertUtils.sourceToTarget(userEntity, UserDto.class));
                entity.setUsername(userEntity.getUsername());
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
    public OrderEntity queryInfo(Long id) {
        OrderEntity entity = this.getById(id);
        UserEntity userEntity = userService.lambdaQuery().eq(UserEntity::getId, entity.getUserId()).one();
        entity.setUserInfo(ConvertUtils.sourceToTarget(userEntity, UserDto.class));
        entity.setUsername(userEntity.getUsername());
        if (entity.getType() == 1) {
            entity.setItemInfo(scenicService.lambdaQuery().eq(ScenicEntity::getId, entity.getItemId()).one());
        } else if (entity.getType() == 2) {
            entity.setItemInfo(packagesService.lambdaQuery().eq(PackagesEntity::getId, entity.getItemId()).one());
        }
        return entity;
    }

    @Override
    public OrderEntity insert(OrderDto orderDto) {
        OrderEntity entity = lambdaQuery()
                .eq(OrderEntity::getNum, orderDto.getNum())
                .one();
        if (entity != null) {
            throw new HttpException("新增数据失败！！！");
        }
        entity = ConvertUtils.sourceToTarget(orderDto, OrderEntity.class);
        LocalDateTime date = LocalDateTime.now();
        entity.setNum("DD" + date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + date.toInstant(ZoneOffset.ofHours(8)).toEpochMilli() % 10000);
        List<OrderDetailedDto> statusList = new ArrayList<>();
        statusList.add(new OrderDetailedDto(1, "买家下单", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        //模拟卖家确认
        entity.setStatus(2);
        statusList.add(new OrderDetailedDto(2, "卖家确认", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        entity.setStatusList(statusList);
        this.save(entity);
        return this.queryInfo(entity.getId());
    }

    @Override
    public OrderEntity update(OrderDto orderDto) {
        OrderEntity entity = this.queryInfo(orderDto.getId());
        if (entity == null) {
            throw new HttpException("更新数据失败！！！");
        }
        BeanUtils.copyProperties(orderDto, entity, "id");
        List<OrderDetailedDto> statusList = entity.getStatusList();
        switch (orderDto.getStatus()) {
            case 3:
                statusList.add(new OrderDetailedDto(3, "买家签收", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                break;
            case 4:
                statusList.add(new OrderDetailedDto(4, "买家评价", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                //提交评论，订单完成
                entity.setStatus(5);
                statusList.add(new OrderDetailedDto(5, "交易完成", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                break;
            case 0:
                statusList.add(new OrderDetailedDto(0, "交易取消", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + orderDto.getStatus());
        }
        entity.setStatusList(statusList);
        this.updateById(entity);
        return entity;
    }

    @Override
    public void deleteBatch(List<Long> idList) {
        this.removeByIds(idList);
    }
}

package travel.service;

import travel.base.service.BaseService;
import travel.dto.OrderDto;
import travel.entity.OrderEntity;

import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author wang
 * @since 2023-03-29
 */
public interface OrderService extends BaseService<OrderEntity> {
    /**
     * 查询详情
     *
     * @param id 数据id
     */
    OrderEntity queryInfo(Long id);

    /**
     * 新增
     *
     * @param orderDto 数据
     */
    OrderEntity insert(OrderDto orderDto);

    /**
     * 修改
     *
     * @param orderDto 数据
     */
    OrderEntity update(OrderDto orderDto);

    /**
     * 删除
     *
     * @param idList 数据id
     */
    void deleteBatch(List<Long> idList);
}

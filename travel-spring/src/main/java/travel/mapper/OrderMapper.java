package travel.mapper;

import org.apache.ibatis.annotations.Mapper;
import travel.base.mapper.BaseMapper;
import travel.entity.OrderEntity;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2023-04-18
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {

}

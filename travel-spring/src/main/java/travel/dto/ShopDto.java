package travel.dto;

import lombok.Data;
import travel.entity.UserEntity;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author wang
 * @since 2023-03-27
 */
@Data
public class ShopDto {
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 景点/套餐id
     */
    private Long itemId;

    /**
     * 订单类型，1景点，2套餐
     */
    private Integer type;

    /**
     * 类型名称
     */
    private String typeText;

    /**
     * 用户详情
     */
    private UserEntity userInfo;

    /**
     * 景点/套餐详情
     */
    private Object itemInfo;
}

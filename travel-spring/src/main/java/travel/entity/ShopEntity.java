package travel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import travel.dict.annotation.DataDict;
import travel.base.entity.BaseEntity;
import travel.dto.UserDto;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("travel_shop")
public class ShopEntity extends BaseEntity {

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 景点/套餐id
     */
    @TableField("item_id")
    private Long itemId;

    /**
     * 订单类型，1景点，2套餐
     */
    @TableField("type")
    @DataDict(code = "order_type", target = "typeText")
    private Integer type;

    /**
     * 类型名称
     */
    @TableField(exist = false)
    private String typeText;

    /**
     * 用户详情
     */
    @TableField(exist = false)
    private UserDto userInfo;

    /**
     * 景点/套餐详情
     */
    @TableField(exist = false)
    private Object itemInfo;
}

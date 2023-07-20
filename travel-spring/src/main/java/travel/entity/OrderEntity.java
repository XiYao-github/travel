package travel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import travel.dict.annotation.DataDict;
import travel.base.entity.BaseEntity;
import travel.dto.OrderDetailedDto;
import travel.dto.UserDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author wang
 * @since 2023-03-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "travel_order", autoResultMap = true)
public class OrderEntity extends BaseEntity {

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String username;

    /**
     * 景点/套餐id
     */
    @TableField("item_id")
    private Long itemId;

    /**
     * 订单编号
     */
    @TableField("num")
    private String num;

    /**
     * 订单价格
     */
    @TableField("price")
    private BigDecimal price;

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
     * 订单状态，1买家下单，2卖家确认，3买家签收，4买家评价，5交易完成，0交易取消
     */
    @TableField("status")
    @DataDict(code = "order_status", target = "statusText")
    private Integer status;

    /**
     * 订单状态详情
     */
    @TableField(value = "status_list", typeHandler = FastjsonTypeHandler.class)
    private List<OrderDetailedDto> statusList;

    /**
     * 状态名称
     */
    @TableField(exist = false)
    private String statusText;

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

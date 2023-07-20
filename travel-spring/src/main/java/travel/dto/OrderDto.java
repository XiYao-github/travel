package travel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author wang
 * @since 2023-03-27
 */
@Data
public class OrderDto {
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 景点/套餐id
     */
    private Long itemId;

    /**
     * 订单编号
     */
    private String num;

    /**
     * 订单价格
     */
    private BigDecimal price;

    /**
     * 订单类型，1景点，2套餐
     */
    private Integer type;

    /**
     * 类型名称
     */
    private String typeText;

    /**
     * 订单状态，1买家下单，2卖家确认，3买家签收，4买家评价，5交易完成，0交易取消
     */
    private Integer status;

    /**
     * 订单状态详情
     */
    private List<OrderDetailedDto> statusList;

    /**
     * 状态名称
     */
    private String statusText;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    /**
     * 用户详情
     */
    private UserDto userInfo;

    /**
     * 景点/套餐详情
     */
    private Object itemInfo;

}

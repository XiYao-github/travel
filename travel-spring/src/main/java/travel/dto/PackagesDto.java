package travel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 套餐
 * </p>
 *
 * @author wang
 * @since 2023-03-27
 */
@Data
public class PackagesDto {
    private Long id;
    /**
     * 套餐名称
     */
    private String name;

    /**
     * 套餐描述
     */
    private String description;

    /**
     * 套餐区域
     */
    private String address;

    /**
     * 套餐价格
     */
    private BigDecimal price;

    /**
     * 套餐评分
     */
    private Integer score;

    /**
     * 套餐图片
     */
    private List<String> image;

    /**
     * 套餐状态，0禁用，1正常
     */
    private Integer status;

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
     * 套餐景点id
     */
    private List<Long> scenicIdList;

    /**
     * 套餐详情
     */
    private List<ScenicDto> scenicList;

    /**
     * 评论详情
     */
    private List<CommentDto> commentList;
}

package travel.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 景点
 * </p>
 *
 * @author wang
 * @since 2023-03-27
 */
@Data
public class ScenicDto {
    private Long id;
    /**
     * 景点名称
     */
    private String name;

    /**
     * 景点描述
     */
    private String description;

    /**
     * 景点地址
     */
    private String address;

    /**
     * 景点价格
     */
    private BigDecimal price;

    /**
     * 景点评分
     */
    private Integer score;

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
     * 景点图片
     */
    private List<String> image;

    /**
     * 景点状态，0禁用，1正常
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusText;

    /**
     * 评论详情
     */
    private List<CommentDto> commentList;
}

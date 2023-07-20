package travel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 评价
 * </p>
 *
 * @author wang
 * @since 2023-03-27
 */
@Data
public class CommentDto {
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 作者名称
     */
    private String username;

    /**
     * 景点/套餐/游记id
     */
    private Long itemId;

    /**
     * 评价类型，1景点，2套餐，3游记
     */
    private Integer type;

    /**
     * 类型名称
     */
    private String typeText;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评分
     */
    private Integer score;

    /**
     * 评价图片
     */
    private List<String> image;

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
}

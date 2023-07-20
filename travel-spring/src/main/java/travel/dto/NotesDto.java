package travel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 游记
 * </p>
 *
 * @author wang
 * @since 2023-03-27
 */
@Data
public class NotesDto {
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
     * 游记标题
     */
    private String title;

    /**
     * 游记内容
     */
    private List<NotesDetailedDto> content;

    /**
     * 游记图片
     */
    private List<String> image;

    /**
     * 游记状态，1未审核，2已审核
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
     * 用户详情
     */
    private UserDto userInfo;
}

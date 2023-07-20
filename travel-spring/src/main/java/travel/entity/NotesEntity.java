package travel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import travel.dict.annotation.DataDict;
import travel.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import travel.dto.NotesDetailedDto;
import travel.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 游记
 * </p>
 *
 * @author wang
 * @since 2023-03-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "travel_notes", autoResultMap = true)
public class NotesEntity extends BaseEntity {

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 作者名称
     */
    @TableField(exist = false)
    private String username;

    /**
     * 游记标题
     */
    @TableField("title")
    private String title;

    /**
     * 游记内容
     */
    @TableField(value = "content", typeHandler = FastjsonTypeHandler.class)
    private List<NotesDetailedDto> content;

    /**
     * 游记图片
     */
    @TableField(value = "image", typeHandler = FastjsonTypeHandler.class)
    private List<String> image;

    public void setImage(List<String> image) {
        this.image = image != null && !image.isEmpty() ? image : new ArrayList<>();
    }

    /**
     * 游记状态，1未审核，2已审核
     */
    @TableField("status")
    @DataDict(code = "notes_status", target = "statusText")
    private Integer status;

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

}

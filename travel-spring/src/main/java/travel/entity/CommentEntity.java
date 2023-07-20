package travel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import travel.dict.annotation.DataDict;
import travel.base.entity.BaseEntity;
import travel.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评价
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "travel_comment", autoResultMap = true)
public class CommentEntity extends BaseEntity {

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
     * 景点/套餐/游记id
     */
    @TableField("item_id")
    private Long itemId;

    /**
     * 评价类型，1景点，2套餐，3游记
     */
    @TableField("type")
    @DataDict(code = "comment_type", target = "typeText")
    private Integer type;

    /**
     * 类型名称
     */
    @TableField(exist = false)
    private String typeText;

    /**
     * 评价内容
     */
    @TableField("content")
    private String content;

    //public void setContent(String content) {
    //    this.content = content != null && !content.isEmpty() ? content : "";
    //}

    /**
     * 评分
     */
    @TableField("score")
    private Integer score;

    /**
     * 评价图片
     */
    @TableField(value = "image", typeHandler = FastjsonTypeHandler.class)
    private List<String> image;

    public void setImage(List<String> image) {
        this.image = image != null && !image.isEmpty() ? image : new ArrayList<>();
    }

    /**
     * 用户详情
     */
    @TableField(exist = false)
    private UserDto userInfo;


}

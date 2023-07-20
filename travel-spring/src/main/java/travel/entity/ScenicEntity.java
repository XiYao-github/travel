package travel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import travel.dict.annotation.DataDict;
import travel.base.entity.BaseEntity;
import travel.dto.CommentDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 景点
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "travel_scenic", autoResultMap = true)
public class ScenicEntity extends BaseEntity {

    /**
     * 景点名称
     */
    @TableField("name")
    private String name;

    /**
     * 景点描述
     */
    @TableField("description")
    private String description;

    /**
     * 景点地址
     */
    @TableField("address")
    private String address;

    /**
     * 景点价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 景点评分
     */
    @TableField(exist = false)
    private Integer score;

    /**
     * 景点图片
     */
    @TableField(value = "image", typeHandler = FastjsonTypeHandler.class)
    private List<String> image;

    public void setImage(List<String> image) {
        this.image = image != null && !image.isEmpty() ? image : new ArrayList<>();
    }

    /**
     * 景点状态，0禁用，1正常
     */
    @TableField("status")
    @DataDict(code = "yes_no", target = "statusText")
    private Integer status;

    /**
     * 状态名称
     */
    @TableField(exist = false)
    private String statusText;

    /**
     * 评论详情
     */
    @TableField(exist = false)
    private List<CommentDto> commentList;
}

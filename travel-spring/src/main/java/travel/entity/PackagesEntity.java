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
import travel.dto.ScenicDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 套餐
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "travel_packages", autoResultMap = true)
public class PackagesEntity extends BaseEntity {

    /**
     * 套餐名称
     */
    @TableField("name")
    private String name;

    /**
     * 套餐描述
     */
    @TableField("description")
    private String description;

    /**
     * 套餐区域
     */
    @TableField("address")
    private String address;

    /**
     * 套餐价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 套餐评分
     */
    @TableField( exist = false)
    private Integer score;

    /**
     * 套餐图片
     */
    @TableField(value = "image", typeHandler = FastjsonTypeHandler.class)
    private List<String> image;

    public void setImage(List<String> image) {
        this.image = image != null && !image.isEmpty() ? image : new ArrayList<>();
    }

    /**
     * 套餐状态，0禁用，1正常
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
     * 套餐景点id
     */
    @TableField(exist = false)
    private List<Long> scenicIdList;

    /**
     * 套餐详情
     */
    @TableField(exist = false)
    private List<ScenicDto> scenicList;

    /**
     * 评论详情
     */
    @TableField(exist = false)
    private List<CommentDto> commentList;

}

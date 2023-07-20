package travel.dict.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import travel.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("travel_dict")
public class DictEntity extends BaseEntity {

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 值
     */
    @TableField("value")
    private String value;

    /**
     * 描述
     */
    @TableField("label")
    private String label;


}

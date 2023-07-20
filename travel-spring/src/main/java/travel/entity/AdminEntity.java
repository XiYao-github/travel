package travel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import travel.dict.annotation.DataDict;
import travel.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("travel_admin")
public class AdminEntity extends BaseEntity {

    /**
     * 管理员名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 管理员状态，0禁用，1正常
     */
    @TableField("status")
    @DataDict(code = "yes_no", target = "statusText")
    private Integer status;

    /**
     * 状态名称
     */
    @TableField(exist = false)
    private String statusText;

}

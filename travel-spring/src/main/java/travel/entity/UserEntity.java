package travel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import travel.dict.annotation.DataDict;
import travel.base.entity.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "travel_user", autoResultMap = true)
public class UserEntity extends BaseEntity {

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 出生日期
     */
    @TableField("birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    /**
     * 性别，1男，2女
     */
    @TableField("gender")
    @DataDict(code = "gender", target = "genderText")
    private Integer gender;

    /**
     * 性别名称
     */
    @TableField(exist = false)
    private String genderText;

    /**
     * 用户状态，0禁用，1正常
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
     * 用户头像
     */
    @TableField(value = "image", typeHandler = FastjsonTypeHandler.class)
    private List<String> image;

    public void setImage(List<String> image) {
        this.image = image != null && !image.isEmpty() ? image : new ArrayList<>();
    }
}

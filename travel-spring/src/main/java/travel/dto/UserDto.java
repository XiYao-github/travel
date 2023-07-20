package travel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author wang
 * @since 2023-03-27
 */
@Data
public class UserDto {
    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    /**
     * 性别，1男，2女
     */
    private Integer gender;

    /**
     * 性别名称
     */
    private String genderText;

    /**
     * 用户状态，0禁用，1正常
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusText;

    /**
     * 用户头像
     */
    private List<String> image;

    /**
     * 注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
}

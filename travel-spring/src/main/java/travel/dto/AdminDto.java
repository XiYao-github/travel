package travel.dto;

import lombok.Data;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author wang
 * @since 2023-03-27
 */
@Data
public class AdminDto {
    private Long id;
    /**
     * 管理员名
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
     * 管理员状态，0禁用，1正常
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusText;


}

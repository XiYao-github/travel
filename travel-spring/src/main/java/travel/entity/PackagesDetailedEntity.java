package travel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 套餐景点明细
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("travel_packages_detailed")
public class PackagesDetailedEntity {

    @TableId
    private Long id;

    /**
     * 套餐id
     */
    @TableField("packages_id")
    private Long packagesId;

    /**
     * 景点id
     */
    @TableField("scenic_id")
    private Long scenicId;

}

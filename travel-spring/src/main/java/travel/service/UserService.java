package travel.service;

import travel.base.service.BaseService;
import travel.dto.UserDto;
import travel.entity.UserEntity;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
public interface UserService extends BaseService<UserEntity> {
    /**
     * 查询详情
     *
     * @param id 数据id
     */
    UserEntity queryInfo(Long id);

    /**
     * 新增
     *
     * @param userDto 数据
     */
    UserEntity insert(UserDto userDto);

    /**
     * 修改
     *
     * @param userDto 数据
     */
    UserEntity update(UserDto userDto);

    /**
     * 删除
     *
     * @param idList 数据id
     */
    void deleteBatch(List<Long> idList);
}

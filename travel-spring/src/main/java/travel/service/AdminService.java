package travel.service;

import travel.base.service.BaseService;
import travel.dto.AdminDto;
import travel.entity.AdminEntity;

import java.util.List;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
public interface AdminService extends BaseService<AdminEntity> {
    /**
     * 查询详情
     *
     * @param id 数据id
     */
    AdminEntity queryInfo(Long id);

    /**
     * 新增
     *
     * @param adminDto 数据
     */
    AdminEntity insert(AdminDto adminDto);

    /**
     * 修改
     *
     * @param adminDto 数据
     */
    AdminEntity update(AdminDto adminDto);

    /**
     * 删除
     *
     * @param idList 数据id
     */
    void deleteBatch(List<Long> idList);
}

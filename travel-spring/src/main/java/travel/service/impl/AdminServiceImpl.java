package travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import travel.base.service.impl.BaseServiceImpl;
import travel.base.utils.ConvertUtils;
import travel.dto.AdminDto;
import travel.entity.AdminEntity;
import travel.exception.HttpException;
import travel.mapper.AdminMapper;
import travel.service.AdminService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<AdminMapper, AdminEntity> implements AdminService {

    @Override
    public QueryWrapper<AdminEntity> queryWrapper(Map<String, Object> params) {
        return null;
    }

    @Override
    public void handleList(List<AdminEntity> list, Map<String, Object> params) {

    }

    @Override
    public AdminEntity queryInfo(Long id) {
        return this.getById(id);
    }

    @Override
    public AdminEntity insert(AdminDto adminDto) {
        AdminEntity entity = ConvertUtils.sourceToTarget(adminDto, AdminEntity.class);
        this.save(entity);
        return this.queryInfo(entity.getId());
    }

    @Override
    public AdminEntity update(AdminDto adminDto) {
        AdminEntity entity = this.queryInfo(adminDto.getId());
        if (entity == null) {
            throw new HttpException("更新数据失败！！！");
        }
        BeanUtils.copyProperties(adminDto, entity, "id");
        this.updateById(entity);
        return entity;
    }

    @Override
    public void deleteBatch(List<Long> idList) {
        this.removeByIds(idList);
    }
}

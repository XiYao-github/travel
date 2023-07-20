package travel.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import travel.base.service.impl.BaseServiceImpl;
import travel.base.utils.ConvertUtils;
import travel.dto.UserDto;
import travel.entity.UserEntity;
import travel.exception.HttpException;
import travel.mapper.UserMapper;
import travel.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public QueryWrapper<UserEntity> queryWrapper(Map<String, Object> params) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(!StrUtil.isBlankIfStr(params.get("username")), UserEntity::getUsername, params.get("username"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("status")), UserEntity::getStatus, params.get("status"));
        return wrapper;
    }

    @Override
    public void handleList(List<UserEntity> list, Map<String, Object> params) {

    }

    @Override
    public UserEntity queryInfo(Long id) {
        return this.getById(id);
    }

    @Override
    public UserEntity insert(UserDto userDto) {
        UserEntity entity = lambdaQuery()
                .eq(UserEntity::getUsername, userDto.getUsername())
                .one();
        if (entity != null) {
            throw new HttpException("新增数据失败！！！");
        }
        entity = ConvertUtils.sourceToTarget(userDto, UserEntity.class);
        this.save(entity);
        return this.queryInfo(entity.getId());
    }

    @Override
    public UserEntity update(UserDto userDto) {
        UserEntity entity = this.queryInfo(userDto.getId());
        if (entity == null) {
            throw new HttpException("更新数据失败！！！");
        }
        BeanUtils.copyProperties(userDto, entity, "id");
        this.updateById(entity);
        return entity;
    }

    @Override
    public void deleteBatch(List<Long> idList) {
        this.removeByIds(idList);
    }
}

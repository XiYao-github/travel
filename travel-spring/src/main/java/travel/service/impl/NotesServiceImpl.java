package travel.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel.base.service.impl.BaseServiceImpl;
import travel.base.utils.ConvertUtils;
import travel.dto.NotesDto;
import travel.dto.UserDto;
import travel.entity.NotesEntity;
import travel.entity.UserEntity;
import travel.exception.HttpException;
import travel.mapper.NotesMapper;
import travel.service.NotesService;
import travel.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 游记 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-03-29
 */
@Service
public class NotesServiceImpl extends BaseServiceImpl<NotesMapper, NotesEntity> implements NotesService {
    @Autowired
    private UserService userService;

    @Override
    public QueryWrapper<NotesEntity> queryWrapper(Map<String, Object> params) {
        QueryWrapper<NotesEntity> wrapper = new QueryWrapper<>();
        wrapper.inSql(!StrUtil.isBlankIfStr(params.get("username")), "id", "select id from travel_user where username like '%" + params.get("username") + "%'");
        wrapper.lambda().like(!StrUtil.isBlankIfStr(params.get("title")), NotesEntity::getTitle, params.get("title"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("status")), NotesEntity::getStatus, params.get("status"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("userId")), NotesEntity::getUserId, params.get("userId"));
        wrapper.lambda().orderByDesc(NotesEntity::getUpdatedTime);
        return wrapper;
    }

    @Override
    public void handleList(List<NotesEntity> list, Map<String, Object> params) {
        List<Long> userId = list.stream().map(NotesEntity::getUserId).collect(Collectors.toList());
        Map<Long, UserEntity> userEntityMap = userId.isEmpty() ? new HashMap<>() : userService.lambdaQuery().in(UserEntity::getId, userId).list().stream()
                .collect(Collectors.toMap(UserEntity::getId, Function.identity()));
        list.parallelStream().forEachOrdered(entity -> {
            if(userEntityMap.containsKey(entity.getUserId())){
                UserEntity userEntity = userEntityMap.get(entity.getUserId());
                entity.setUserInfo(ConvertUtils.sourceToTarget(userEntity, UserDto.class));
                entity.setUsername(userEntity.getUsername());
            }
        });
    }

    @Override
    public NotesEntity queryInfo(Long id) {
        NotesEntity entity = this.getById(id);
        UserEntity userEntity = userService.lambdaQuery().eq(UserEntity::getId, entity.getUserId()).one();
        entity.setUserInfo(ConvertUtils.sourceToTarget(userEntity, UserDto.class));
        entity.setUsername(userEntity.getUsername());
        return entity;
    }

    @Override
    public NotesEntity insert(NotesDto notesDto) {
        NotesEntity entity = ConvertUtils.sourceToTarget(notesDto, NotesEntity.class);
        this.save(entity);
        return this.queryInfo(entity.getId());
    }

    @Override
    public NotesEntity update(NotesDto notesDto) {
        NotesEntity entity = this.queryInfo(notesDto.getId());
        if (entity == null) {
            throw new HttpException("更新数据失败！！！");
        }
        BeanUtils.copyProperties(notesDto, entity, "id");
        this.updateById(entity);
        return entity;
    }

    @Override
    public void deleteBatch(List<Long> idList) {
        this.removeByIds(idList);
    }
}

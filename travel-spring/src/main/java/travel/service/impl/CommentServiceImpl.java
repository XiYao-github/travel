package travel.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel.base.service.impl.BaseServiceImpl;
import travel.base.utils.ConvertUtils;
import travel.dto.CommentDto;
import travel.dto.UserDto;
import travel.entity.CommentEntity;
import travel.entity.UserEntity;
import travel.exception.HttpException;
import travel.mapper.CommentMapper;
import travel.service.CommentService;
import travel.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 评价 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<CommentMapper, CommentEntity> implements CommentService {
    @Autowired
    private UserService userService;

    @Override
    public QueryWrapper<CommentEntity> queryWrapper(Map<String, Object> params) {
        QueryWrapper<CommentEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("type")), CommentEntity::getType, params.get("type"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("score")), CommentEntity::getScore, params.get("score"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("itemId")), CommentEntity::getItemId, params.get("itemId"));
        wrapper.lambda().eq(!StrUtil.isBlankIfStr(params.get("userId")), CommentEntity::getUserId, params.get("userId"));
        wrapper.lambda().orderByDesc(CommentEntity::getUpdatedTime);
        return wrapper;
    }

    @Override
    public void handleList(List<CommentEntity> list, Map<String, Object> params) {
        List<Long> userId = list.stream().map(CommentEntity::getUserId).collect(Collectors.toList());
        Map<Long, UserEntity> userEntityMap = userId.isEmpty() ? new HashMap<>() : userService.lambdaQuery().in(UserEntity::getId, userId).list().stream()
                .collect(Collectors.toMap(UserEntity::getId, Function.identity()));
        list.parallelStream().forEachOrdered(entity -> {
            if (userEntityMap.containsKey(entity.getUserId())) {
                UserEntity userEntity = userEntityMap.get(entity.getUserId());
                entity.setUserInfo(ConvertUtils.sourceToTarget(userEntity, UserDto.class));
                entity.setUsername(userEntity.getUsername());
            }
        });
    }

    @Override
    public CommentEntity queryInfo(Long id) {
        CommentEntity entity = this.getById(id);
        UserEntity userEntity = userService.lambdaQuery().eq(UserEntity::getId, entity.getUserId()).one();
        entity.setUserInfo(ConvertUtils.sourceToTarget(userEntity, UserDto.class));
        entity.setUsername(userEntity.getUsername());
        return entity;
    }

    @Override
    public CommentEntity insert(CommentDto commentDto) {
        CommentEntity entity = ConvertUtils.sourceToTarget(commentDto, CommentEntity.class);
        this.save(entity);
        return this.queryInfo(entity.getId());
    }

    @Override
    public CommentEntity update(CommentDto commentDto) {
        CommentEntity entity = this.queryInfo(commentDto.getId());
        if (entity == null) {
            throw new HttpException("更新数据失败！！！");
        }
        BeanUtils.copyProperties(commentDto, entity, "id");
        this.updateById(entity);
        return entity;
    }

    @Override
    public void deleteBatch(List<Long> idList) {
        this.removeByIds(idList);
    }
}

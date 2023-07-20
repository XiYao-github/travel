package travel.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import travel.base.mapper.BaseMapper;

public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

}

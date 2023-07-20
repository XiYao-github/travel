package travel.base.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@UtilityClass
public class ConvertUtils {

    public <T> T sourceToTarget(Object source, Class<T> target, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        T targetObject = null;
        try {
            targetObject = target.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, targetObject, ignoreProperties);
        } catch (Exception e) {
            log.error("convert error ", e);
        }
        return targetObject;
    }

    public <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target, String... ignoreProperties) {
        if (sourceList == null) {
            return null;
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        sourceList.parallelStream().forEachOrdered(source -> {
            try {
                T targetObject = target.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(source, targetObject, ignoreProperties);
                targetList.add(targetObject);
            } catch (Exception e) {
                log.error("convert error ", e);
            }
        });
        return targetList;
    }
}
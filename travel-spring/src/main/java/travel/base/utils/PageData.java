package travel.base.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;


@Data
public class PageData<T> {

    private long total;

    private long size;

    private long current;

    private long pages;

    private List<T> records;

    public PageData(IPage<?> page, Class<T> target) {
        this.total = page.getTotal();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.pages = page.getPages();
        this.records = ConvertUtils.sourceToTarget(page.getRecords(), target);
    }

    public PageData(long total, long size, long current, long pages, List<T> records) {
        this.total = total;
        this.size = size;
        this.current = current;
        this.pages = pages;
        this.records = records;
    }
}

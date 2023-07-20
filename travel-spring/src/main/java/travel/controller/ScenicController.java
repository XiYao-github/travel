package travel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import travel.base.groups.CreateGroup;
import travel.base.groups.UpdateGroup;
import travel.base.utils.ConvertUtils;
import travel.base.utils.Result;
import travel.dto.ScenicDto;
import travel.service.ScenicService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景点 前端控制器
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/scenic")
public class ScenicController {
    @Autowired
    private ScenicService scenicService;

    @GetMapping("/list")
    public Result queryList(@RequestParam Map<String, Object> params) {
        return new Result().success(scenicService.queryList(params, false, ScenicDto.class));
    }

    @GetMapping("/page")
    public Result queryPage(@RequestParam Map<String, Object> params) {
        return new Result().success(scenicService.queryPage(params, ScenicDto.class));
    }

    @GetMapping("/info")
    public Result queryInfo(@RequestParam("id") Long id) {
        return new Result().success(ConvertUtils.sourceToTarget(scenicService.queryInfo(id), ScenicDto.class));
    }

    @PostMapping
    public Result insert(@RequestBody @Validated(CreateGroup.class) ScenicDto scenicDto) {
        return new Result().success(ConvertUtils.sourceToTarget(scenicService.insert(scenicDto), ScenicDto.class));
    }

    @PutMapping
    public Result update(@RequestBody @Validated(UpdateGroup.class) ScenicDto scenicDto) {
        return new Result().success(ConvertUtils.sourceToTarget(scenicService.update(scenicDto), ScenicDto.class));
    }

    @DeleteMapping
    public Result delete(@RequestBody List<Long> idList) {
        scenicService.deleteBatch(idList);
        return new Result();
    }
}


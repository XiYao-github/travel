package travel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import travel.base.groups.CreateGroup;
import travel.base.groups.UpdateGroup;
import travel.base.utils.ConvertUtils;
import travel.base.utils.Result;
import travel.dto.PackagesDto;
import travel.service.PackagesService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 套餐 前端控制器
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/packages")
public class PackagesController {
    @Autowired
    private PackagesService packagesService;

    @GetMapping("/list")
    public Result queryList(@RequestParam Map<String, Object> params) {
        return new Result().success(packagesService.queryList(params, false, PackagesDto.class));
    }

    @GetMapping("/page")
    public Result queryPage(@RequestParam Map<String, Object> params) {
        return new Result().success(packagesService.queryPage(params, PackagesDto.class));
    }

    @GetMapping("/info")
    public Result queryInfo(@RequestParam("id") Long id) {
        return new Result().success(ConvertUtils.sourceToTarget(packagesService.queryInfo(id), PackagesDto.class));
    }

    @PostMapping
    public Result insert(@RequestBody @Validated(CreateGroup.class) PackagesDto packagesDto) {
        return new Result().success(ConvertUtils.sourceToTarget(packagesService.insert(packagesDto), PackagesDto.class));
    }

    @PutMapping
    public Result update(@RequestBody @Validated(UpdateGroup.class) PackagesDto packagesDto) {
        return new Result().success(ConvertUtils.sourceToTarget(packagesService.update(packagesDto), PackagesDto.class));
    }

    @DeleteMapping
    public Result delete(@RequestBody List<Long> idList) {
        packagesService.deleteBatch(idList);
        return new Result();
    }
}


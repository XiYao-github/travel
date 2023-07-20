package travel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import travel.base.groups.CreateGroup;
import travel.base.groups.UpdateGroup;
import travel.base.utils.ConvertUtils;
import travel.base.utils.Result;
import travel.dto.ShopDto;
import travel.entity.ShopEntity;
import travel.service.ShopService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @GetMapping("/list")
    public Result queryList(@RequestParam Map<String, Object> params) {
        return new Result().success(shopService.queryList(params, true, ShopDto.class));
    }

    @GetMapping("/page")
    public Result queryPage(@RequestParam Map<String, Object> params) {
        return new Result().success(shopService.queryPage(params, ShopDto.class));
    }

    @GetMapping("/info")
    public Result queryInfo(@RequestParam("id") Long id) {
        return new Result().success(ConvertUtils.sourceToTarget(shopService.queryInfo(id), ShopDto.class));
    }

    @GetMapping("/count")
    public Result queryCount(@RequestParam("userId") Long userId) {
        return new Result().success(shopService.lambdaQuery().eq(ShopEntity::getUserId, userId).count());
    }

    @PostMapping
    public Result insert(@RequestBody @Validated(CreateGroup.class) ShopDto shopDto) {
        return new Result().success(ConvertUtils.sourceToTarget(shopService.insert(shopDto), ShopDto.class));
    }

    @PutMapping
    public Result update(@RequestBody @Validated(UpdateGroup.class) ShopDto shopDto) {
        return new Result().success(ConvertUtils.sourceToTarget(shopService.update(shopDto), ShopDto.class));
    }

    @DeleteMapping
    public Result delete(@RequestBody List<Long> idList) {
        shopService.deleteBatch(idList);
        return new Result();
    }
}


package travel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import travel.base.groups.CreateGroup;
import travel.base.groups.UpdateGroup;
import travel.base.utils.ConvertUtils;
import travel.base.utils.Result;
import travel.dto.OrderDto;
import travel.service.OrderService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author wang
 * @since 2023-03-29
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public Result queryList(@RequestParam Map<String, Object> params) {
        return new Result().success(orderService.queryList(params, true, OrderDto.class));
    }

    @GetMapping("/page")
    public Result queryPage(@RequestParam Map<String, Object> params) {
        return new Result().success(orderService.queryPage(params, OrderDto.class));
    }

    @GetMapping("/info")
    public Result queryInfo(@RequestParam("id") Long id) {
        return new Result().success(ConvertUtils.sourceToTarget(orderService.queryInfo(id), OrderDto.class));
    }

    @PostMapping
    public Result insert(@RequestBody @Validated(CreateGroup.class) OrderDto orderDto) {
        return new Result().success(ConvertUtils.sourceToTarget(orderService.insert(orderDto), OrderDto.class));
    }

    @PutMapping
    public Result update(@RequestBody @Validated(UpdateGroup.class) OrderDto orderDto) {
        return new Result().success(ConvertUtils.sourceToTarget(orderService.update(orderDto), OrderDto.class));
    }

    @DeleteMapping
    public Result delete(@RequestBody List<Long> idList) {
        orderService.deleteBatch(idList);
        return new Result();
    }
}


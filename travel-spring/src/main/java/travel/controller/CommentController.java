package travel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import travel.base.groups.CreateGroup;
import travel.base.groups.UpdateGroup;
import travel.base.utils.ConvertUtils;
import travel.base.utils.Result;
import travel.dto.CommentDto;
import travel.service.CommentService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评价 前端控制器
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    public Result queryList(@RequestParam Map<String, Object> params) {
        return new Result().success(commentService.queryList(params, true, CommentDto.class));
    }

    @GetMapping("/page")
    public Result queryPage(@RequestParam Map<String, Object> params) {
        return new Result().success(commentService.queryPage(params, CommentDto.class));
    }

    @GetMapping("/info")
    public Result queryInfo(@RequestParam("id") Long id) {
        return new Result().success(ConvertUtils.sourceToTarget(commentService.queryInfo(id), CommentDto.class));
    }

    @PostMapping
    public Result insert(@RequestBody @Validated(CreateGroup.class) CommentDto commentDto) {
        return new Result().success(ConvertUtils.sourceToTarget(commentService.insert(commentDto), CommentDto.class));
    }

    @PutMapping
    public Result update(@RequestBody @Validated(UpdateGroup.class) CommentDto commentDto) {
        return new Result().success(ConvertUtils.sourceToTarget(commentService.update(commentDto), CommentDto.class));
    }

    @DeleteMapping
    public Result delete(@RequestBody List<Long> idList) {
        commentService.deleteBatch(idList);
        return new Result();
    }
}


package travel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import travel.base.groups.CreateGroup;
import travel.base.groups.UpdateGroup;
import travel.base.utils.ConvertUtils;
import travel.base.utils.Result;
import travel.dto.NotesDto;
import travel.service.NotesService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 游记 前端控制器
 * </p>
 *
 * @author wang
 * @since 2023-03-29
 */
@RestController
@RequestMapping("/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @GetMapping("/list")
    public Result queryList(@RequestParam Map<String, Object> params) {
        return new Result().success(notesService.queryList(params, true, NotesDto.class));
    }

    @GetMapping("/page")
    public Result queryPage(@RequestParam Map<String, Object> params) {
        return new Result().success(notesService.queryPage(params, NotesDto.class));
    }

    @GetMapping("/info")
    public Result queryInfo(@RequestParam("id") Long id) {
        return new Result().success(ConvertUtils.sourceToTarget(notesService.queryInfo(id), NotesDto.class));
    }

    @PostMapping
    public Result insert(@RequestBody @Validated(CreateGroup.class) NotesDto notesDto) {
        return new Result().success(ConvertUtils.sourceToTarget(notesService.insert(notesDto), NotesDto.class));
    }

    @PutMapping
    public Result update(@RequestBody @Validated(UpdateGroup.class) NotesDto notesDto) {
        return new Result().success(ConvertUtils.sourceToTarget(notesService.update(notesDto), NotesDto.class));
    }

    @DeleteMapping
    public Result delete(@RequestBody List<Long> idList) {
        notesService.deleteBatch(idList);
        return new Result();
    }
}


package travel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import travel.base.groups.CreateGroup;
import travel.base.groups.UpdateGroup;
import travel.base.utils.ConvertUtils;
import travel.base.utils.Result;
import travel.dto.AdminDto;
import travel.entity.AdminEntity;
import travel.service.AdminService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/list")
    public Result queryList(@RequestParam Map<String, Object> params) {
        return new Result().success(adminService.queryList(params, true, AdminDto.class));
    }

    @GetMapping("/page")
    public Result queryPage(@RequestParam Map<String, Object> params) {
        return new Result().success(adminService.queryPage(params, AdminDto.class));
    }

    @GetMapping("/info")
    public Result queryInfo(@RequestParam("id") Long id) {
        return new Result().success(ConvertUtils.sourceToTarget(adminService.queryInfo(id), AdminDto.class));
    }

    @GetMapping("/getUser")
    public Result getUser(@RequestParam Map<String, Object> params) {
        AdminEntity entity = adminService.lambdaQuery()
                .eq(AdminEntity::getUsername, params.get("username"))
                .eq(AdminEntity::getPassword, params.get("password"))
                .eq(AdminEntity::getStatus, 1).one();
        return new Result().success(ConvertUtils.sourceToTarget(entity, AdminDto.class));
    }

    @PostMapping
    public Result insert(@RequestBody @Validated(CreateGroup.class) AdminDto adminDto) {
        return new Result().success(ConvertUtils.sourceToTarget(adminService.insert(adminDto), AdminDto.class));
    }

    @PutMapping
    public Result update(@RequestBody @Validated(UpdateGroup.class) AdminDto adminDto) {
        return new Result().success(ConvertUtils.sourceToTarget(adminService.update(adminDto), AdminDto.class));
    }

    @DeleteMapping
    public Result delete(@RequestBody List<Long> idList) {
        adminService.deleteBatch(idList);
        return new Result();
    }
}


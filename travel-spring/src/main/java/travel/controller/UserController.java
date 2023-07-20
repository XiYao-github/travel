package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import travel.base.groups.CreateGroup;
import travel.base.groups.UpdateGroup;
import travel.base.utils.ConvertUtils;
import travel.base.utils.Result;
import travel.dto.UserDto;
import travel.entity.UserEntity;
import travel.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //登入
    //@RequiresAuthentication
    //角色
    //@RequiresRoles("user")
    //权限
    //@RequiresPermissions("admin")
    @GetMapping("/list")
    public Result queryList(@RequestParam Map<String, Object> params) {
        return new Result().success(userService.queryList(params, false, UserDto.class));
    }

    @GetMapping("/page")
    public Result queryPage(@RequestParam Map<String, Object> params) {
        return new Result().success(userService.queryPage(params, UserDto.class));
    }

    @GetMapping("/info")
    public Result queryInfo(@RequestParam("id") Long id) {
        return new Result().success(ConvertUtils.sourceToTarget(userService.queryInfo(id), UserDto.class));
    }

    @GetMapping("/getUser")
    public Result getUser(@RequestParam Map<String, Object> params) {
        UserEntity entity = userService.lambdaQuery()
                .eq(UserEntity::getUsername, params.get("username"))
                .eq(UserEntity::getPassword, params.get("password"))
                .eq(UserEntity::getStatus, 1).one();
        return new Result().success(ConvertUtils.sourceToTarget(entity, UserDto.class));
    }

    @PostMapping
    public Result insert(@RequestBody @Validated(CreateGroup.class) UserDto userDto) {
        List<String> list = new ArrayList<>();
        list.add("http://localhost:8080/travel/image/download?name=img.png");
        userDto.setImage(list);
        return new Result().success(ConvertUtils.sourceToTarget(userService.insert(userDto), UserDto.class));
    }

    @PutMapping
    public Result update(@RequestBody @Validated(UpdateGroup.class) UserDto userDto) {
        return new Result().success(ConvertUtils.sourceToTarget(userService.update(userDto), UserDto.class));
    }

    @DeleteMapping
    public Result delete(@RequestBody List<Long> idList) {
        userService.deleteBatch(idList);
        return new Result();
    }

    //@GetMapping("/login")
    //public Result login(String username, String password) {
    //    //获取Subject对象
    //    Subject subject = SecurityUtils.getSubject();
    //    //封装数据到token中
    //    AuthenticationToken token = new UsernamePasswordToken(username, password);
    //    try {
    //        //使用shiro的login方法判断
    //        subject.login(token);
    //        return new Result().success("登录成功：token=" + token);
    //    } catch (AuthenticationException e) {
    //        return new Result().error("登录失败");
    //    }
    //}
    //
    //@GetMapping("/logout")
    //public Result logout() {
    //    return new Result().success("用户退出");
    //}
}


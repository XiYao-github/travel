//package travel.common;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import travel.entity.UserEntity;
//import travel.exception.HttpException;
//import travel.service.UserService;
//
//import java.util.ArrayList;
//
//@Component
//@Slf4j
//public class MyRealm extends AuthorizingRealm {
//    @Autowired
//    private UserService userService;
//
//*
//     * 鉴权
//     *
//     * @param principalCollection 令牌
//
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        //模拟数据库查询角色信息
//        ArrayList<String> roles = new ArrayList<>();
//        roles.add("user");
//        //模拟数据库查询权限信息
//        ArrayList<String> permissions = new ArrayList<>();
//        permissions.add("admin");
//        //设置用户角色和权限
//        info.addRoles(roles);
//        info.addStringPermissions(permissions);
//        return info;
//    }
//
//*
//     * 认证
//     *
//     * @param authenticationToken token对象
//
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        //获取用户信息
//        String username = authenticationToken.getPrincipal().toString();
//        String password = authenticationToken.getCredentials().toString();
//        log.info("username：" + username);
//        log.info("password：" + password);
//        //查询数据库
//        UserEntity entity = userService.lambdaQuery().eq(UserEntity::getUsername, username).one();
//        //密码加密
//        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
//        //指定密码匹配方式
//        matcher.setHashAlgorithmName("md5");
//        //指定密码迭代次数
//        matcher.setHashIterations(3);
//        //使用父亲方法使匹配方式生效
//        super.setCredentialsMatcher(matcher);
//        //判断
//        if (entity == null) {
//            throw new HttpException("用户不存在！！！");
//        }
//        //传递账号和密码:参数1：用户认证凭证信息，参数2：md5带盐3次加密密码，参数三：字节salt,参数4：当前DefinitionRealm名称
//        return new SimpleAuthenticationInfo(
//                entity.getUsername(),
//                entity.getPassword(),
//                ByteSource.Util.bytes(entity.getSalt()),
//                getName()
//        );
//    }
//}

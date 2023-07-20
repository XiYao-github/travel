//package travel.config;
//
//import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
//import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
//import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import travel.common.MyRealm;
//
//@Configuration
//public class ShiroConfig {
//
//    @Autowired
//    private MyRealm myRealm;
//
//    @Bean
//    public DefaultWebSecurityManager webSecurityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        ModularRealmAuthenticator realmAuthenticator = new ModularRealmAuthenticator();
//        realmAuthenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
//        //管理器设置属性
//        //先设置认证器，再进行realm
//        securityManager.setAuthenticator(realmAuthenticator);
//        securityManager.setRealm(myRealm);
//        return securityManager;
//    }
//
//    @Bean
//    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
//        definition.addPathDefinition("/user/login", "anon");
//        definition.addPathDefinition("/user/logout", "logout");
//        definition.addPathDefinition("/**", "authc");
//        definition.addPathDefinition("/**", "user");
//        return definition;
//    }
//}

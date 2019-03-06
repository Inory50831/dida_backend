package didastudy.config;

import didastudy.shiro.JWTFilter;
import didastudy.shiro.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 处理拦截资源文件问题
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //指定要求登录时的链接
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登陆成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权时跳转的链接
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //自定义JWT过滤器
        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
        filterMap.put("jwt", new JWTFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        //拦截器链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //401和404页面不通过Filter
        filterChainDefinitionMap.put("/401", "anon");
        filterChainDefinitionMap.put("/404", "anon");
        //静态资源不通过Filter
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/templates/**", "anon");
        //注销操作不通过Filter
        filterChainDefinitionMap.put("/logout", "logout");
        //其余
        //filterChainDefinitionMap.put("/system", "roles[2]");
        filterChainDefinitionMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(myShiroRealm());
        //注入缓存管理器
        securityManager.setCacheManager(ehCacheManager());
        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        //md5加密
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        //散列次数(加密次数)
//        hashedCredentialsMatcher.setHashIterations(2);
//        return hashedCredentialsMatcher;
//    }

    /**
     * 身份认证realm
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        //myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        System.out.println("myShiroRealm注入成功");
        return myShiroRealm;
    }

    /**
     * shiro缓存管理器
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    /**
     * 开启shiro aop注解支持，使用代理方式
     * @param securityManager 安全管理器
     * @return 授权Advisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}

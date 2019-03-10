package didastudy.shiro;

import didastudy.entity.*;
import didastudy.service.SysRoleService;
import didastudy.service.SysUserService;
import didastudy.service.UserLoginService;
import didastudy.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

    public static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

    //JWT签名密钥
    public static final String SECRET = "admin";

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 身份认证
     * Authentication 用来验证用户身份
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth)
        throws AuthenticationException {
        logger.info("MyShiroRealm.doGetAuthenticationInfo()");
        String token = (String)auth.getCredentials();
        //解密获得username,用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if(username == null) {
            throw new AuthenticationException("token invalid");
        }
        //通过username从数据库中查找NucDidaLogin对象
        NucDidaLogin login = userLoginService.getUserByNumber(username);
        if(login == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        if(!JWTUtil.verify(token, username, login.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        String username = JWTUtil.getUsername(principals.toString());
        //NucDidaLogin login = userLoginService.getUserByNumber(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //NucDidaUser user = sysUserService.getUserByNumber(username);
        //设置对应角色的权限信息(暂时按角色行使权限)
        //Java 8 lamda
        sysRoleService.getRoleByNumber(username).stream().forEach(
                NucDidaRole ->{
                    authorizationInfo.addRole(NucDidaRole.getRole());
                }
        );
//        for(NucDidaRole role:user.getRoles()){
//            authorizationInfo.addRole(role.getRole());
//            for(NucDidaPermission p:role.getPermissions()){
//                authorizationInfo.addStringPermission(p.getPermission());
//            }
//        }
        return authorizationInfo;
    }
}

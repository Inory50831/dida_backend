package didastudy.controller;

import didastudy.entity.NucDidaLogin;
import didastudy.entity.NucDidaUser;
import didastudy.service.SysUserService;
import didastudy.service.UserLoginService;
import didastudy.shiro.ShiroKit;
import didastudy.util.BaseResponse;
import didastudy.util.JWTUtil;
import didastudy.util.LoginParam;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserLoginController {

    public static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestHeader(name="Content-Type", defaultValue = "application/json")String contentType,
                                      @RequestBody LoginParam loginParam) {
        logger.info("用户请求登录获取Token");
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        NucDidaLogin user = userLoginService.getUserByNumber(username);
        //随机数盐
        String salt = user.getSalt();
        //原密码加密(通过username + salt作为盐) md5
        String encodedPassword = ShiroKit.md5(password, username + salt);
        NucDidaUser u = sysUserService.getUserByNumber(username);
        if( user.getPassword().equals(encodedPassword) && u.getStatus()!= 0) {
            return new BaseResponse<>(true, "Login success", JWTUtil.sign(username, encodedPassword));
        } else {
            throw new UnauthorizedException();
        }
    }
}

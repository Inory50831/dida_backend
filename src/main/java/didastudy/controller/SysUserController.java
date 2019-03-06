package didastudy.controller;

import didastudy.entity.NucDidaUser;
import didastudy.service.SysUserService;
import didastudy.util.CustomErrorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/system/sysuser")
@Api(value = "/sysuser", description = "用户管理")
public class SysUserController {

    public static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "获取用户信息列表", notes = "")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping(value = "/user")
    public ResponseEntity<List<NucDidaUser>> getAllUser() {
        List<NucDidaUser> users = sysUserService.listUser();
        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<NucDidaUser>>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "获取用户信息", notes = "根据学号或职工号获取用户信息")
    @ApiImplicitParam(name = "number", value = "用户学号或职工号", required = true, dataType = "String", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping(value = "/user/{number}")
    public ResponseEntity<?> getUserByNumber(@PathVariable("number") String number) {
        logger.info("Fetching User with number {}", number);
        NucDidaUser user = sysUserService.getUserByNumber(number);
        if(user == null) {
            logger.error("User with number {} not found", number);
            return new ResponseEntity<>(new CustomErrorType("User with number " + number + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<NucDidaUser>(user, HttpStatus.OK);
    }

    @ApiOperation(value = "修改用户信息", notes = "根据用户id指定更新对象，并根据传来的user数据来更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "NucDidaUser")
    })
    @RequiresAuthentication
    @RequiresRoles("2")
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody NucDidaUser user) {
        logger.info("Updating User with id {}", id);
        NucDidaUser currentUser = sysUserService.getUserById(id);
        if(currentUser == null) {
            logger.error("Unable to update. User with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to update. User with id " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        currentUser.setNumber(user.getNumber());
        currentUser.setName(user.getName());
        currentUser.setCollege(user.getCollege());
        currentUser.setPhone(user.getPhone());
        currentUser.setEmail(user.getEmail());
        currentUser.setMessage(user.getMessage());
        currentUser.setBackgroundImage(user.getBackgroundImage());
        currentUser.setFace(user.getFace());
        currentUser.setQq(user.getQq());
        currentUser.setType(user.getType());
        sysUserService.updateUser(currentUser);
        return new ResponseEntity<NucDidaUser>(currentUser, HttpStatus.OK);
    }

    @ApiOperation(value = "添加用户", notes = "")
    @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "NucDidaUser")
    @RequiresAuthentication
    @RequiresRoles("2")
    @PostMapping(value = "/user")
    public ResponseEntity<?> insertUser(@RequestBody NucDidaUser user, UriComponentsBuilder builder) {
        logger.info("Creating User: {}",user);
        if(sysUserService.isUserExist(user)) {
            logger.error("Unable to create. User with number {} already exist", user.getNumber());
            return new ResponseEntity<>(new CustomErrorType("Unable to create. User with number " + user.getNumber() +
                    " already exist"), HttpStatus.CONFLICT);
        }
        sysUserService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/sysuser/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "封禁用户", notes = "根据用户id封禁")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @PutMapping(value = "/user/ban/{id}")
    public ResponseEntity<?> banUser(@PathVariable("id") Long id) {
        logger.info("Ban User with id {}", id);
        NucDidaUser user = sysUserService.getUserById(id);
        if(user == null) {
            logger.error("Unable to ban. User with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to ban. User with " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        user.setStatus(0);
        sysUserService.updateUser(user);
        return new ResponseEntity<NucDidaUser>(user, HttpStatus.OK);
    }
}

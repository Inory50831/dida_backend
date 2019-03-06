package didastudy.controller;

import didastudy.entity.NucDidaPost;
import didastudy.service.SysPostService;
import didastudy.util.CustomErrorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/system/syspost")
@Api(value = "/syspost", description = "帖子管理")
public class SysPostController {

    public static final Logger logger = LoggerFactory.getLogger(SysPostController.class);

    @Autowired
    private SysPostService sysPostService;

    @ApiOperation(value = "获取帖子信息列表", notes = "")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping(value = "/post")
    public ResponseEntity<List<NucDidaPost>> getAllPost() {
        List<NucDidaPost> post = sysPostService.listPost();
        if(post.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<NucDidaPost>>(post, HttpStatus.OK);
    }

    @ApiOperation(value = "获取帖子信息(标题中关键字)", notes = "根据标题中关键字获取帖子信息")
    @ApiImplicitParam(name = "title", value = "博文标题", required = true, dataType = "String", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping(value = "/post/title/{title}")
    public ResponseEntity<?> getPostByTitle(@PathVariable("title") String title) {
        logger.info("Fetching Post with title {}", title);
        NucDidaPost post = sysPostService.getPostByTitle(title);
        if(post == null) {
            logger.error("Post with title {} not found", title);
            return new ResponseEntity<>(new CustomErrorType("Post with " + title + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<NucDidaPost>(post, HttpStatus.OK);
    }

    @ApiOperation(value = "获取帖子信息(发帖人id)", notes = "根据发帖人ID获取帖子信息")
    @ApiImplicitParam(name = "user_id", value = "发帖人id", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping(value = "/post/user/{user_id}")
    public ResponseEntity<?> getPostById(@PathVariable("user_id") Long user_id) {
        logger.info("Fetching Post with userId {}", user_id);
        NucDidaPost post = sysPostService.getPostByUserId(user_id);
        if(post == null) {
            logger.error("Post with userId {} not found", user_id);
            return new ResponseEntity<>(new CustomErrorType("Post with " + user_id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<NucDidaPost>(post, HttpStatus.OK);
    }

    @ApiOperation(value = "获取帖子信息(帖子id)", notes = "根据帖子ID获取帖子信息")
    @ApiImplicitParam(name = "id", value = "帖子id", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping(value = "/post/{id}")
    public ResponseEntity<?> getPostByUserId(@PathVariable("id") Long id) {
        logger.info("Fetching Post with id {}", id);
        NucDidaPost post = sysPostService.getPostById(id);
        if(post == null) {
            logger.error("Post with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Post with " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<NucDidaPost>(post, HttpStatus.OK);
    }

    @ApiOperation(value = "封禁帖子", notes = "根据帖子id进行封禁")
    @ApiImplicitParam(name = "id", value = "帖子ID", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @PutMapping(value = "/post/ban/{id}")
    public ResponseEntity<?> banPost(@PathVariable("id") Long id) {
        logger.info("Ban Post with id {}", id);
        NucDidaPost post = sysPostService.getPostById(id);
        if(post == null){
            logger.error("Unable to ban. Post with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to ban. Post with " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        post.setStatus(0);
        sysPostService.updatePost(post);
        return new ResponseEntity<NucDidaPost>(post, HttpStatus.OK);
    }

    @ApiOperation(value = "置顶帖子", notes = "根据帖子id进行置顶")
    @ApiImplicitParam(name = "id", value = "帖子ID", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @PutMapping(value = "/blog/top/{id}")
    public ResponseEntity<?> topBlog(@PathVariable("id") Long id) {
        logger.info("Top Post with id {}", id);
        NucDidaPost post = sysPostService.getPostById(id);
        if(post == null){
            logger.error("Unable to top. Post with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to top. Post with " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        } else if(0 == post.getStatus()) {
            logger.error("Unable to top. Post with id {} was ban", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to top. Post with " + id + " was ban"),
                    HttpStatus.NOT_FOUND);
        }
        post.setTop(1);
        sysPostService.updatePost(post);
        return new ResponseEntity<NucDidaPost>(post, HttpStatus.OK);
    }
}

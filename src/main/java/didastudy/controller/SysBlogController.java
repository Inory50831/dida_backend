package didastudy.controller;

import didastudy.entity.NucDidaBlog;
import didastudy.service.SysBlogService;
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
@RequestMapping(value = "/system/sysblog")
@Api(value = "/sysblog", description = "博文管理")
public class SysBlogController {

    public static final Logger logger = LoggerFactory.getLogger(SysBlogController.class);

    @Autowired
    private SysBlogService sysBlogService;

    @ApiOperation(value = "获取博文信息列表", notes = "")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping(value = "/blog")
    public ResponseEntity<List<NucDidaBlog>> getAllBlog() {
        List<NucDidaBlog> blog = sysBlogService.listBlog();
        if(blog.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<NucDidaBlog>>(blog, HttpStatus.OK);
    }

    @ApiOperation(value = "获取博文信息(标题中关键字)", notes = "根据标题中关键字获取博文信息")
    @ApiImplicitParam(name = "title", value = "博文标题", required = true, dataType = "String", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping(value = "/blog/title/{title}")
    public ResponseEntity<?> getBlogByTitle(@PathVariable("title") String title) {
        logger.info("Fetching Blog with title {}", title);
        NucDidaBlog blog = sysBlogService.getBlogByTitle(title);
        if(blog == null) {
            logger.error("Blog with title {} not found", title);
            return new ResponseEntity<>(new CustomErrorType("Blog with " + title + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<NucDidaBlog>(blog, HttpStatus.OK);
    }

    @ApiOperation(value = "获取博文信息(作者id)", notes = "根据作者id获取博文信息")
    @ApiImplicitParam(name = "user_id", value = "作者ID", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping(value = "/blog/user/{user_id}")
    public ResponseEntity<?> getBlogByUserId(@PathVariable("user_id") Long user_id) {
        logger.info("Fetching Blog with userId {}", user_id);
        NucDidaBlog blog = sysBlogService.getBlogByUserId(user_id);
        if(blog == null) {
            logger.error("Blog with userId {} not found", user_id);
            return new ResponseEntity<>(new CustomErrorType("Blog with " + user_id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<NucDidaBlog>(blog, HttpStatus.OK);
    }

    @ApiOperation(value = "获取博文信息(博文id)", notes = "根据博文id获取博文信息")
    @ApiImplicitParam(name = "id", value = "博文ID", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping(value = "/blog/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable("id") Long id) {
        logger.info("Fetching Blog with id {}", id);
        NucDidaBlog blog = sysBlogService.getBlogById(id);
        if(blog == null) {
            logger.error("Blog with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Blog with " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<NucDidaBlog>(blog, HttpStatus.OK);
    }

    @ApiOperation(value = "封禁博文", notes = "根据博文id进行封禁")
    @ApiImplicitParam(name = "id", value = "博文ID", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @PutMapping(value = "/blog/ban/{id}")
    public ResponseEntity<?> banBlog(@PathVariable("id") Long id) {
        logger.info("Ban Blog with id {}", id);
        NucDidaBlog blog = sysBlogService.getBlogById(id);
        if(blog == null) {
            logger.error("Unable to ban. Blog with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to ban. Blog with " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        blog.setStatus(0);
        sysBlogService.updateBlog(blog);
        return new ResponseEntity<NucDidaBlog>(blog, HttpStatus.OK);
    }
}

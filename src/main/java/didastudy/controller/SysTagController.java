package didastudy.controller;

import didastudy.entity.NucDidaTag;
import didastudy.service.SysTagService;
import didastudy.util.CustomErrorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@RequestMapping("/system/systag")
@Api(value = "/systag", description = "标签管理")
public class SysTagController {

    public static final Logger logger = LoggerFactory.getLogger(SysTagController.class);

    @Autowired
    private SysTagService sysTagService;

    @ApiOperation(value = "获取标签信息列表", notes = "")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping("/tag")
    public ResponseEntity<List<NucDidaTag>> getAllTag() {
        List<NucDidaTag> tag = sysTagService.getAllTag();
        if(tag.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<NucDidaTag>>(tag, HttpStatus.OK);
    }

    @ApiOperation(value = "获取标签信息", notes = "根据标签id获取标签信息")
    @ApiImplicitParam(name = "id", value = "标签ID", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @GetMapping("/tag/{id}")
    public ResponseEntity<?> getTagById(@PathVariable("id") Long id) {
        logger.info("Fetching Tag with id {}", id);
        NucDidaTag tag = sysTagService.getTagById(id);
        if(tag == null) {
            logger.error("Tag with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Tag with id " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<NucDidaTag>(tag, HttpStatus.OK);
    }

    @ApiOperation(value = "添加标签", notes = "")
    @ApiImplicitParam(name = "tag", value = "标签实体tag", required = true, dataType = "NucDidaTag")
    @RequiresAuthentication
    @RequiresRoles("2")
    @PostMapping("/tag")
    public ResponseEntity<?> insertTag(@RequestBody NucDidaTag tag, UriComponentsBuilder builder) {
        logger.info("Creating Tag: {}", tag);
        if(sysTagService.isTagExist(tag)) {
            logger.error("Unable to create. Tag with id {} already exist", tag.getId());
            return new ResponseEntity<>(new CustomErrorType("Unable to create. Tag with id " + tag.getId() +
                    " already exist"), HttpStatus.CONFLICT);
        }
        sysTagService.saveTag(tag);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/systag/tag/{id}").buildAndExpand(tag.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "修改标签信息", notes = "根据标签id修改标签信息")
    @ApiImplicitParam(name = "id", value = "标签ID", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @PutMapping("/tag/{id}")
    public ResponseEntity<?> updateTagById(@PathVariable("id") Long id, @RequestBody NucDidaTag tag) {
        logger.info("Updating Tag with id {}", id);
        NucDidaTag currentTag = sysTagService.getTagById(id);
        if(currentTag == null) {
            logger.error("Unable to update. Tag with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to update. Tag with id " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        currentTag.setName(tag.getName());
        currentTag.setPath(tag.getPath());
        currentTag.setInClass(tag.getInClass());
        currentTag.setIsSystem(tag.getIsSystem());
        currentTag.setRank(tag.getRank());
        sysTagService.updateTag(currentTag);
        return new ResponseEntity<NucDidaTag>(currentTag, HttpStatus.OK);
    }

    @ApiOperation(value = "删除标签", notes = "根据标签id删除标签")
    @ApiImplicitParam(name = "id", value = "标签ID", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("2")
    @DeleteMapping("/tag/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id) {
        logger.info("Deleting Tag with id {}", id);
        NucDidaTag tag = sysTagService.getTagById(id);
        if(tag == null) {
            logger.error("Unable to delete. Tag with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to delete. Tag with " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        sysTagService.deleteTag(id);
        return new ResponseEntity<NucDidaTag>(HttpStatus.NO_CONTENT);
    }
}

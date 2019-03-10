package didastudy.controller;

import didastudy.entity.NucDidaAccusation;
import didastudy.service.SysAccusationService;
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
@RequestMapping(value = "/system/sysaccusation")
@Api(value = "/sysaccusation", description = "举报管理")
public class SysAccusationController {

    public static final Logger logger = LoggerFactory.getLogger(SysAccusationController.class);

    @Autowired
    private SysAccusationService sysAccusationService;

    @ApiOperation(value = "获取举报信息列表", notes="")
    @RequiresAuthentication
    @RequiresRoles("3")
    @GetMapping("/accusation")
    public ResponseEntity<List<NucDidaAccusation>> getAllAccusation() {
        List<NucDidaAccusation> accusation = sysAccusationService.listAccusation();
        if(accusation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<NucDidaAccusation>>(accusation, HttpStatus.OK);
    }

    @ApiOperation(value = "删除举报信息", notes = "根据举报信息id对其删除")
    @ApiImplicitParam(name = "id", value = "举报信息id", required = true, dataType = "Long", paramType = "path")
    @RequiresAuthentication
    @RequiresRoles("3")
    @DeleteMapping("/accusation/{id}")
    public ResponseEntity<?> deleteAccusation(@PathVariable Long id) {
        logger.info("Deleting Accusation with id {}", id);
        NucDidaAccusation accusation = sysAccusationService.getAccusationById(id);
        if(accusation == null) {
            logger.error("Unable to delete. Accusation with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to delete. Accusation with " + id +  " not found"),
                    HttpStatus.NOT_FOUND);
        }
        sysAccusationService.deleteAccusation(id);
        return new ResponseEntity<NucDidaAccusation>(HttpStatus.NO_CONTENT);
    }
}

package didastudy.entity;

import java.util.Date;
import java.util.List;

public class NucDidaPermission {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String permission;

    private List<NucDidaRole> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public List<NucDidaRole> getRoles() {
        return roles;
    }

    public void setRoles(List<NucDidaRole> roles) {
        this.roles = roles;
    }
}
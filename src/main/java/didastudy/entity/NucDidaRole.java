package didastudy.entity;

import java.util.Date;
import java.util.List;

public class NucDidaRole {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String role;

    private List<NucDidaUser> users;

    private List<NucDidaPermission> permissions;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<NucDidaUser> getUsers() {
        return users;
    }

    public void setUsers(List<NucDidaUser> users) {
        this.users = users;
    }

    public List<NucDidaPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<NucDidaPermission> permissions) {
        this.permissions = permissions;
    }
}
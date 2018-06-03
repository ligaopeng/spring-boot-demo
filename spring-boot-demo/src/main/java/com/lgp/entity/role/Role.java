package com.lgp.entity.role;

import java.io.Serializable;
import java.util.Date;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-02 22:05
 */
public class Role implements Serializable {

    private Integer id;

    private String roleName;

    private String roleFlag;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(String roleFlag) {
        this.roleFlag = roleFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

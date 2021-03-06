package com.lgp.dto.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-06 17:19
 */
public class RoleDTO implements Serializable {

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

    public String getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(String roleFlag) {
        this.roleFlag = roleFlag;
    }
}

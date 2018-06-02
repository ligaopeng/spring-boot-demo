package com.lgp.dto.user;

import com.lgp.entity.role.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-06 17:18
 */
public class UserDTO implements Serializable {

    private Integer id;

    private String loginName;

    private String pwd;

    private Date createTime;

    private List<Role> roleList;

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}

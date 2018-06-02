package com.lgp.entity.user;

import com.lgp.entity.role.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-27 22:59
 */
@ApiModel("用户基本信息")
public class User implements Serializable {


    private Integer id;

    @ApiModelProperty("登录名")
    private String loginName;

    @ApiModelProperty("密码")
    private String pwd;

    private Date createTime;

    private List<Role> roleList;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

}

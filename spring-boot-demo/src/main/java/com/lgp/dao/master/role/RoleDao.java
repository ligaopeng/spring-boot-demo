package com.lgp.dao.master.role;

import com.lgp.entity.role.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-02 22:23
 */
@Repository
public interface RoleDao {

    List<Role> listRole(@Param("userId") Integer userId);
}

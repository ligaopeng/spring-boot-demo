package com.lgp.dao.master.user;

import com.lgp.entity.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-27 23:04
 */
@Repository
public interface UserDao {

    User getUser(Integer id);

    int saveUser(User user);

    int deleteUser(Integer id);

    List<User> listUser();

    User getUserByUsername(@Param("username") String username);
}

package com.lgp.service;

import com.github.pagehelper.PageInfo;
import com.lgp.entity.user.User;
import com.lgp.repository.customer.Customer;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-27 23:09
 */
public interface UserService {

    User getUser(Integer id);

    List<User> listUser();

    User saveUser(User user);

    void deleteUser(Integer id);

    List<Customer> testMongodb(Customer customer);

    PageInfo<User> getPageInoByUser(Integer pageSize, Integer pageNum);

}

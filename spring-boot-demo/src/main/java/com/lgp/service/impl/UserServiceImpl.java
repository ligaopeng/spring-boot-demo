package com.lgp.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgp.dao.master.role.RoleDao;
import com.lgp.dao.master.user.UserDao;
import com.lgp.entity.role.Role;
import com.lgp.entity.user.User;
import com.lgp.repository.CustomerRepository;
import com.lgp.repository.RedisRepository;
import com.lgp.repository.customer.Customer;
import com.lgp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-27 23:25
 */
@Service
public class UserServiceImpl implements UserService {

    protected static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RedisRepository redisRepository;

    @Override
    @Cacheable(value = "user", key = "#id.toString()")
    public User getUser(Integer id) {
        User user = userDao.getUser(id);
        log.info("为id、key为" + id + "数据做了缓存");
        return user;
    }

    @Override
    public List<User> listUser() {
        List<User> users;
        if (!redisRepository.exists("user")) {
            users = userDao.listUser();
            for (User user : users) {
                List<Role> roles = roleDao.listRole(user.getId());
                user.setRoleList(roles);
            }
            redisRepository.set("user", users, 1000l);
        } else {
            users = (List<User>) redisRepository.get("user");
        }
        return users;
    }

    @Override
    @CachePut(value = "user", key = "#user.id")//缓存新增的或更新的数据到缓存，其中缓存名称为user, key为ID
    @Transactional
    public User saveUser(User user) {
        userDao.saveUser(user);
        log.info("为id:{}, loginName:{} 做了缓存", user.getId(), user.getLoginName());
        return user;
    }

    @Override
    @CacheEvict(value = "user", key = "#id.toString()")//从缓存user中删除key为ID的数据
    @Transactional
    public void deleteUser(Integer id) {
        log.info("删除了Id为 {} 数据缓存", id);
        userDao.deleteUser(id);
    }


    @Override
    @Transactional
    public List<Customer> testMongodb(Customer customer) {
        //删除所有
        customerRepository.deleteAll();
        //添加customer
        customerRepository.save(customer);
        //查询全部
        List<Customer> all = customerRepository.findAll();
        log.info("所有customer:{}", JSON.toJSONString(all));
        return all;
    }


    //@ReadOnlyConnection 只读注解
    //@Transactional
    public PageInfo<User> getPageInoByUser(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.listUser();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }


}

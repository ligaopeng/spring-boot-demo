package com.lgp.service.impl;

import com.lgp.dao.master.user.UserDao;
import com.lgp.dao.master.role.RoleDao;
import com.lgp.entity.role.Role;
import com.lgp.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-02 23:17
 */
@Service
public class UserRoleService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user != null) {
            List<Role> roles = roleDao.listRole(user.getId());
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getRoleFlag()));
            }
            user.setRoleList(roles);
        } else {
            throw new UsernameNotFoundException("未查到" + username + "信息");
        }
        return new org.springframework.security.core.userdetails.User(user.getLoginName(), user.getPwd(), authorities);
    }
}

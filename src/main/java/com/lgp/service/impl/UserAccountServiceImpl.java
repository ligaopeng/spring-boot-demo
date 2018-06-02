package com.lgp.service.impl;

import com.lgp.dao.cluster.UserAccountDao;
import com.lgp.entity.useraccount.UserAccount;
import com.lgp.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-27 23:29
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    UserAccountDao userAccountDao;

    @Override
    public UserAccount getUserAccount(Integer id) {
        return userAccountDao.getUserAccount(id);
    }
}

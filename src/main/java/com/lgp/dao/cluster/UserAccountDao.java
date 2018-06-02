package com.lgp.dao.cluster;

import com.lgp.entity.useraccount.UserAccount;
import org.springframework.stereotype.Repository;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-27 23:05
 */
@Repository
public interface UserAccountDao {

    UserAccount getUserAccount(Integer id);
}

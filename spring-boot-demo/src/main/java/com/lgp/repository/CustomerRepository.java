package com.lgp.repository;

import com.lgp.repository.customer.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-30 15:29
 */
public interface CustomerRepository extends MongoRepository<Customer, Integer> {
}

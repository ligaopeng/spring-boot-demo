package com.lgp.controller.user;

import com.github.pagehelper.PageInfo;
import com.lgp.common.util.LoggerUtils;
import com.lgp.dto.user.UserDTO;
import com.lgp.entity.user.User;
import com.lgp.entity.useraccount.UserAccount;
import com.lgp.exception.ApiResult;
import com.lgp.exception.ApiResultGenerator;
import com.lgp.mapstruct.UserMapper;
import com.lgp.repository.customer.Customer;
import com.lgp.service.UserAccountService;
import com.lgp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-27 23:31
 */
@Api(tags = "用户信息")
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UserMapper userMapper;

    @GetMapping(value = "getUser")
    public User getUser(Integer id) {
        User user = userService.getUser(id);
        return user;
    }

    @GetMapping(value = "listUser")
    public ApiResult lisUser() {
        List<User> users = userService.listUser();
        ApiResult apiResult = ApiResultGenerator.successResult(users);
        return apiResult;
    }


    @PostMapping(value = "saveUser")
    public User saveUser(@RequestBody User user, HttpServletRequest request) {
        User u = userService.saveUser(user);
        request.setAttribute(LoggerUtils.LOGGER_RETURN, u);
        return u;
    }

    @ApiIgnore
    @DeleteMapping(value = "deleteUser")
    public void deleteUser(@ApiParam("id") Integer id) {
        userService.deleteUser(id);
    }


    @GetMapping(value = "getUserAccount")
    public UserAccount getUserAccount(Integer id) {
        UserAccount userAccount = userAccountService.getUserAccount(id);
        return userAccount;
    }


    @PostMapping(value = "testMongodb")
    public List<Customer> testMongodb(@RequestBody Customer customer) {
        List<Customer> customers = userService.testMongodb(customer);
        return customers;
    }

    @ApiOperation("用户列表")
    @GetMapping(value = "listUserDTO")
    public List<UserDTO> listUserDTO() {
        List<User> users = userService.listUser();
        List<UserDTO> userDTOS = userMapper.fromUserList(users);
        return userDTOS;
    }

    @ApiOperation("用户列表-分页")
    @GetMapping(value = "pageUser")
    public PageInfo<User> pageUser(@ApiParam("查看第几页") Integer pageNum, @ApiParam("每页多少条") Integer pageSize) {
        PageInfo<User> pageInoByUser = userService.getPageInoByUser(pageSize, pageNum);
        return pageInoByUser;
    }

}

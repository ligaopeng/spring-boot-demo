package com.lgp.mapstruct;

import com.lgp.dto.user.RoleDTO;
import com.lgp.dto.user.UserDTO;
import com.lgp.entity.role.Role;
import com.lgp.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-06 17:14
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "user.id", target = "id"),
            @Mapping(source = "user.loginName", target = "loginName"),
            @Mapping(source = "user.pwd", target = "pwd"),
            @Mapping(source = "user.createTime", target = "createTime")
    })
    UserDTO fromUser(User user);

    @Mappings({
            @Mapping(source = "role.id", target = "id"),
            @Mapping(source = "role.roleName", target = "roleName"),
            @Mapping(source = "role.roleFlag", target = "roleFlag"),
            @Mapping(source = "role.createTime", target = "createTime")
    })
    RoleDTO fromRole(Role role);


    List<UserDTO> fromUserList(List<User> userList);
}

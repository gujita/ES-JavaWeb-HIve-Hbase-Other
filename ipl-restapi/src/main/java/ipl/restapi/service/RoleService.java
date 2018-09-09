package ipl.restapi.service;

import ipl.manager.pojo.Role;

import java.util.List;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.service
 * @date 2018/3/23 9:29
 * @since api1.0
 */
public interface RoleService {
    //得到所有角色信息，返回角色列表
    List<Role> getAllRole();

    //根据角色id返回角色信息
    Role getRoleById(Short roleId);

    //根据角色id删除角色
    void deleteRoleById(Short id);

    //返回角色ID的最小值
    int getMinRoleId();

    void insertRole(Role role);

    void updateRole(Role role);
}

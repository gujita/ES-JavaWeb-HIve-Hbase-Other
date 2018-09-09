package ipl.restapi.service.impl;

import ipl.manager.mapper.RoleMapper;
import ipl.manager.pojo.Role;
import ipl.restapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.service.impl
 * @date 2018/3/23 9:29
 * @since api1.0
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private RoleMapper roleMapper;

    /**
     * 获取所有角色信息
     * @return 角色列表
     */
    @Override
    public List<Role> getAllRole(){
        List<Role> role = roleMapper.selectAll();
        return role;
    }

    /**
     * 根据ID查找角色信息
     * @param id 角色ID
     * @return 角色对象
     */
    @Override
    public Role getRoleById(Short id){
        Role role = roleMapper.selectByPrimaryKey(id);
        return role;
    }

    /**
     * 获取角色ID的最小值
     * @return 最小ID
     */
    @Override
    public int getMinRoleId(){
        Role role = roleMapper.getMinId();
        int index = role.getId();
        return index;
    }

    /**
     * 根据角色ID删除角色
     * @param id 角色ID
     */
    @Override
    public void deleteRoleById(Short id){
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertRole(Role role){
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role){
        roleMapper.updateByPrimaryKey(role);
    }

}

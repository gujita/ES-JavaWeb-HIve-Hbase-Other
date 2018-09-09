package ipl.manager.mapper;

import ipl.manager.pojo.Role;

import java.util.List;

public interface RoleMapper {
    //根据角色ID删除角色
    int deleteByPrimaryKey(Short id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Short id);

    //返回角色信息
    List<Role> selectAll();

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    //得到最小ID
    Role getMinId();
}
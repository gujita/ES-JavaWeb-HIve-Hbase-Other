package ipl.manager.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ipl.manager.pojo.RoleSearchExample;
import ipl.manager.pojo.RoleSearchKey;

public interface RoleSearchMapper {
    int countByExample(RoleSearchExample example);

    int deleteByExample(RoleSearchExample example);

    int deleteByPrimaryKey(RoleSearchKey key);

    int deleteByRoleId(Short key);

    int insert(RoleSearchKey record);

    int insertSelective(RoleSearchKey record);

    List<RoleSearchKey> selectByExample(RoleSearchExample example);

    int updateByExampleSelective(@Param("record") RoleSearchKey record, @Param("example") RoleSearchExample example);

    int updateByExample(@Param("record") RoleSearchKey record, @Param("example") RoleSearchExample example);

    int updateByPrimaryKey(RoleSearchKey roleSearchKey);
}
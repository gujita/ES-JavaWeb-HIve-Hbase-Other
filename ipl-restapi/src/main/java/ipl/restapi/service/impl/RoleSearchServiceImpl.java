package ipl.restapi.service.impl;

import ipl.manager.mapper.RoleMapper;
import ipl.manager.mapper.RoleSearchMapper;
import ipl.manager.pojo.Role;
import ipl.manager.pojo.RoleSearchKey;
import ipl.restapi.service.RoleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.service.impl
 * @date 2018/3/23 14:56
 * @since api1.0
 */
@Service
public class RoleSearchServiceImpl implements RoleSearchService{

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private RoleSearchMapper roleSearchMapper;

    @Override
    public void delByRoleId(Short key){
        roleSearchMapper.deleteByRoleId(key);
    }

    @Override
    public void insertRoleSearchKey(RoleSearchKey roleSearchKey){
        roleSearchMapper.insert(roleSearchKey);
    }
}

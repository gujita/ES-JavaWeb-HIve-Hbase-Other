package ipl.restapi.service;

import ipl.manager.pojo.RoleSearchKey;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.service
 * @date 2018/3/23 14:55
 * @since api1.0
 */
public interface RoleSearchService {
    void delByRoleId(Short key);
    void insertRoleSearchKey(RoleSearchKey roleSearchKey);
}

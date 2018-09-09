package ipl.restapi.service;

import ipl.manager.pojo.UserInfo;

import java.util.List;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.restapi.service
 * @date 2018/3/16 21:09
 * @since api1.0
 */
public interface UsersService {
    /**
     * 通过用户id查询User所有数据的接口
     *
     * @param userId 用户id
     * @return 用户
     */
    UserInfo getUserById(long userId);

    /**
     * 所有角色升一级
     * @param identity
     */
    void updateIndentityByIndentityUP(Short identity);

    /**
     * 所有角色降一级
     * @param identity
     */
    void updateIndentityByIndentityDOWN(Short identity);

    List<UserInfo> getAllUsers();

}

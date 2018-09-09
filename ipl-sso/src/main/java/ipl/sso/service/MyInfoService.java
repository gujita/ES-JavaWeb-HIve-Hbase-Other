package ipl.sso.service;

import ipl.manager.pojo.UserInfo;

/**
 * <p>Descirption:通过token解析出用户的email信息，再根据用户email查询User所有数据</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.service
 * @date 2018/3/26 16:37
 * @since api1.0
 */
public interface MyInfoService {
    /**
     * 通过token解析出用户的email信息，再根据用户email查询User所有数据的接口
     *
     * @param id 用户email
     * @return 用户
     */
    UserInfo getUserInfoById(long id);
}

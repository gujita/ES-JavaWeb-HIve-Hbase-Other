package ipl.sso.service;

import ipl.manager.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Descirption:用户登录接口</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.service
 * @date 2018/3/22 19:36
 * @since api1.0
 */
public interface UserLoginRegistService {
    String userLogin(String email, String password, HttpServletRequest request, HttpServletResponse response);

    String createUser(UserInfo user);
}

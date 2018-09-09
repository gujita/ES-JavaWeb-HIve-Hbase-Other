package ipl.sso.service;

/**
 * <p>Descirption: 注册时的用户信息验证接口</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.service
 * @date 2018/3/19 22:20
 * @since api1.0
 */
public interface UserValidatService {
    String validateUserInfo(String validateValue, int type);

}

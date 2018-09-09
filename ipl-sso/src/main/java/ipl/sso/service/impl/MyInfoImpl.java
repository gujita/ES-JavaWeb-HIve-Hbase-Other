package ipl.sso.service.impl;

import ipl.manager.mapper.UserInfoMapper;
import ipl.manager.pojo.UserInfo;
import ipl.sso.service.MyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.service.impl
 * @date 2018/3/26 16:40
 * @since api1.0
 */
@Service
public class MyInfoImpl implements MyInfoService{
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoById(long id) {
        UserInfo user = userInfoMapper.selectByPrimaryKey(id);
        return user;
    }
}

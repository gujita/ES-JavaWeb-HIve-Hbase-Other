package ipl.restapi.service.impl;

import ipl.manager.mapper.UserInfoMapper;
import ipl.manager.pojo.UserInfo;
import ipl.restapi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.restapi.service.impl
 * @date 2018/3/16 21:13
 * @since api1.0
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserInfoMapper userInfoMapper;

    @Override
    // 这里的itemId参数看起来是“死”的，实际在controller中，使用@RequestMapping以及@PathVariable“转活”
    public UserInfo getUserById(long userId) {
//        UsersExample usersExample = new UsersExample();
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        return userInfo;
    }

    /**
     * 所有角色升一级
     * @param indentity 传入原来角色的ID
     */
    @Override
    public void updateIndentityByIndentityUP(Short indentity){
        userInfoMapper.updateIdentityByIdentityUP(indentity);
    }

    /**
     * 所有角色降一级
     * @param indentity 传入原来角色的ID
     */
    @Override
    public void updateIndentityByIndentityDOWN(Short indentity){
        userInfoMapper.updateIdentityByIdentityDOWN(indentity);
    }

    @Override
    public List<UserInfo> getAllUsers(){
        List<UserInfo> userInfo = userInfoMapper.getAllUser();
        return userInfo;
    }

}

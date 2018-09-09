package ipl.sso.service.impl;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.common.utils.StackTraceToString;
import ipl.manager.mapper.UserInfoMapper;
import ipl.manager.pojo.UserInfo;
import ipl.manager.pojo.UserInfoExample;
import ipl.sso.service.UserLoginRegistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * <p>Descirption:用户登录以及token，用户注册的实现</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.service.impl
 * @date 2018/3/22 19:51
 * @since api1.0
 */
@Service
public class UserLoginRegistServiceImpl implements UserLoginRegistService {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserLoginRegistServiceImpl.class);

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserInfoMapper userInfoMapper;

    @Override
    public String userLogin(String email, String password, HttpServletRequest request, HttpServletResponse response) {
        // 此情况几乎不可能出现，所以没写入API接口文档
        if (email == null || password == null) {
            LOGGER.info("缺少信息{}", email);
            return JacksonUtil.bean2Json(ResultFormat.build("101", "登录失败，必须提供用户邮箱和密码", 1, "login", null));
        }
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        criteria.andEmailEqualTo(email);

        List<UserInfo> list = userInfoMapper.selectByExample(userInfoExample);
        if (list.size() != 1) {
            System.out.println("邮箱参数========：" + email);
            LOGGER.info("没有邮箱为：{}的用户", email);
            return JacksonUtil.bean2Json(ResultFormat.build("101", "登录失败，无此邮箱", 1, "login", null));
        }
        UserInfo user = list.get(0);
        String requestPass = password.trim();
        // 接下来验证密码。md5算法。!DigestUtils.md5DigestAsHex(password.getBytes())
        if (!DigestUtils.md5DigestAsHex(requestPass.getBytes()).equals(user.getPassword())) {
            return JacksonUtil.bean2Json(ResultFormat.build("101", "登录失败，用户密码不正确", 1, "login", null));
        }
        user.setLastLoginTime(user.getLoginTime());
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());*/
        user.setLoginTime(new Date());
        user.setLoginCount(user.getLoginCount() + 1);
        userInfoMapper.loginUpdate(user);
        LOGGER.info("用户\t{}第\t{}次登录",email,user.getLoginCount());
        // 把用户对象中的密码清空。
        user.setPassword(null);

/*        // 生成token并用cookie携带
        PlayLoadHelper playLoadHelper = new PlayLoadHelper(new Date(), email);
        String token = JWTManager.createToken(playLoadHelper, 90000);
        System.out.println("response中的token=======" + token);
//        response.addHeader("Set-Cookie", "uid=112; Path=/; HttpOnly");
        Cookie cookie = new Cookie("access_token", token);

        response.addCookie(cookie);*/

        //创建session对象
        HttpSession session = request.getSession();
        //把用户数据保存在session域对象中
        session.setAttribute("sessionid", user.getId());

        return JacksonUtil.bean2Json(ResultFormat.build("100", "登录成功", 0, "login", null));
    }

    @Override
    public String createUser(UserInfo user) {
        // 此情况几乎不可能出现，所以没写入API接口文档
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            return JacksonUtil.bean2Json(ResultFormat.build("107", "username,email,password没有完整提供", 1, "register", null));
        }
//        设置注册时间
        user.setRegistTime(new Date());
//        设置本次登录时间
        user.setLoginTime(new Date());
//        设置上次登录时间
        user.setLastLoginTime(new Date());
//        设置登录次数
        user.setLoginCount(1);
        user.setIdentity((short) 1);
        // md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        try {
            userInfoMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(StackTraceToString.getStackTraceString(e));
            return JacksonUtil.bean2Json(ResultFormat.build("107", "注册失败，请联系站长", 1, "register", null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("106", "注册成功", 0, "register", null));
    }
}

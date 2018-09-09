package ipl.sso.controller;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.common.utils.StackTraceToString;
import ipl.manager.pojo.UserInfo;
import ipl.sso.service.MyInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.controller
 * @date 2018/3/26 16:51
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class MyInfoController {
    public static final Logger LOGGER = LoggerFactory.getLogger(MyInfoController.class);
    @Autowired
    private MyInfoService myInfoService;

    // 获取用户自身信息[不允许POST方法]
    @RequestMapping(value = "/myinfo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String getMyInfo(HttpServletRequest request) {
        // 得到session对象(若存在会话则返回该会话,否则新建一个会话————不用判空)
        HttpSession session = request.getSession(true);
        // 如果session中没有id的值
        if (session.getAttribute("sessionid") == null) {
            return JacksonUtil.bean2Json(ResultFormat.build("109", "用户未登录", 1, "myinfo", null));
        }
        // 有id值，转为long型到数据库查询
        long id = Long.parseLong(String.valueOf(session.getAttribute("sessionid")));
        if (id <= 10) {
            LOGGER.warn("id：{}不存在,用户虚假cookie请求“我的信息”，已拦截。", id);
            return JacksonUtil.bean2Json(ResultFormat.build("109", "用户未登录,数据库中没有对应id的用户", 1, "myinfo", null));
        }
        try {
            UserInfo user = myInfoService.getUserInfoById(id);
            user.setPassword("不可见");
            String dataString = JacksonUtil.bean2Json(user);
            return JacksonUtil.bean2Json(ResultFormat.build("102", "获取用户信息成功", 0, "myinfo", dataString));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("用户,{}获取自己信息失败========{}", id, StackTraceToString.getStackTraceString(e));
            return JacksonUtil.bean2Json(ResultFormat.build("103", "获取用户信息失败，请联系站长", 1, "myinfo", null));
        }
    }
}

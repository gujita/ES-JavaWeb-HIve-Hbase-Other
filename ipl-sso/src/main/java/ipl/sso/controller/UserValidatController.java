package ipl.sso.controller;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.common.utils.StackTraceToString;
import ipl.sso.enums.UserValidatiEnum;
import ipl.sso.service.UserValidatService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.controller
 * @date 2018/3/20 20:02
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserValidatController {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserValidatController.class);
    @Autowired
    private UserValidatService userValidatService;

    @RequestMapping(value = "/checkinfo", produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @ResponseBody
    public String checkData(@RequestParam(value = "checkvalue") String param, @RequestParam(value = "type") Integer type) {
// @RequestParam 是从request里面拿取值，而 @PathVariable 是从一个URI模板里面来填充
        String result = null;

        //参数有效性校验
        if (StringUtils.isBlank(param) || type.toString() == null) {
            result = JacksonUtil.bean2Json(ResultFormat.build("105", "校验值/检验类型不能为空", 1, "check", null));
        }
        // 暂时去掉： type != UserValidatiEnum.USER_PHONE.getType() &&
        if (type != UserValidatiEnum.USER_NAME.getType() && type != UserValidatiEnum.USER_EMAIL.getType()) {
            result = JacksonUtil.bean2Json(ResultFormat.build("105", "校验值的类型错误,1-username;3-email", 1, "check", null));
        }
        // 如果此时result中就已经有值，那么校验出错,不再调用服务。
        if (null != result) {
            return result;
        }
        // 调用服务
        try {
            result = userValidatService.validateUserInfo(param, type);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(StackTraceToString.getStackTraceString(e));
            result = JacksonUtil.bean2Json(ResultFormat.build("500", "服务器维护，请联系站长", 1, "check", null));
        }
        return result;
    }

}

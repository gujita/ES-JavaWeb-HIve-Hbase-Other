package ipl.restapi.controller;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.manager.pojo.UserInfo;
import ipl.restapi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.restapi.controller
 * @date 2018/3/16 22:18
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*",methods = {GET,POST},maxAge=3600)
public class UserController {
    @Autowired
    private UsersService usersService;

    // 可以匹配多个value,produces属性避免乱码
    @RequestMapping(value = "/users/{userId}", method = {GET,POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public String getUserJson(@PathVariable long userId) {
        UserInfo user = usersService.getUserById(userId);
        //  取出user的所有属性，解析为前端所需要的json格式，返回给前端
        String userJson = JacksonUtil.bean2Json(user);
        return userJson;
    }

    @RequestMapping(value = "/users/all", method = {GET,POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public String getAllUserJson(){
        List<UserInfo> userInfo = null;
        try{
            //返回所有角色信息
            userInfo = usersService.getAllUsers();
       }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("202","返回数据失败",1,"user",null));
        }
        //将角色信息变为JSON格式赋值给roleJson

        return JacksonUtil.bean2Json(ResultFormat.build("201", "返回数据成功", 0, "user", userInfo));

    }
}
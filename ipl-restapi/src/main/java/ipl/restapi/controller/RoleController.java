package ipl.restapi.controller;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.manager.pojo.Role;
import ipl.manager.pojo.RoleSearchKey;
import ipl.restapi.service.RoleSearchService;
import ipl.restapi.service.RoleService;
import ipl.restapi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.controller
 * @date 2018/3/23 9:27
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*",methods = {GET,POST},maxAge=3600)
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RoleSearchService roleSearchService;

    // 可以匹配多个value,produces属性避免乱码
    @RequestMapping(value = "/roles/all", method = {GET,POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public String getALLRoleJson() {
        List<Role> role;
        try{
            //返回所有角色信息
            role = roleService.getAllRole();
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("202","返回数据失败",1,"role",null));
        }
        //将角色信息变为JSON格式赋值给roleJson
        return JacksonUtil.bean2Json(ResultFormat.build("201", "返回数据成功", 0, "role", role));
    }

    // 可以匹配多个value,produces属性避免乱码
    @RequestMapping(value = "/roles", method = {GET,POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public String getRoleByIdJson(@RequestParam String roleId) {
        Role role;
        Short rId = new Short(roleId);
        try{
            //得到角色信息
            role = roleService.getRoleById(rId);
        }catch(Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("202","返回数据失败",1,"role",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("201", "返回数据成功", 0, "role", role));
    }

    // 可以匹配多个value,produces属性避免乱码
    @RequestMapping(value = "/roles/delete/{roleId}", method = {GET,POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public String deleteRoleJson(@PathVariable Short roleId) {
        try{
            /**
             * if待删除的角色id等于最小的角色id则所有角色升一级
             * 否则所有角色降一级
             */
            if(roleId == roleService.getMinRoleId()){
                //所有用户升一级
                usersService.updateIndentityByIndentityUP(roleId);
            }else{
                //所有用户降一级
                usersService.updateIndentityByIndentityDOWN(roleId);
            }
            //删除role_search表中的对应
            roleSearchService.delByRoleId(roleId);

            //删除角色
            roleService.deleteRoleById(roleId);
        }catch(Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("204", "删除失败",1 , "role", null));
        }
        List<Role> role;
        try{
            //返回所有角色信息
            role = roleService.getAllRole();
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("207","删除成功返回角色所有信息时失败",1,"role",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("208", "删除成功返回角色所有信息时成功", 0, "role", role));
    }

    // 可以匹配多个value,produces属性避免乱码
    @RequestMapping(value = "/roles/add",
            method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    @ResponseBody
    public String addRole(@RequestParam(value = "roleId") Short roleId, @RequestParam(value = "roleName") String roleName, @RequestParam(value = "roleSearchCount") int roleSearchCount, @RequestParam(value = "index1") Short index1, @RequestParam(value = "index2") Short index2, @RequestParam(value = "index3") Short index3){
        Short i1=1,i2=2,i3=3;
        Role role = new Role();
        RoleSearchKey roleSearchKey = new RoleSearchKey();
        role.setId(roleId);
        role.setName(roleName);
        role.setSearchCount(roleSearchCount);
        try{
            roleService.insertRole(role);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("205", "插入角色信息失败",1 , "role", null));
        }
        try{
            if(index1 == i1){
                roleSearchKey.setSearchId(index1);
                roleSearchKey.setRoleId(roleId);
                roleSearchService.insertRoleSearchKey(roleSearchKey);
            }
            if(index2 == i1){
                roleSearchKey.setSearchId(i2);
                roleSearchKey.setRoleId(roleId);
                roleSearchService.insertRoleSearchKey(roleSearchKey);
            }
            if(index3 == i1){
                roleSearchKey.setSearchId(i3);
                roleSearchKey.setRoleId(roleId);
                roleSearchService.insertRoleSearchKey(roleSearchKey);
            }
        }catch(Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("206", "分配角色检索权限",1 , "role", null));
        }
        List<Role> rolel;
        try{
            //返回所有角色信息
            rolel = roleService.getAllRole();
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("209","添加角色成功返回所有信息时失败",1,"role",null));
        }
        //将角色信息变为JSON格式赋值给roleJson
        return JacksonUtil.bean2Json(ResultFormat.build("210", "添加角色成功返回所有信息时成功", 0, "role", rolel));
    }

    @RequestMapping(value = "/roles/update",
            method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public String updateRole(@RequestParam(value = "roleId") Short roleId, @RequestParam(value = "roleName") String roleName, @RequestParam(value = "roleSearchCount") int roleSearchCount, @RequestParam(value = "index1") Short index1, @RequestParam(value = "index2") Short index2, @RequestParam(value = "index3") Short index3){

        Short i1 = 1,i2 = 2,i3 = 3;
        Role role = new Role();
        RoleSearchKey roleSearchKey = new RoleSearchKey();
        role.setId(roleId);
        role.setName(roleName);
        role.setSearchCount(roleSearchCount);
        roleSearchKey.setRoleId(roleId);
        roleSearchService.delByRoleId(roleId);
        try{
            roleService.updateRole(role);
        }catch(Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("211", "更新角色信息出错", 1, "role", null));
        }
        try{
            if(index1 == i1){
                roleSearchKey.setSearchId(i1);
                roleSearchService.insertRoleSearchKey(roleSearchKey);
            }
            if(index2 == i1){
                roleSearchKey.setSearchId(i2);
                roleSearchService.insertRoleSearchKey(roleSearchKey);
            }
            if(index3 == i1){
                roleSearchKey.setSearchId(i3);
                roleSearchService.insertRoleSearchKey(roleSearchKey);
            }
        }catch(Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("212", "更新检索权限出错", 1, "role", null));
        }
        List<Role> roleArr;
        try{
            //返回所有角色信息
            roleArr = roleService.getAllRole();
        }catch(Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("213", "更新角色成功返回数据出错", 1, "role", null));
        }
        //将角色信息变为JSON格式赋值给roleJson
        return JacksonUtil.bean2Json(ResultFormat.build("214", "更新角色成功返回数据成功", 0, "role", roleArr));
    }
}

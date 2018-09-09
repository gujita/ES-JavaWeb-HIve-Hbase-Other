package ipl.restapi.controller;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.manager.pojo.Footprint;
import ipl.manager.pojo.FootprintKey;
import ipl.restapi.service.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.controller
 * @date 2018/3/24 15:41
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*",methods = {GET,POST},maxAge=3600)
public class FootprintController {

    @Autowired
    private FootprintService footprintService;

    @RequestMapping(value = "/footprint/{userId}", method = {GET,POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object getAllFootprint(@PathVariable Long userId){

        List<Footprint> footprint;
        try{
            footprint = footprintService.getAllFootprint(userId);
        }catch(Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("301","返回数据失败",1,"footprint",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("302","返回数据成功",0,"footprint",footprint));
    }

    @RequestMapping(value = "/footprint/insertBy{userId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object insertFootprintByuserId(@PathVariable Long userId,@RequestParam(value = "searchContent") String searchContent){
        Date searchTime = new Date();

        Footprint footprint = new Footprint();
        footprint.setSearchContent(searchContent);
        footprint.setUserId(userId);
        footprint.setSearchTime(searchTime);

        try{
            if(10 > footprintService.getFootprintcount(userId)){
                try {
                    footprintService.insertFootprintByUserId(footprint);
                } catch (Exception e) {
                    footprintService.updateByPK(footprint);
                }
            }else{
                Footprint footprint2 = new Footprint();
                footprint2.setSearchTime(footprintService.getMinTime(userId));
                footprint2.setUserId(userId);
                footprintService.delFPByIDAndTime(footprint2);
                try {
                    footprintService.insertFootprintByUserId(footprint);
                } catch (Exception e) {
                    footprintService.updateByPK(footprint);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("303","添加足迹失败",1,"footprint",null));
        }


        List<Footprint> footprints;
        try{
            footprints = footprintService.getAllFootprint(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("304","添加足迹成功返回数据失败",1,"footprint",null));
        }
        String data = JacksonUtil.bean2Json(footprints);
        return JacksonUtil.bean2Json(ResultFormat.build("305","添加足迹成功返回数据成功",0,"footprint",data));
    }

    @RequestMapping(value = "/footprint/delAllBy{userId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object delAllFootprintByuserId(@PathVariable Long userId){

        FootprintKey footprintKey = new FootprintKey();
        footprintKey.setUserId(userId);

        try{
            footprintService.delAllByUserId(footprintKey);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("309","清除足迹失败",1,"footprint",null));
        }

        List<Footprint> footprints;
        try{
            footprints = footprintService.getAllFootprint(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("310","清除足迹成功返回信息失败",1,"footprint",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("311","清除足迹成功返回信息成功",0,"footprint",footprints));
    }

    @RequestMapping(value = "/footprint/delBy{userId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object delFootprintByContent(@PathVariable Long userId,@RequestParam(value = "searchContent") String searchContent){

        FootprintKey footprintKey = new FootprintKey();
        footprintKey.setUserId(userId);
        footprintKey.setSearchContent(searchContent);

        try{
            footprintService.delByUserIdAndContent(footprintKey);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("306","删除单项足迹失败",1,"footprint",null));
        }
        List<Footprint> footprints;
        try{
            footprints = footprintService.getAllFootprint(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("307","删除单项足迹成功返回数据失败",1,"footprint",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("308","删除单项足迹成功返回数据成功",0,"footprint",footprints));
    }
}

package ipl.restapi.service;

import ipl.manager.pojo.Footprint;
import ipl.manager.pojo.FootprintKey;

import java.util.Date;
import java.util.List;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.service
 * @date 2018/3/24 15:43
 * @since api1.0
 */
public interface FootprintService {
    void insertFootprintByUserId(Footprint footprint);
    List<Footprint> getAllFootprint(Long userId);
    void delAllByUserId(FootprintKey footprintKey);
    void delByUserIdAndContent(FootprintKey footprintKey);
    int getFootprintcount(Long userId);
    void delFPByIDAndTime(Footprint footprint);
    Date getMinTime(Long userId);
    void updateByPK(Footprint footprint);
}

package ipl.restapi.service.impl;

import ipl.manager.mapper.FootprintMapper;
import ipl.manager.pojo.Footprint;
import ipl.manager.pojo.FootprintKey;
import ipl.restapi.service.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.service.impl
 * @date 2018/3/24 15:42
 * @since api1.0
 */
@Service
public class FootprintServiceImpl implements FootprintService{

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private FootprintMapper footprintMapper;

    @Override
    public List<Footprint> getAllFootprint(Long userId){
        List<Footprint> footprints = footprintMapper.getAllFootprintByUserId(userId);
        return footprints;
    }

    @Override
    public void insertFootprintByUserId(Footprint footprint){
        footprintMapper.insert(footprint);
    }

    @Override
    public void delAllByUserId(FootprintKey footprintKey){
        footprintMapper.deleteByUserId(footprintKey);
    }

    @Override
    public void delByUserIdAndContent(FootprintKey footprintKey){
        footprintMapper.deleteByPrimaryKey(footprintKey);
    }

    @Override
    public int getFootprintcount(Long userId){
        return footprintMapper.getFootprintCount(userId);
    }

    @Override
    public void delFPByIDAndTime(Footprint footprint){
        footprintMapper.delFootprintByuserIdAndTime(footprint);
    }

    @Override
    public Date getMinTime(Long userId){
        Date minTime = footprintMapper.getMinTime(userId);
        return minTime;
    }

    public void updateByPK(Footprint footprint){
        footprintMapper.updateByPrimaryKey(footprint);
    }
}

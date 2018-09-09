package ipl.manager.mapper;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import ipl.manager.pojo.Footprint;
import ipl.manager.pojo.FootprintExample;
import ipl.manager.pojo.FootprintKey;

public interface FootprintMapper {
    int countByExample(FootprintExample example);

    int deleteByExample(FootprintExample example);

    int deleteByPrimaryKey(FootprintKey key);

    int deleteByUserId(FootprintKey key);

    int insert(Footprint record);

    int insertSelective(Footprint record);

    List<Footprint> selectByExample(FootprintExample example);

    Footprint selectByPrimaryKey(FootprintKey key);

    int updateByExampleSelective(@Param("record") Footprint record, @Param("example") FootprintExample example);

    int updateByExample(@Param("record") Footprint record, @Param("example") FootprintExample example);

    int updateByPrimaryKeySelective(Footprint record);

    int updateByPrimaryKey(Footprint record);

    List<Footprint> getAllFootprintByUserId(Long userId);

    int getFootprintCount(Long userId);

    Date getMinTime(Long userId);

    int delFootprintByuserIdAndTime(Footprint footprint);
}
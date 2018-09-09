package ipl.manager.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ipl.manager.pojo.Collect;
import ipl.manager.pojo.CollectExample;
import ipl.manager.pojo.CollectKey;

public interface CollectMapper {
    int countByExample(CollectExample example);

    int deleteByExample(CollectExample example);

    int deleteByPrimaryKey(CollectKey key);

    int insert(Collect record);

    int insertSelective(Collect record);

    List<Collect> selectByExample(CollectExample example);

    Collect selectByPrimaryKey(CollectKey key);

    int updateByExampleSelective(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByExample(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    List<Collect> getAllCollectByUserId(Long userId);
}
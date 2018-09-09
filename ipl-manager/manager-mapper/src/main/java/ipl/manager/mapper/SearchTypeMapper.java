package ipl.manager.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ipl.manager.pojo.SearchType;
import ipl.manager.pojo.SearchTypeExample;

public interface SearchTypeMapper {
    int countByExample(SearchTypeExample example);

    int deleteByExample(SearchTypeExample example);

    int deleteByPrimaryKey(Short id);

    int insert(SearchType record);

    int insertSelective(SearchType record);

    List<SearchType> selectByExample(SearchTypeExample example);

    SearchType selectByPrimaryKey(Short id);

    int updateByExampleSelective(@Param("record") SearchType record, @Param("example") SearchTypeExample example);

    int updateByExample(@Param("record") SearchType record, @Param("example") SearchTypeExample example);

    int updateByPrimaryKeySelective(SearchType record);

    int updateByPrimaryKey(SearchType record);
}
package ipl.restapi.service.impl;

import ipl.manager.mapper.CollectMapper;
import ipl.manager.pojo.Collect;
import ipl.restapi.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.service.impl
 * @date 2018/3/24 14:14
 * @since api1.0
 */
@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private CollectMapper collectMapper;

    @Override
    public List<Collect> getAllCollect(Long userId){
        List<Collect> collect = collectMapper.getAllCollectByUserId(userId);
        return collect;
    }

    @Override
    public void insertByuserId(Collect collect){
        collectMapper.insert(collect);
    }

    @Override
    public void updateByUserIdAndDocId(Collect collect){
        collectMapper.updateByPrimaryKey(collect);
    }

    public void delByPK(Collect collect){
        collectMapper.deleteByPrimaryKey(collect);
    }

}

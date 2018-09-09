package ipl.restapi.service;

import ipl.manager.pojo.Collect;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.service
 * @date 2018/3/24 14:14
 * @since api1.0
 */
public interface CollectService {
    List<Collect> getAllCollect(Long userId);
    void insertByuserId(Collect collect);
    void updateByUserIdAndDocId(Collect collect);
    void delByPK(Collect collect);
}

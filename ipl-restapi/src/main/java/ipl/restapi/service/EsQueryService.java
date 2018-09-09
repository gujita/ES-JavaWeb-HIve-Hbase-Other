package ipl.restapi.service;

/**
 * <p>pakage: ipl.restapi.service.impl</p>
 *
 * descirption:
 *
 * @author wanghai
 * @version V1.0
 * @since <pre>2018/8/17 上午12:05</pre>
 */
public interface EsQueryService {
    String queryByAuthor(String authorName);
    String queryByYear(String authorName);
}

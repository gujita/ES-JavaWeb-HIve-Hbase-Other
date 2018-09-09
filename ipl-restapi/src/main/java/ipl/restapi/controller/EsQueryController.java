package ipl.restapi.controller;

import ipl.restapi.service.EsQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * descirption:
 *
 * @author wanghai
 * @version V1.0
 * @since <pre>2018/8/17 上午12:30</pre>
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class EsQueryController {
    private static final Logger LOGGER = LoggerFactory.getLogger("es");
    @Autowired
    private EsQueryService esQueryService;

    @RequestMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @ResponseBody
    public String queryByAuthoName(@RequestParam(value = "authorName") String param) {
        return esQueryService.queryByAuthor(param);
    }
}
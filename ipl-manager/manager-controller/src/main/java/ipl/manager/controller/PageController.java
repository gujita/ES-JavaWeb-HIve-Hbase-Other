package ipl.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 王海
 * @version V1.0
 * @package ipl.manager.controller
 * @description 页面跳转controller
 * @date 2018/3/14 22:10
 */
// 标记为Spring MVC的控制器，处理HTTP请求。
@Controller
public class PageController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showIndexJsp() {
        // 前后缀字符串拼接（也可以使用ModelAndView对象）,返回test.jsp
        return "test";
    }

    /**
     * 展示其他页面
     *
     * @param page 请求URL的page名
     * @return page名
     */
//    用于定义一个请求映射，value为请求的url，值为 / 说明，该请求首页请求，method用以指定该请求类型，一般为get和post
    @RequestMapping("/{page}")
//    @ResponseBody 作用： 该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
// @PathVariable 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数

    public String showPage(@PathVariable String page) {
        return page;
    }

}


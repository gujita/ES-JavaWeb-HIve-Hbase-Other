package ipl.restapi.controller;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * @author 原之安
 * @date 2018/5/28 21:17
 * @author 王海
 * @date 2018/9/23 11:17
 */
public class LoginCookie {
    /**
     * 模拟登陆
     * @return cookie
     */
    public static String ConnectTheNet() {
        String loginUrl = "http://172.21.201.131/search/user/login";

        HttpClient httpClient = new HttpClient();

        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);

        // 设置登陆时要求的信息，用户名和密码
        NameValuePair[] data = {new NameValuePair("name", "webmaster"), new NameValuePair("pwd", "dfld1234")};
        postMethod.setRequestBody(data);

        // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        try {
            httpClient.executeMethod(postMethod);
        } catch (IOException e) {
            return "-1";
        }
        // 获得登陆后的 Cookie
        Cookie[] cookies = httpClient.getState().getCookies();
        StringBuilder tmpcookies = new StringBuilder();
        for (Cookie c : cookies) {
            tmpcookies.append(c.toString()).append(";");
        }
        System.out.println("!!!!!!获取cookie： " + tmpcookies.toString());
        return tmpcookies.toString();
    }
}

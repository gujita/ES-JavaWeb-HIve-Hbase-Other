package ipl.restapi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * <p>package: ipl.restapi.service.bigdata</p>
 *
 * descirption:
 *
 * @author 王海
 * @version V1.0
 * @since <pre>2018/9/3 11:28</pre>
 */
public class GetDataUtils {
    public static String getDataWithCookie(String dataUrl,String cookies) {
        URL url;
        try {
            url = new URL(dataUrl);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException === url = new URL(dataUrl)");
            e.printStackTrace();
            return "-1";
        }
        URLConnection conn;
        try {
            conn = url.openConnection();
        } catch (IOException e) {
            System.out.println("conn = url.openConnection()");
            return "-1";
        }
        conn.setRequestProperty("Cookie", cookies);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoInput(true);
        StringBuilder sb;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")))) {
            sb = new StringBuilder();
            String cp;
            while ((cp = br.readLine()) != null) {
                sb.append(cp);
            }
        } catch (IOException e) {
            return "-1";
        }
        return sb.toString();
    }

    public static String getData(String url) {
        StringBuilder sb;
        try (InputStream is = new URL(url).openStream(); BufferedReader brd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")))) {
            sb = new StringBuilder();
            String cp;
            while ((cp = brd.readLine()) != null) {
                sb.append(cp);
            }
        } catch (IOException e) {
            return "-1";
        }
        return sb.toString();
    }
}

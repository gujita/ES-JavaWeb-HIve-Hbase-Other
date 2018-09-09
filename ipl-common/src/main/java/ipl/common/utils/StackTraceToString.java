package ipl.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>Descirption:日志中需要获取堆栈的全部信息</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.common.utils
 * @date 2018/3/20 20:40
 * @since api1.0
 */
public class StackTraceToString {
    public static String getStackTraceString(Throwable e) {
        // 保存错误栈的writer
        StringWriter stringWriter = new StringWriter();
        // 重载printStackTrace(PrintWriter s)
        e.printStackTrace(new PrintWriter(stringWriter, true));
        return stringWriter.toString();
    }
}

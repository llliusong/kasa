package com.pine.kasa.utils;


import com.alibaba.druid.support.json.JSONUtils;
import com.pine.kasa.utils.jackson.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    private final static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * 获取文件扩展名;
     *
     * @param fileName
     * @return String [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getFileExtension(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }
        return new String(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()));
    }

    /**
     * 获取HttpServletRequest;
     *
     * @return HttpServletRequest [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static HttpServletRequest getHttpRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取文件扩展名;
     *
     * @param fileName
     * @return String [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getFileName(String fileName) {
        String fileExtension = getFileExtension(fileName);
        return new String(fileName.substring(0, fileName.lastIndexOf(fileExtension) - 1));
    }

    /**
     * 用来去掉List中空值和相同项的。
     *
     * @param list
     * @return
     */
    public static List<String> removeSameItem(List<String> list) {
        List<String> difList = new ArrayList<String>();
        for (String t : list) {
            if (t != null && !difList.contains(t)) {
                difList.add(t);
            }
        }
        return difList;
    }

    /**
     * 返回用户的IP地址
     *
     * @param request
     * @return
     */
    public static String toIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 去除字符串最后一个逗号,若传入为空则返回空字符串
     *
     * @param para
     * @return
     * @descript
     * @author zoomy
     * @date 2015年3月29日
     * @version 1.0
     */
    public static String trimComma(String para) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(para)) {
            if (para.endsWith(",")) {
                return new String(para.substring(0, para.length() - 1));
            } else {
                return para;
            }
        } else {
            return "";
        }
    }

    public static Integer valueOf(String value, int def) {
        try {
            if (StringUtils.isNotEmpty(value)) {
                return Integer.parseInt(value);
            }
        } catch (Exception e) {
            return def;
        }
        return def;
    }

    public static Integer valueOf(String value) {
        return valueOf(value, 0);
    }

    /**
     * html转议
     *
     * @param content
     * @return
     * @descript
     * @author LJN
     * @date 2015年4月27日
     * @version 1.0
     */
    public static String htmltoString(String content) {
        if (content == null)
            return "";
        String html = content;
        html = html.replace("'", "&apos;");
        html = html.replaceAll("&", "&amp;");
        html = html.replace("\"", "&quot;"); // "
        html = html.replace("\t", "&nbsp;&nbsp;");// 替换跳格
        html = html.replace(" ", "&nbsp;");// 替换空格
        html = html.replace("<", "&lt;");
        html = html.replaceAll(">", "&gt;");

        return html;
    }

    /**
     * html转议
     *
     * @param content
     * @return
     * @descript
     * @author LJN
     * @date 2015年4月27日
     * @version 1.0
     */
    public static String stringtohtml(String content) {
        if (content == null)
            return "";
        String html = content;
        html = html.replace("&apos;", "'");
        html = html.replaceAll("&amp;", "&");
        html = html.replace("&quot;", "\""); // "
        html = html.replace("&nbsp;&nbsp;", "\t");// 替换跳格
        html = html.replace("&nbsp;", " ");// 替换空格
        html = html.replace("&lt;", "<");
        html = html.replaceAll("&gt;", ">");
        return html;
    }

    public static Long[] stringArrayToLongArray(String str[]) {
        Long[] num = null;
        if (str != null && str.length > 0) {
            int len = str.length;
            num = new Long[len];
            for (int i = 0; i < len; i++) {
                num[i] = Long.valueOf(str[i]);
            }
        }
        return num;
    }

    /**
     * 移出数组的重复原属
     *
     * @param ori
     * @return
     */
    public static <T> List<T> removeArrayRepeat(T[] ori) {

        List<T> t = new ArrayList<>();

        if (ori != null && ori.length != 0) {
            for (T g : ori) {
                if (!t.contains(g)) t.add(g);
            }
        }

        return t;
    }

    /**
     * response 输出JSON
     *
     * @param response
     * @param resultMap
     */
    public static void out(ServletResponse response, ServletRequest request, Object resultMap) {

        PrintWriter out = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.println(JsonUtil.obj2String(resultMap));
        } catch (Exception e) {
            logger.error("输出JSON报错。", e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 是否是Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

}

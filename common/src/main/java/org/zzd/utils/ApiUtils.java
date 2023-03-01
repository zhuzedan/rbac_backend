package org.zzd.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * @author :zzd
 * @apiNote :api通用工具类
 * @date : 2023-03-01 18:48
 */
public class ApiUtils {

    /**
     * @apiNote: 获取设备ip
     * @return: String
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * @apiNote: 将对象转为字符串数据
     * @param: [obj：带转换对象]
     * @return: java.lang.String
     */
    public static String getStr(Object obj) {
        String str = Objects.nonNull(obj) ? String.valueOf(obj).trim().replaceAll("\\s*|\r|\n|\t", "") : "";
        return "null".equalsIgnoreCase(str) ? "" : str;
    }

    /**
     * @apiNote: 将对象转为字符串数据, obj为空时返回defaultVal值
     * @param: [obj, defaultVal]
     * @return: java.lang.String
     */
    public static String getStr(Object obj, String defaultVal) {
        final String str = getStr(obj);
        return StringUtils.isBlank(str) ? defaultVal : str;
    }


}

package com.pine.kasa.test;

import lombok.Data;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-11-21 22:33.
 */
@Data
public class Test {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id = 1;

    private final static int a = 1;

    private final static Integer aa = 1;

    public static void main(String[] args) {

        final Test test = new Test();
        test.setId(12);
        test.setId(14);
        System.out.println(test);

        String str = "  -23- ";
//        System.out.println(Integer.valueOf(str.trim()));

        System.out.println(simplifyPath("/a//b////c/d//././/.."));

        System.out.println(myAtoi(" -54asd "));

        System.out.println(myAtoi2(" -54asd "));
    }

    public static String simplifyPath(String path) {
        if (null == path || "".equals(path)) {
            return path;
        }
        // 以 / 分割成数组，然后定义一个 sb ,进行拼接
        String[] split = path.split("/");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            if ("".equals(s) || ".".equals(s)) {
                continue;
            }
            if ("..".equals(s)) {
                if (sb.length() != 0) {
                    sb.delete(sb.lastIndexOf("/"), sb.length());

                }
                continue;
            }
            sb.append("/").append(s);
        }
        if (sb.length() == 0) {
            sb.append("/");
        }
        return sb.toString();
    }

    public static int myAtoi(String str) {
        if (str == null || "".equals(str)) {
            return 0;
        }
        str = str.trim();
        char startChar = str.charAt(0);
        // 判断正负
        int z = 1;
        // 记录开始的位置
        int a = 0;
        if (startChar == '-') {
            z = -1;
            a++;
        } else if (startChar == '+') {
            a++;
        }

        int result = 0;
        for (int i = a; i < str.length(); i++) {
            char s = str.charAt(i);
            if (Character.isDigit(s)) {
                result = result * 10 + s - 48;
            } else {
                // 允许尾部出现非数字字符，忽略掉,当出现不是字符时结束for循环
                break;
            }
        }
        return result * z;
    }

    public static int myAtoi2(String str) {
        if (str == null || str.length() == 0) {
            return 0;       //字符串为空
        }
        str = str.trim();   //去掉首尾的空格

        char firstChar = str.charAt(0);
        int sign = 1, start = 0, len = str.length();

        long sum = 0;  //存储最后的结果

        //判断是正数，还是负数
        if (firstChar == '+') {
            sign = 1;
            start++;
        } else if (firstChar == '-') {
            sign = -1;
            start++;
        }

        for (int i = start; i < len; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                //不是数字
                return (int) sum * sign;
            }
            //是数字
            sum = sum * 10 + str.charAt(i) - '0';

            if (sign == 1 && sum > Integer.MAX_VALUE)
                //是正数，溢出
                return Integer.MAX_VALUE;
            if (sign == -1 && (-1) * sum < Integer.MIN_VALUE)
                //是负数，溢出
                return Integer.MIN_VALUE;
        }
        return (int) sum * sign;
    }

}

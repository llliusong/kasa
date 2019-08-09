package com.pine.kasa.utils;

import com.pine.kasa.exception.BusinessException;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 使用正则表达式进行表单验证
 *
 * @author pine
 * @create 2018-04-12 下午7:19
 **/
public class RegexValidateUtils {

    private static boolean flag = false;

    /**
     * 邮箱正则
     */
    private static String email_regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 手机号码正则
     */
    private static String mobile_regex = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";

    /**
     * 固定号码正则
     */
    private static String phone_regex = "^(0\\d{2}\\d{8}(\\d{1,4})?)|(0\\d{3}\\d{7,8}(\\d{1,4})?)$";

    /**
     * 字符长度验证8-16位
     */
    private static String str_length_regex = "^.{8,16}$";

    /**
     * 密码正则(字母、数字、符号中至少包含2种)
     */
    private static String pwd_regex = "^(((?=.*[0-9])(?=.*[a-zA-Z])|(?=.*[0-9])(?=.*[^\\s0-9a-zA-Z])|(?=.*[a-zA-Z])(?=.*[^\\s0-9a-zA-Z]))[^\\s]+)$";

    /**
     * 匹配字符串中的图片url
     * ((https?).*?(?i)(png|jpg|jpeg))
     */
    private static String image_regex = "((https?):\\/\\/comimg.forwe.store.*?(png|jpg|jpeg|webp|gif))";

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        return check(email, email_regex);
    }

    /**
     * 验证手机号码或固话号码
     * <p>
     * 固定号码格式: 区号-号码
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        if (check(phone, mobile_regex)) {
            return true;
        } else {
            return check(phone, phone_regex);
        }
    }

    /**
     * 验证手机号
     *
     * @param mobile 手机号
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile) {
        return check(mobile, mobile_regex);
    }

    /**
     * 验证密码
     *
     * @param pwd 密码
     * @author pine
     * @date 2018/4/19 上午10:19
     */
    public static boolean checkPWD(String pwd) {
        return check(pwd, str_length_regex) && check(pwd, pwd_regex);
    }


    /**
     * 验证数字
     *
     * @param number 数字
     * @author pine
     * @date 2018/6/6 下午5:57
     */
    public static void checkNumber(String number) {
        if (StringUtils.isEmpty(number)) {
            return;
        }
        try {
            new BigDecimal(number);
        } catch (Exception e) {
            throw new BusinessException("金额格式不正确! " + number);
        }
    }

    private static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static List<String> regexImage(String s) {
        List<String> strs = new ArrayList<String>();
        try {
            Pattern p = Pattern.compile(image_regex);
            Matcher m = p.matcher(s);
            while (m.find()) {
                strs.add(m.group());
            }
        } catch (Exception e) {
        }
        return strs;
    }

    public static String getMatcher(String source) {
        String result = "";
        Pattern pattern = Pattern.compile(image_regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    /**
     * 判断字母致函数字，字母，汉字
     * @param str
     * @return
     */
    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";//其他需要，直接修改正则表达式就好
        return str.matches(regex);
    }


    public static void main(String[] args) {
        //System.out.println(checkPhone("15755392060"));
        //System.out.println(checkPhone("157553920601"));
        //System.out.println(checkPhone("5381471"));
        //System.out.println(checkPhone("0551-5381471"));

        //System.out.println(checkPWD("aaaaaa11*11111111"));

//        System.out.println(new BigDecimal("10.4"));
    }
}

package com.pine.kasa.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by pine on  2017/4/7 下午1:54.
 */
@Component
public class IntegerConvert implements Converter<String,Integer> {
    @Override
    public Integer convert(String str){
        try {
            if(str.equals("null")){
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Integer(str);
    }
}

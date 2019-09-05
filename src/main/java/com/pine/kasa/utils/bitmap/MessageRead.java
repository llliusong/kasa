package com.pine.kasa.utils.bitmap;

import sun.jvm.hotspot.utilities.BitMap;

/**
 * Created by pine on  2019/5/31 下午4:33.
 */
public class MessageRead {


    public static void main(String[] args) {
        BitMap bitMap = new BitMap(0);

//        BitMap2 bitMap = new BitMap2(63);
//        bitMap.setBit(63);
//        System.out.println(bitMap.getBit(63));
//        System.out.println(bitMap.getBit(62));

        System.out.println("bitMap = " + bitMap.toString());
    }
}

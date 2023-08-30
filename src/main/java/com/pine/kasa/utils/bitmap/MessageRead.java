package com.pine.kasa.utils.bitmap;

/**
 * Created by pine on  2019/5/31 下午4:33.
 */
public class MessageRead {


    public static void main(String[] args) {
//        BitMap bitMap = new BitMap(0);

//        BitMap2 bitMap = new BitMap2(63);
//        bitMap.setBit(63);
//        System.out.println(bitMap.getBit(63));
//        System.out.println(bitMap.getBit(62));

        Bitmap4 bitMap = new Bitmap4(100);
        bitMap.add(2);

        System.out.println("bitMap = " + bitMap.toString());
        System.out.println("bitMap = " + bitMap.get(2));
    }
}

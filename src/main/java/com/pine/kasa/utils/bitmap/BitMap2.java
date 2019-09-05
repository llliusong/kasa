package com.pine.kasa.utils.bitmap;

/**
 * Created by pine on  2019/6/3 下午2:54.
 */
public class BitMap2 {
    private byte[] bytes;

    public BitMap2(byte[] bytes) {
        super();
        this.bytes = bytes;
    }

    public BitMap2() {
        super();
    }

    public BitMap2(int size) {
        super();
        int number = size / 8 + 1;// may waste a byte, which does not matter
        bytes = new byte[number];
    }

    /**
     *
     * @param n
     *            n>=1
     */
    public void setBit(int n) {
        if (n <= 0)
            return;
        int index = -1;
        int offset = -1;
        if (0 == n % 8) {
            index = n / 8 - 1;
            offset = 7;
        } else {
            index = n / 8;
            offset = n % 8 - 1;
        }
        switch (offset) {
            case 0:
                bytes[index] = (byte)(bytes[index]|0x01);
                break;
            case 1:
                bytes[index] = (byte)(bytes[index]|0x02);
                break;
            case 2:
                bytes[index] = (byte)(bytes[index]|0x04);
                break;
            case 3:
                bytes[index] = (byte)(bytes[index]|0x08);
                break;
            case 4:
                bytes[index] = (byte)(bytes[index]|0x10);
                break;
            case 5:
                bytes[index] = (byte)(bytes[index]|0x20);
                break;
            case 6:
                bytes[index] = (byte)(bytes[index]|0x40);
                break;
            case 7:
                bytes[index] = (byte)(bytes[index]|0x80);
                break;
        }
    }

    public boolean get(int n){
        if (n <= 0)
            return false;
        int index = -1;
        int offset = -1;
        if (0 == n % 8) {
            index = n / 8 - 1;
            offset = 7;
        } else {
            index = n / 8;
            offset = n % 8 - 1;
        }
        switch (offset) {
            case 0:
                return (byte)(bytes[index]&0x01)!=0;//2^0
            case 1:
                return (byte)(bytes[index]&0x02)!=0;
            case 2:
                return (byte)(bytes[index]&0x04)!=0;
            case 3:
                return (byte)(bytes[index]&0x08)!=0;
            case 4:
                return (byte)(bytes[index]&0x10)!=0;
            case 5:
                return (byte)(bytes[index]&0x20)!=0;
            case 6:
                return (byte)(bytes[index]&0x40)!=0;
            case 7:
                return (byte)(bytes[index]&0x80)!=0;
        }
        return false;
    }

    public static void main(String[] args) {
        /**
         * 维护一个 用户userid到自增mapid的映射（不包含0）
         * 如:
         * {
         *    1:"userId1",
         *    2:"userId2",
         *    3:"userId3"
         * }
         * 每一个公司或者每一个群都有一个对应关系，新增则追加，删除在定义一个map来维护
         * 这样群成员每加入一个群里，就有mapid<->usreid的双向映射了，假如群里有5个成员ABCDE, 那就对应mapid 1-5，messageid对应的消息详情存储就可以设计成
         *
         * { uint32_t maxid, uint8_t readbit[]}
         * 然后每一个用户已读及设置其对应的key为1
         *
         */
        BitMap2 bMap = new BitMap2(100000);
        bMap.setBit(0);
        bMap.setBit(1);
        bMap.setBit(59);
        bMap.setBit(20);
        bMap.setBit(121);
        bMap.setBit(9912);
        bMap.setBit(62923);
        bMap.setBit(100000);

        //已读列表
        for(int i=1;i<=100000;i++){
            if(bMap.get(i))
                System.out.println(i);
        }

        System.out.println(bMap.get(0));
    }

}
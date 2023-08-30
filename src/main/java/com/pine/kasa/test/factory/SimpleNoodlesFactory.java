package com.pine.kasa.test.factory;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:28.
 */
public class SimpleNoodlesFactory {

    public static final int TYPE_LZ = 1;//兰州拉面
    public static final int TYPE_PM = 2;//泡面
    public static final int TYPE_GK = 3;//干扣面

    public static INoodles createNoodles(int type) {
        switch (type) {
            case TYPE_LZ:
                return new LzNoodles();
            case TYPE_PM:
                return new PaoNoodles();
            case TYPE_GK:
            default:
                return new GankouNoodles();
        }
    }

}

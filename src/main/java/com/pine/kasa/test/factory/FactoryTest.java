package com.pine.kasa.test.factory;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:29.
 */
public class FactoryTest {

    public static void main(String[] args) {
        INoodles noodles = SimpleNoodlesFactory.createNoodles(SimpleNoodlesFactory.TYPE_GK);
        noodles.desc();
    }
}

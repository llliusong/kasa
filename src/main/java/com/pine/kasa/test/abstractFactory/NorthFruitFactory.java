package com.pine.kasa.test.abstractFactory;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:42.
 */
public class NorthFruitFactory implements FruitFactory {
    @Override
    public Fruit getApple() {
        return new NorthApple();
    }

    @Override
    public Fruit getBanana() {

        return new NorthBanana();
    }
}

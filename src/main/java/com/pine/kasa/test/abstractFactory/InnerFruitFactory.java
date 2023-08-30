package com.pine.kasa.test.abstractFactory;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:41.
 */
public class InnerFruitFactory implements FruitFactory {
    @Override
    public Fruit getApple() {
        return new InnerApple();
    }

    @Override
    public Fruit getBanana() {
        return new InnerBanana();
    }
}

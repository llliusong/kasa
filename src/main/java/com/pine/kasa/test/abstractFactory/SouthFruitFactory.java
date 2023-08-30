package com.pine.kasa.test.abstractFactory;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:44.
 */
public class SouthFruitFactory implements FruitFactory {
    @Override
    public Fruit getApple() {
        return new SouthApple();
    }

    @Override
    public Fruit getBanana() {
        return new SouthBanana();
    }
}

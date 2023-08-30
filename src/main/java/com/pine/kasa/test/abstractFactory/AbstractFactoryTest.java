package com.pine.kasa.test.abstractFactory;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:44.
 */
public class AbstractFactoryTest {

    public static void main(String[] args) {
        FruitFactory ff = new NorthFruitFactory();
        Fruit apple = ff.getApple();
        apple.get();

        Fruit banana = ff.getBanana();
        banana.get();

        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        FruitFactory bb = new SouthFruitFactory();
        Fruit apple2 = bb.getApple();
        apple2.get();

        Fruit banana2 = bb.getBanana();
        banana2.get();

        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        //比如要增加室内innerApple,InnerBanana
        FruitFactory cc = new InnerFruitFactory();
        Fruit apple3 = cc.getApple();
        apple3.get();
        Fruit banana3 = cc.getBanana();
        banana3.get();

    }
}

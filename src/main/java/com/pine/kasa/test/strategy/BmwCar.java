package com.pine.kasa.test.strategy;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:23.
 */
public class BmwCar implements CarStrategy{

    @Override
    public void buyCar() {
        System.out.println("欢迎购买宝马530Li。。。。。。");
    }
}

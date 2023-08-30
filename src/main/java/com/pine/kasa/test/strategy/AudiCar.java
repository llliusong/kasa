package com.pine.kasa.test.strategy;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:24.
 */
public class AudiCar implements CarStrategy {

    @Override
    public void buyCar() {
        System.out.println("欢迎购买奥迪A6L。。。。。。");
    }
}

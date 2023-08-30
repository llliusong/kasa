package com.pine.kasa.test.strategy;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:25.
 */
public class StrategyTest {

    public static void main(String[] args) {
        // 宝马车
        CarContext bmwCarContext1 = new CarContext(new BmwCar());
        bmwCarContext1.buyCarStrategy();
        // 奔驰车
        CarContext bcCarContext = new CarContext(new BcCar());
        bcCarContext.buyCarStrategy();
        // 奥迪车
        CarContext audiCarContext = new CarContext(new AudiCar());
        audiCarContext.buyCarStrategy();
    }
}

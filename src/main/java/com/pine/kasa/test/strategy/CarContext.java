package com.pine.kasa.test.strategy;

/**
 * 环境（Context）类
 *
 * @author pine
 * @date 2020-06-13 00:24.
 */
public class CarContext {
    private CarStrategy carStrategy;

    public CarContext(CarStrategy carStrategy){
        this.carStrategy = carStrategy;
    }

    public void buyCarStrategy(){
        carStrategy.buyCar();
    }

}

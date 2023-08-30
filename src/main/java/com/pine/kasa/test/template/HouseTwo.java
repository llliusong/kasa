package com.pine.kasa.test.template;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:16.
 */
public class HouseTwo extends HouseTemplate {

    HouseTwo(String name){
        super(name);
    }

    @Override
    protected void buildDoor() {
        System.out.println(name + "的门采用木门");
    }

    @Override
    protected void buildWindow() {
        System.out.println(name + "的窗户要向南");
    }

    @Override
    protected void buildWall() {
        System.out.println(name + "的墙使用玻璃制造");
    }

    @Override
    protected void buildBase() {
        System.out.println(name + "的地基使用花岗岩");
    }

    @Override
    protected void buildToilet() {
        System.out.println(name + "的厕所建在西北角");
    }
}

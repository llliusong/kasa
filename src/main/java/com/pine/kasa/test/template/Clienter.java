package com.pine.kasa.test.template;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:17.
 */
public class Clienter {

    public static void main(String[] args) {
//        HouseTemplate houseOne = new HouseOne("房子1");
        HouseTemplate houseOne = new HouseOne("房子1", false);

        HouseTemplate houseTwo = new HouseTwo("房子2");
        houseOne.buildHouse();
        houseTwo.buildHouse();
    }


}

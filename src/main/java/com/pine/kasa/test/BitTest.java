package com.pine.kasa.test;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-07-21 15:44.
 */
public class BitTest {

    public static void main(String[] args) {
        transferBi(7);
    }

    //模拟耗时操作，定义一个斐波那契数列
    private static int fibc(int num) {
        if (num == 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        return fibc(num - 1) + fibc(num - 2);
    }

    public static void transferBi2(int num) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 32; i++) {

            Integer r = (num & (0x01 << i));
            if (r != 0) {
                System.out.println("get " + r);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("======请求主页耗时毫秒数：" + (endTime - startTime) + ",换算为秒：" + (endTime - startTime) / 1000f);

    }

    public static void transferBi(int num) {
        long startTime = System.currentTimeMillis();
//		接收二进制各位数
        List<Integer> bi = new ArrayList<Integer>();
//		接收组成数
        List<Integer> numbers = new ArrayList<Integer>();
//		计算组成数时，获取当前位的0/1值
        int number = 0;

//		二进制转换
        while (num > 0) {
            bi.add(num % 2);
            num = num / 2;
        }

//		计算组成数
        for (int i = 0; i < bi.size(); i++) {
            if ((number = bi.get(i)) != 0) {
                numbers.add(number * (int) Math.pow(2, i));
            }
        }

//		输出结果
        System.out.println("输入的数由这些数组成：");

        for (int i : numbers) {
            System.out.print(i + " ");
        }

        long endTime = System.currentTimeMillis();
        System.out.println("======请求主页耗时毫秒数：" + (endTime - startTime) + ",换算为秒：" + (endTime - startTime) / 1000f);

    }
}

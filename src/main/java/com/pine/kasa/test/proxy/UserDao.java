package com.pine.kasa.test.proxy;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:46.
 */

/**
 * 接口实现 目标对象
 */
public class UserDao implements IUserDao {

    public void save() {
        System.out.println("----已经保存数据!----");
    }
}
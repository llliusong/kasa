package com.pine.kasa.test.builder;

/**
 * TODO --do sth
 *
 * @author pine
 * @date 2020-06-13 00:35.
 */
public class User {
    /**账号*/
    private String userName;

    /**密码*/
    private String password;

    /**年龄*/
    private int age;

    /**性别*/
    private String sex;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    public static class Builder {

        /**账号*/
        private String userName;

        /**密码*/
        private String password;

        /**年龄*/
        private int age;

        /**性别*/
        private String sex;

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder sex(String sex) {
            this.sex = sex;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

    private User(Builder b) {
        this.age = b.age;
        this.password = b.password;
        this.sex = b.sex;
        this.userName = b.userName;
    }

    public static void main(String[] args) {
        User user = new Builder().age(10).password("abc").sex("男").userName("张三").build();
        System.out.println(user.toString());
    }

}

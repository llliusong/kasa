package com.pine.kasa.utils.keygen;


/**
 * @author: pine
 * @date: 2019-08-14 17:25.
 * @description:
 */
public class KeyGeneratorFactory {
    public static KeyGenerator newInstance(String keyGeneratorClassName) {
        try {
            return (KeyGenerator)Class.forName(keyGeneratorClassName).newInstance();
        } catch (ReflectiveOperationException var2) {
            throw new IllegalArgumentException(String.format("Class %s should have public privilege and no argument constructor", keyGeneratorClassName));
        }
    }

    private KeyGeneratorFactory() {
    }
}

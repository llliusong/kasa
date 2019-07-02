package com.pine.kasa.utils.jackson;

/**
 * @Author: pine
 * @CreateDate: 2018-09-15 11:29
 */
public enum  SerializerFeature {
    WriteNullListAsEmpty,
    WriteNullStringAsEmpty,
    WriteNullNumberAsZero,
    WriteNullBooleanAsFalse;

    public final int mask;

    SerializerFeature() {
        mask = (1 << ordinal());
    }
}

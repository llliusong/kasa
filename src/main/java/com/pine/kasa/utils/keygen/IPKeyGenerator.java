package com.pine.kasa.utils.keygen;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: pine
 * @date: 2019-08-15 10:45.
 * @description:
 */
public class IPKeyGenerator {
    private static DefaultKeyGenerator defaultKeyGenerator =  new DefaultKeyGenerator();

    static {
        initWorkerId();
    }

    /**
     * 获取ID
     *
     * @author sunk
     */
    public long generateKey() {
        return defaultKeyGenerator.generateKey().longValue();
    }

    private static void initWorkerId() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }
        byte[] ipAddressByteArray = address.getAddress();
        DefaultKeyGenerator.setWorkerId((long) (((ipAddressByteArray[ipAddressByteArray.length - 2] & 0B11) << Byte.SIZE)
                + (ipAddressByteArray[ipAddressByteArray.length - 1] & 0xFF)));
    }
}

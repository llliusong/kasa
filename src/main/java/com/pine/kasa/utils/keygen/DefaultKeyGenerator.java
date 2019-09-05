package com.pine.kasa.utils.keygen;

import io.shardingsphere.core.keygen.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: pine
 * @date: 2019-08-14 17:28.
 * @description:
 */
public final class DefaultKeyGenerator implements KeyGenerator {
    private static final Logger log = LoggerFactory.getLogger(io.shardingsphere.core.keygen.DefaultKeyGenerator.class);
    /**
     * 时间偏移量，从2016年11月1日零点开始
     */
    private final static long EPOCH = 1477929600000L;

    /**
     * 自增量占用比特
     */
    private static final long SEQUENCE_BITS = 12L;

    /**
     * 工作进程ID比特
     */
    private static final long WORKER_ID_BITS = 10L;

    /**
     * 自增量掩码（最大值）
     */
    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

    /**
     * 工作进程ID左移比特数（位数）
     */
    private static final long WORKER_ID_LEFT_SHIFT_BITS = 12L;

    /**
     * 时间戳左移比特数（位数）
     */
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = 22L;

    /**
     * 工作进程ID最大值
     */
    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

    /**
     * 工作进程ID最小值
     */
    private static final long WORKER_ID_MIN_VALUE = 0L;
    private static long workerId;
    private long sequence;
    private long lastTime;


    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class DefaultKeyGeneratorHolder {
        private static DefaultKeyGenerator instance = new DefaultKeyGenerator();

        static {
            initWorkerId();
        }

        public static void initWorkerId() {
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

    public static DefaultKeyGenerator getInstance() {
        return DefaultKeyGeneratorHolder.instance;
    }

    public static void setWorkerId(long currentWorkerId) {
        if (currentWorkerId >= WORKER_ID_MIN_VALUE && currentWorkerId < WORKER_ID_MAX_VALUE) {
            workerId = currentWorkerId;
        } else {
            throw new RuntimeException("WorkerId number is out of the maximum range.");
        }
    }

    /**
     * 生成Id
     *
     * @return
     */
    @Override
    public synchronized Number generateKey() {
        long currentMillis = getCurrentMillis();
        if (currentMillis < this.lastTime) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (this.lastTime == currentMillis) {
            if (0L == (this.sequence = this.sequence + 1L & SEQUENCE_MASK)) {
                currentMillis = this.waitUntilNextTime(currentMillis);
            }
        } else {
            this.sequence = 0L;
        }

        System.out.println("workerId=" + workerId);
        System.out.println("sequence=" + sequence);

        this.lastTime = currentMillis;

        return ((currentMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    /**
     * 不停获得时间，直到大于最后时间
     *
     * @param lastTime 最后时间
     * @return 时间
     */
    private long waitUntilNextTime(final long lastTime) {
        long mill = getCurrentMillis();
        while (mill <= lastTime) {
            mill = getCurrentMillis();
        }
        return mill;
    }

    private long getCurrentMillis() {
        return System.currentTimeMillis();
    }

//    static {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2016, 10, 1);
//        calendar.set(11, 0);
//        calendar.set(12, 0);
//        calendar.set(13, 0);
//        calendar.set(14, 0);
//        EPOCH = calendar.getTimeInMillis();
//    }
}
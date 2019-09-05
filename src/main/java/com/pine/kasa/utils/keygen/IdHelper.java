package com.pine.kasa.utils.keygen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: pine
 * @date: 2019-08-15 09:59.
 * @description:
 */
@Component
@PropertySource(value = "file:${distributed.config.file}")
@Slf4j
public class IdHelper {

    /**
     * 工作节点id
     */
    @Value("${workerId}")
    private int workerId;

    private static DefaultKeyGenerator defaultKeyGenerator;

    static {
        defaultKeyGenerator = new DefaultKeyGenerator();
//        DefaultKeyGenerator.setWorkerId(111);
    }

    @PostConstruct
    private void init() {
        log.info("workerId : {}", workerId);

//        defaultKeyGenerator = new DefaultKeyGenerator(workerId);
    }

    /**
     * 获取ID
     * @author sunk
     */
    public long nextId() {
        return defaultKeyGenerator.generateKey().longValue();
//        return DefaultKeyGenerator.getInstance().generateKey().longValue();
    }


}

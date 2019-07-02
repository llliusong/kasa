package com.pine.kasa.algorithm;

import io.shardingsphere.core.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;


/**
 * 主从加载算法
 *
 * @Author: pine
 * @CreateDate: 2019-02-22 11:20
 */
public class MasterSlaveLoadAlgorithm implements MasterSlaveLoadBalanceAlgorithm {
    private final Logger logger = LoggerFactory.getLogger(MasterSlaveLoadAlgorithm.class);

    @Override
    public String getDataSource(String name, String masterDataSourceName, List<String> slaveDataSourceNames) {
        logger.info(name);
        logger.info(masterDataSourceName);
        logger.info(slaveDataSourceNames.toString());

        if (LocalTime.now().isAfter(LocalTime.of(12, 0, 0))) {
            return slaveDataSourceNames.get(new Random().nextInt(slaveDataSourceNames.size()));
        } else {
            return slaveDataSourceNames.get(0);
        }
    }
}

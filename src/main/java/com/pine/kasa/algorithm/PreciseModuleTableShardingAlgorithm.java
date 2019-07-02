package com.pine.kasa.algorithm;


import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * 自定义标准分片策略，使用精确分片算法（=与IN）
 *
 * @Author: pine
 * @CreateDate: 2019-02-22 11:20
 */
public class PreciseModuleTableShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(PreciseModuleTableShardingAlgorithm.class);

    @Override
    public String doSharding(Collection<String> availableTargetNames,
                             PreciseShardingValue<Integer> preciseShardingValue) {
        logger.debug("collection:" + availableTargetNames + ",preciseShardingValue:" + preciseShardingValue);

        for (String each : availableTargetNames) {
            if (each.endsWith(preciseShardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}

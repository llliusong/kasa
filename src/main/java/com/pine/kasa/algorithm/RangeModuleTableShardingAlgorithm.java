package com.pine.kasa.algorithm;

import com.google.common.collect.Range;
import io.shardingsphere.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedHashSet;


/**
 * 自定义标准分库策略，使用范围分片算法（BETWEEN AND）
 *
 * @Author: pine
 * @CreateDate: 2019-02-22 11:20
 */
public class RangeModuleTableShardingAlgorithm implements RangeShardingAlgorithm<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(RangeModuleTableShardingAlgorithm.class);

    @Override
    public Collection<String> doSharding(
            Collection<String> availableTargetNames,
            RangeShardingValue<Integer> rangeShardingValue) {
        logger.debug("Range collection:" + availableTargetNames + ",rangeShardingValue:" + rangeShardingValue);

        Collection<String> collect = new LinkedHashSet<>();
        Range<Integer> range = rangeShardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : availableTargetNames) {
                if (each.endsWith(i % 2 + "")) {
                    collect.add(each);
                }
            }
        }

        return collect;
    }

}
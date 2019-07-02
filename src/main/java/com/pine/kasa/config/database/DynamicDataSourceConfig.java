package com.pine.kasa.config.database;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.google.common.collect.Lists;
import com.pine.kasa.algorithm.PreciseModuleTableShardingAlgorithm;
import com.pine.kasa.algorithm.RangeModuleTableShardingAlgorithm;
import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.core.api.config.ShardingRuleConfiguration;
import io.shardingsphere.core.api.config.TableRuleConfiguration;
import io.shardingsphere.core.api.config.strategy.StandardShardingStrategyConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * 配置多数据源
 *
 * @author yupan
 */
@Configuration
public class DynamicDataSourceConfig {

    private static String DS0_NAME = "ds_0";

    private static String DS_MASTER_0 = "ds_master_0";

    private static String DS_MASTER_0_SLAVE_0 = "ds_master_0_slave_0";

    @Bean(name = "dingpushDataSource")
    @ConfigurationProperties("spring.datasource.druid.dingpush")
    public DataSource dingpushDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "firstDataSource", initMethod = "init", destroyMethod = "close")   //声明其为Bean实例
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource firstDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "secondDataSource", initMethod = "init", destroyMethod = "close")   //声明其为Bean实例
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "shardingDataSource")
    public DataSource shardingDataSource(@Qualifier("firstDataSource") DataSource firstDataSource, @Qualifier("secondDataSource") DataSource secondDataSource) throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置主库
        dataSourceMap.put(DS_MASTER_0, firstDataSource);

        // 配置第一个从库
        dataSourceMap.put(DS_MASTER_0_SLAVE_0, secondDataSource);

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().add("cc_test");

        // 默认的分表策略
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new PreciseModuleTableShardingAlgorithm()));
        shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());


        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new HashMap<String, Object>(), new Properties());

        return dataSource;

    }

    /**
     * 配置分表规则
     *
     * @return
     */
    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        //配置逻辑表名，并非数据库中真实存在的表名，而是sql中使用的那个，不受分片策略而改变.
        result.setLogicTable("cc_test");
        //配置真实的数据节点，即数据库中真实存在的节点，由数据源名 + 表名组成
        //${} 是一个groovy表达式，[]表示枚举，{...}表示一个范围。
        //整个inline表达式最终会是一个笛卡尔积，表示ds_0.t_order_0. ds_0.t_order_1
        result.setActualDataNodes("ds_0.cc_test${0..1}");
        //主键生成列，默认的主键生成算法是snowflake,需要long类型
        result.setKeyGeneratorColumnName("id");
        //设置分片策略，这里简单起见直接取模，也可以使用自定义算法来实现分片规则
        result.setTableShardingStrategyConfig(
                new StandardShardingStrategyConfiguration("id",
                        new PreciseModuleTableShardingAlgorithm(),
                        new RangeModuleTableShardingAlgorithm()));
        return result;
    }

    /**
     * 配置读写分离规则
     *
     * @return
     */
    List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
        MasterSlaveRuleConfiguration masterSlaveRuleConfig1 = new MasterSlaveRuleConfiguration(DS0_NAME, DS_MASTER_0, Arrays.asList(DS_MASTER_0_SLAVE_0));
        return Lists.newArrayList(masterSlaveRuleConfig1);
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource shardingDataSource, DataSource dingpushDataSource, DataSource secondDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, shardingDataSource);
        targetDataSources.put(DataSourceNames.SECOND, dingpushDataSource);
        targetDataSources.put(DataSourceNames.THIRD, secondDataSource);
        return new DynamicDataSource(shardingDataSource, targetDataSources);
    }


    /**
     * 配置@Transactional注解事物
     * 注:使用spring整合mybatis后,mybatis一级缓存会失效，原因:https://blog.csdn.net/ctwy291314/article/details/81938882,https://blog.csdn.net/weixin_36507118/article/details/85619155
     *  @Bean
     *     public PlatformTransactionManager transactionManager(DataSource shardingDataSource, DataSource dingpushDataSource, DataSource secondDataSource) {
     *         return new DataSourceTransactionManager(dataSource(shardingDataSource, dingpushDataSource, secondDataSource));
     *     }
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DynamicDataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

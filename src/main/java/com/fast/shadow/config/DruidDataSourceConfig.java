package com.fast.shadow.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-27
 * @Description:com.fast.shadow.config
 * @Version:1.0
 **/

@Configuration
@Slf4j
public class DruidDataSourceConfig {

    /**
     * DruidDatasrouceConfig
     *
     * @return DataSource
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        log.info("Datasource创建完成 ...");
        return druidDataSource;
    }

}

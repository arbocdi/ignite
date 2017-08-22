/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ignite_pg;

import javax.sql.DataSource;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author root
 */
@Configuration
@ComponentScan
public class PostgresConfiguration {

    @Bean(destroyMethod = "close")
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS,value = "singleton")
    @Qualifier("postgres")
    public DataSource getDS() {
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName("A Data Source");
        source.setServerName("localhost");
        source.setDatabaseName("ignite");
        source.setUser("postgres");
        source.setPassword("postgres");
        source.setMaxConnections(100);
        return source;
    }
    @Bean
    @Primary
    @Scope(value = "singleton",proxyMode = ScopedProxyMode.INTERFACES)
    public JdbcOperations getOperations(@Qualifier("postgres") DataSource ds){
        return new JdbcTemplate(ds);
    }
}

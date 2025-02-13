package com.example.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.MountableFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;


@TestConfiguration
public class TestContainerDatabaseConfiguration {
    static String image = "gvenzl/oracle-free:23.6-slim-faststart";
    public static final String SQL_DATA_INIT_SQL = "sql/data_init.sql";
    public static final String SQL_DDL_INIT_SQL = "sql/db_init.sql";

    static OracleContainer oracleContainer = new OracleContainer(image)
            .withStartupTimeout(Duration.ofMinutes(5))  // Increased timeout
            .withUsername("testuser")
            .withPassword("testpwd")
            .withInitScript(SQL_DATA_INIT_SQL)  // Point to your init.sql script
            .waitingFor(org.testcontainers.containers.wait.strategy.Wait.forListeningPort());  // Wait for the container to be ready

    static {
        oracleContainer.start();
        // Load the init.sql script from the classpath. Any file may be used.
        MountableFile sqlFile = MountableFile.forClasspathResource(SQL_DDL_INIT_SQL);
        oracleContainer.copyFileToContainer(sqlFile, "/tmp/init.sql");
        // Run the init.sql script as sysdba on the database container.
        try {
            oracleContainer.execInContainer("sqlplus", "sys / as sysdba", "@/tmp/init.sql");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    DynamicPropertyRegistrar propertyRegistrar() {
        return registry -> {
            registry.add("spring.datasource.url", oracleContainer::getJdbcUrl);
            registry.add("spring.datasource.username", () -> "testuser");
            registry.add("spring.datasource.url", () -> "testpwd");
        };
    }

    @Bean
    DataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(oracleContainer.getJdbcUrl());
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setUsername(oracleContainer.getUsername());
        ds.setPassword(oracleContainer.getPassword());
        return ds;
    }
}
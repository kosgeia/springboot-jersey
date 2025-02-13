package com.example.demo.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.pool.OracleDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@TestPropertySource("classpath:application-test.properties")
@Import(TestContainerDatabaseConfiguration.class)
class OracleDatabaseTest {

    @Autowired
    DataSource ds;


    @Test
    void getConnection() throws SQLException {
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select * from students where first_name = 'Alice'")) {
            Assertions.assertTrue(rs.next());
            assertThat(rs.getString(2)).isEqualTo("Alice");
        }
    }
}
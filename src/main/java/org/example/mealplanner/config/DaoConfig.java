package org.example.mealplanner.config;

import org.example.mealplanner.core.dao.MealDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DaoConfig {

    @Bean
    public MealDao mealDao(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        return new MealDao(connection);
    }
}

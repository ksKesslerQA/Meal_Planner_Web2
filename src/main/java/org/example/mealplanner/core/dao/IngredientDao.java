package org.example.mealplanner.core.dao;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientDao {
    private final Connection connection;

    public IngredientDao(Connection connection) {
        this.connection = connection;
    }

    public List<String> findAllIngredientNames() throws SQLException {
        String sql = "SELECT DISTINCT name FROM ingredients ORDER BY name";

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<String> result = new ArrayList<>();
        while (rs.next()) {
            result.add(rs.getString("name"));
        }

        return result;
    }
}

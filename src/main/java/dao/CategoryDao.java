package dao;

import db.DatabaseConfig;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    public int create(Category category){
        String sql = "INSERT INTO categories(name) VALUES (?)";

        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS
            )) {

            statement.setString(1, category.getName());

            statement.executeUpdate();

            try(ResultSet keys = statement.getGeneratedKeys()){
                if(keys.next()){
                    return keys.getInt(1);
                }
            }

            throw new RuntimeException("No ID generated");

        } catch (SQLException e){
            throw new RuntimeException("Category not saved " + e);
        }
    }

    public List<Category> findAll(){
        String sql = "SELECT id, name FROM categories ORDER BY name";
        List<Category> categories = new ArrayList<>();
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
        ){
            while(resultSet.next()){
                categories.add(new Category(resultSet.getInt("id"), resultSet.getString("name")));
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException("Categories not loaded from DB" + e);
        }
    }

    public void deleteAll(){
        String sql = "DELETE FROM categories";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ){
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete records from category table" + e);
        }
    }
}
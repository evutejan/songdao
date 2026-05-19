package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public void createTableIfNeeded() {


        String createCategoryTable = """
                CREATE TABLE IF NOT EXISTS categories (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL UNIQUE
                )
                """;

        String createSongsTable = """
                CREATE TABLE IF NOT EXISTS songs (
                id INT AUTO_INCREMENT PRIMARY KEY,
                title VARCHAR(150) NOT NULL,
                artist VARCHAR(120) NOT NULL,
                category_id INT NOT NULL,
                FOREIGN KEY (category_id) references categories(id) ON DELETE CASCADE
                )
                """;
        try(
                Connection connection = DatabaseConfig.getConnection();
                Statement statement = connection.createStatement()
        )
        {
            statement.execute(createCategoryTable);
            statement.execute(createSongsTable);
        } catch(SQLException e)
        {
            throw new RuntimeException("ERROR: Can't create table. More info: " + e.getMessage());
        }
    }
}
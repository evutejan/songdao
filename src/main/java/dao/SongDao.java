package dao;

import db.DatabaseConfig;
import model.Category;
import model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDao {

    public void deleteAll(){
        String sql = "DELETE FROM songs";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Songs not deleted from DB" + e);
        }
    }

    public int create(Song song){
        String sql ="INSERT INTO songs (title, artist, category_id) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
                //create sql statement for jdbc
            statement.setString(1, song.getTitle());
                //assign value to paramtre number one
            statement.setString(2, song.getArtist());
            statement.setInt(3, song.getCategoryId());
            statement.executeUpdate();
                try(ResultSet keys = statement.getGeneratedKeys()){
                    if (keys.next()){
                        return keys.getInt(1);
                    }
        }
                 {
            throw new RuntimeException("no id");

        }
    } catch (SQLException e) {
            throw new RuntimeException("this is not good" + e);
        }
    }

    public List<Song> findAllWithCategory(){
        String sql = "SELECT s.id, s.title, s.artist, s.category_id " +
                "FROM songs s " +
                "JOIN categories c " +
                "ON s.category_id = c.id " +
                "ORDER BY s.title ASC";
        List<Song> songs = new ArrayList<>();
        try(Connection connection = DatabaseConfig.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                songs.add(new Song(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("artist"), resultSet.getInt("categoryId")));
            }
            return songs;
        }catch (SQLException e) {
            throw new RuntimeException("songs not loaded " + e);
        }
    }

    public List<Song> findByCategoryId(int categoryId){
        String sql = "SELECT s.id, s.title, s.artist, s.categoryId " +
                "FROM songs s " +
                "WHERE s.categoryId = ? " +
                "ORDER BY s.title ASC";
        List<Song> moreSongs = new ArrayList<>();
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                moreSongs.add(new Song(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("artist"), resultSet.getInt("categoryId")));
            }
            return moreSongs;
        }catch (SQLException e) {
            throw new RuntimeException("songs not loaded " + e);
        }
    }

    public void updateTitle(int songId, String newTitle){
        String sql = "UPDATE songs" +
                "SET title = ? " +
                "WHERE id = ?;";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {  statement.setString(1, newTitle);
            statement.setInt(2, songId);
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("fatal error " + e);
        }
    }

    public void deleteById(int songId){
        String sql = "DELETE FROM songs " +
                "WHERE id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {statement.setInt(1, songId);
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("fatal error " + e);
        }
    }


}
//public void deleteAll()
//
//public int create(Song song)
//
//public List<Song> findAllWithCategory()
//
//public List<Song> findByCategoryId(int categoryId)
//
//public void updateTitle(int songId, String newTitle)
//
//public void deleteById(int songId)
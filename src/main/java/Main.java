

import dao.CategoryDao;
import dao.SongDao;
import db.DatabaseInitializer;
import model.Category;
import model.Song;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer initializer = new DatabaseInitializer();
        initializer.createTableIfNeeded();

        CategoryDao categoryDao = new CategoryDao();

        categoryDao.deleteAll();

        int rockId = categoryDao.create(new Category(0, "Rock"));
        int popId = categoryDao.create(new Category(1, "Pop"));

        System.out.println(rockId);
        System.out.println(popId);

        SongDao songDao = new SongDao();

         //1. deleteAll()
        songDao.deleteAll();
        System.out.println("All songs deleted");

        // 2. create()
        Song song1 = new Song(0, "Believer", "Imagine Dragons", 1);
        Song song2 = new Song(1, "Numb", "Linkin Park", 2);

        int id1 = songDao.create(song1);
        int id2 = songDao.create(song2);

        System.out.println("Created songs with IDs: " + id1 + ", " + id2);

        // 3. findAllWithCategory()
        System.out.println("\nAll songs:");
        for (Song song : songDao.findAllWithCategory()) {
            System.out.println(song);
        }

        // 4. findByCategoryId()
        System.out.println("\nSongs in category 1:");
        for (Song song : songDao.findByCategoryId(1)) {
            System.out.println(song);
        }

        // 5. updateTitle()
        songDao.updateTitle(id1, "Thunder");
        System.out.println("\nUpdated title of song with ID " + id1);

        System.out.println("\nSongs after update:");
        for (Song song : songDao.findAllWithCategory()) {
            System.out.println(song);
        }

        // 6. deleteById()
        songDao.deleteById(id2);
        System.out.println("\nDeleted song with ID " + id2);

        System.out.println("\nFinal song list:");
        for (Song song : songDao.findAllWithCategory()) {
            System.out.println(song);
        }
    }
}
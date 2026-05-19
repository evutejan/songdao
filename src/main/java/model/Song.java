package model;

public class Song {
    private int id;
    private String title;
    private String artist;
    private int categoryId;

    public Song(int id, String title, String artist, int categoryId) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.categoryId = categoryId;
    }

    public Song(String title, int categoryId, String artist){
        this.title = title;
        this.categoryId = categoryId;
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getCategoryId() {
        return categoryId;
    }

}
package javaapplication2;

/**
 *
 * @author meena
 */


public class Movie {
    private int id;
    private String title;
    private String genre;
    private float rating;

    public Movie(int id, String title, String genre, float rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public float getRating() { return rating; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setRating(float rating) { this.rating = rating; }
}

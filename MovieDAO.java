package javaapplication2;

/**
 *
 * @author meena
 */


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getFloat("rating")
                );
                movies.add(movie);
            }
        }
        return movies;
    }

    public void addMovie(Movie movie) throws SQLException {
        String sql = "INSERT INTO movies (title, genre, rating) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getGenre());
            pstmt.setFloat(3, movie.getRating());
            pstmt.executeUpdate();
        }
    }

    public void updateMovie(Movie movie) throws SQLException {
        String sql = "UPDATE movies SET title = ?, genre = ?, rating = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getGenre());
            pstmt.setFloat(3, movie.getRating());
            pstmt.setInt(4, movie.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteMovie(int id) throws SQLException {
        String sql = "DELETE FROM movies WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}

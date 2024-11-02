package javaapplication2;

/**
 *
 * @author meena
 */




import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class MovieManagement extends JFrame {
    private JTextField titleField;
    private JTextField genreField;
    private JTextField ratingField;
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private MovieDAO movieDAO;

    public MovieManagement() {
        setTitle("Movie Management System");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        movieDAO = new MovieDAO();

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(20, 20, 80, 25);
        add(titleLabel);

        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setBounds(20, 50, 80, 25);
        add(genreLabel);

        JLabel ratingLabel = new JLabel("Rating:");
        ratingLabel.setBounds(20, 80, 80, 25);
        add(ratingLabel);

        titleField = new JTextField();
        titleField.setBounds(100, 20, 150, 25);
        add(titleField);

        genreField = new JTextField();
        genreField.setBounds(100, 50, 150, 25);
        add(genreField);

        ratingField = new JTextField();
        ratingField.setBounds(100, 80, 150, 25);
        add(ratingField);

        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Genre", "Rating"}, 0);
        movieTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.setBounds(300, 20, 450, 300);
        add(scrollPane);

        JButton addButton = new JButton("Add");
        addButton.setBounds(20, 120, 80, 25);
        add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(110, 120, 80, 25);
        add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(200, 120, 80, 25);
        add(deleteButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMovie();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateMovie();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteMovie();
            }
        });

        loadMovies();
    }

    private void loadMovies() {
        try {
            List<Movie> movies = movieDAO.getAllMovies();
            tableModel.setRowCount(0);
            for (Movie movie : movies) {
                tableModel.addRow(new Object[]{
                        movie.getId(),
                        movie.getTitle(),
                        movie.getGenre(),
                        movie.getRating()
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addMovie() {
        String title = titleField.getText();
        String genre = genreField.getText();
        float rating = Float.parseFloat(ratingField.getText());

        try {
            Movie movie = new Movie(0, title, genre, rating);
            movieDAO.addMovie(movie);
            JOptionPane.showMessageDialog(this, "Movie added successfully!");
            loadMovies();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateMovie() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a movie to update.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String title = titleField.getText();
        String genre = genreField.getText();
        float rating = Float.parseFloat(ratingField.getText());

        try {
            Movie movie = new Movie(id, title, genre, rating);
            movieDAO.updateMovie(movie);
            JOptionPane.showMessageDialog(this, "Movie updated successfully!");
            loadMovies();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteMovie() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a movie to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            movieDAO.deleteMovie(id);
            JOptionPane.showMessageDialog(this, "Movie deleted successfully!");
            loadMovies();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MovieManagement app = new MovieManagement();
            app.setVisible(true);
        });
    }
}

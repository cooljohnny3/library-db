package com.john.librarydb.dao;

import com.john.librarydb.model.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("mySQL")
public class MySQLDao implements PersonDAO, BookDAO, GenreDAO, AuthorDAO, LibraryDAO {
    private Connection connection;
    private static final String url = "jdbc:mysql://192.168.1.184:3306/librarydb";
    private static final String user = "minty";
    private static final String password = "greatsqldb";

    public MySQLDao() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error in SQL connection.");
            e.printStackTrace();
        }
    }

    // PersonDAO

    @Override
    public int addPerson(UUID id, Person person) {
        String query = "INSERT INTO people (id,firstname,middlename,lastname) VALUES (?,?,?,?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getMiddleName());
            preparedStatement.setString(4, person.getLastName());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error adding person");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public int deletePersonById(UUID id) {
        String query = "DELETE FROM people WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error deleting person with id " + id);
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        String query = "UPDATE people SET firstname=?, middlename=?, lastname=? WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getMiddleName());
            preparedStatement.setString(3, person.getLastName());
            preparedStatement.setString(4, id.toString());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error deleting person with id " + id);
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public List<Person> getPeople() {
        ArrayList<Person> peopleList = new ArrayList<>();
        String query = "SELECT * FROM people;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                peopleList.add(new Person(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("lastname")
                        )
                );
            }
        } catch(SQLException e) {
            System.out.println("Error getting people");
            e.printStackTrace();
        }
        return peopleList;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        String query = "SELECT * FROM people WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(new Person(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("lastname")
                ));
            }
        } catch(SQLException e) {
            System.out.println("Error getting person");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // BookDAO

    @Override
    public int addBook(UUID id, Book book) {
        String query = "INSERT INTO books (id,title,genreID,authorID) VALUES (?,?,?,?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getGenre().getId().toString());
            preparedStatement.setString(4, book.getAuthor().getId().toString());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error adding book");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteBookById(UUID id) {
        String query = "DELETE FROM books WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error deleting book with id " + id);
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public int updateBookById(UUID id, Book book) {
        System.out.println(id);
        String query = "UPDATE books SET title=?, genreID=?, authorID=? WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getGenre().getId().toString());
            preparedStatement.setString(3, book.getAuthor().getId().toString());
            preparedStatement.setString(4, id.toString());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error updating person with id " + id);
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public List<Book> getBooks() {
        ArrayList<Book> bookList = new ArrayList<>();
        String query =
                "SELECT books.id, books.title, books.authorID, authors.firstname 'authorFirstName', authors.lastname 'authorLastName', books.genreID, genres.name 'genre' \n" +
                "FROM books\n" +
                "INNER JOIN authors ON books.authorID=authors.id \n" +
                "INNER JOIN genres ON books.genreID=genres.id;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                bookList.add(new Book(
                                UUID.fromString(rs.getString("id")),
                                rs.getString("title"),
                                new Author(
                                        UUID.fromString(rs.getString("authorID")),
                                        rs.getString("authorFirstName"),
                                        rs.getString("authorLastName")),
                                new Genre(
                                        UUID.fromString(rs.getString("genreID")),
                                        rs.getString("genre")
                                ),
                                null
                        )
                );
            }
        } catch(SQLException e) {
            System.out.println("Error getting books");
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public Optional<Book> selectBookById(UUID id) {
        String query =
                "SELECT books.id, books.title, books.authorID, authors.firstname 'authorFirstName', authors.lastname 'authorLastName', books.genreID, genres.name 'genre' \n" +
                "FROM books\n" +
                "INNER JOIN authors ON books.authorID=authors.id \n" +
                "INNER JOIN genres ON books.genreID=genres.id \n" +
                "WHERE books.id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(new Book(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("title"),
                        new Author(
                                UUID.fromString(rs.getString("authorID")),
                                rs.getString("authorFirstName"),
                                rs.getString("authorLastName")),
                        new Genre(
                                UUID.fromString(rs.getString("genreID")),
                                rs.getString("genre")
                        ),
                        null)
                );
            }
        } catch(SQLException e) {
            System.out.println("Error getting person");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // AuthorDAO

    @Override
    public int addAuthor(UUID id, Author author) {
        String query = "INSERT INTO authors (id,firstname,lastname) VALUES (?,?,?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            preparedStatement.setString(2, author.getFirstName());
            preparedStatement.setString(3, author.getLastName());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error adding author");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public Optional<Author> getAuthorById(UUID id) {
        String query = "SELECT * FROM authors WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(new Author(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                ));
            }
        } catch(SQLException e) {
            System.out.println("Error getting author");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> getAuthorByName(String firstName, String lastName) {
        String query = "SELECT * FROM authors WHERE firstname=? AND lastname=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(new Author(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                ));
            }
        } catch(SQLException e) {
            System.out.println("Error getting author");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public int removeAuthorById(UUID id) {
        String query = "DELETE FROM authors WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error deleting author with id " + id);
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    // GenreDAO

    @Override
    public int addGenre(UUID id, Genre genre) {
        String query = "INSERT INTO genres (id,name) VALUES (?,?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            preparedStatement.setString(2, genre.getName());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error adding genre");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public Optional<Genre> getGenreById(UUID id) {
        String query = "SELECT * FROM genres WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(new Genre(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name")
                ));
            }
        } catch(SQLException e) {
            System.out.println("Error getting author");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        String query = "SELECT * FROM genres WHERE name=? ;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(new Genre(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name")
                ));
            }
        } catch(SQLException e) {
            System.out.println("Error getting genre");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public int removeGenreById(UUID id) {
        String query = "DELETE FROM authors WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error deleting author with id " + id);
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    // LibraryDAO

    @Override
    public int addLibrary(UUID id, Library library) {
        String query = "INSERT INTO libraries (id,name) VALUES (?,?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            preparedStatement.setString(2, library.getName());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error adding library");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteLibraryById(UUID id) {
        String query = "DELETE FROM libraries WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error deleting library with id " + id);
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public int updateLibraryById(UUID id, Library library) {
        System.out.println(id);
        String query = "UPDATE libraries SET name=? WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, library.getName());
            preparedStatement.setString(4, id.toString());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error updating lobrary with id " + id);
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public List<Library> getLibraries() {
        ArrayList<Library> libraryList = new ArrayList<>();
        String query = "SELECT * FROM libraries;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                libraryList.add(new Library(
                                UUID.fromString(rs.getString("id")),
                                rs.getString("name")
                        )
                );
            }
        } catch(SQLException e) {
            System.out.println("Error getting libraries");
            e.printStackTrace();
        }
        return libraryList;
    }

    @Override
    public Optional<Library> selectLibraryById(UUID id) {
        String query = "SELECT * FROM libraries WHERE id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id.toString());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(new Library(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name")
                ));
            }
        } catch(SQLException e) {
            System.out.println("Error getting library");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public int addBookToLibrary(Book book) {
        String query = "INSERT INTO librarybooks (libraryID,bookID) VALUES (?,?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getLibraryId().toString());
            preparedStatement.setString(2, book.getId().toString());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error adding library book");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public int removeBookFromLibrary(Book book) {
        String query = "DELETE FROM librarybooks WHERE bookID=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getId().toString());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Error removing library book");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public List<Book> getBooksAtLibrary(Library library) {
        ArrayList<Book> bookList = new ArrayList<>();
        String query =
                "SELECT books.id, books.title, books.authorID, authors.firstname 'authorFirstName', authors.lastname 'authorLastName', books.genreID, genres.name 'genre', librarybooks.libraryID \n" +
                        "FROM books\n" +
                        "INNER JOIN authors ON books.authorID=authors.id \n" +
                        "INNER JOIN genres ON books.genreID=genres.id \n" +
                        "JOIN librarybooks ON books.id=librarybooks.bookID \n" +
                        "JOIN libraries ON librarybooks.libraryID=libraries.id";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                bookList.add(new Book(
                                UUID.fromString(rs.getString("id")),
                                rs.getString("title"),
                                new Author(
                                        UUID.fromString(rs.getString("authorID")),
                                        rs.getString("authorFirstName"),
                                        rs.getString("authorLastName")),
                                new Genre(
                                        UUID.fromString(rs.getString("genreID")),
                                        rs.getString("genre")
                                ),
                                UUID.fromString(rs.getString("libraryID"))
                        )
                );
            }
        } catch(SQLException e) {
            System.out.println("Error getting books");
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public int transfer(Library library1, Library library2, Book book) {
        return 0;
    }
}

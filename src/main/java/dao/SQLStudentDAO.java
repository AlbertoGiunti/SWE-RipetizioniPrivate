package dao;

import domainModel.Student;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
public class SQLStudentDAO implements StudentDAO {

    /**
     * get a specific student
     */
    @Override

    public Student get(String CF) throws SQLException{
        // Create a connection with the DB
        Connection con = Database.getConnection();
        Student student = null;
        // Questa riga prepara una dichiarazione SQL parametrizzata per selezionare i dati dei trainer dal database in base al codice fiscale
        PreparedStatement ps = con.prepareStatement("SELECT * FROM students WHERE cf = ?"); // ? = placeholder
        // Questa riga imposta il valore del primo segnaposto
        ps.setString(1, CF);
        ResultSet rs = ps.executeQuery();

        // Se c'è un risultato
        if (rs.next()){
            student = new Student(
                    rs.getString("cf"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("iban")
            );
        }

        rs.close();
        ps.close();
        Database.closeConnection(con);
        return student;
    }

    /**
     *  get all tutors
     */
    @Override
    public List<Student> getAll() throws SQLException{
        Connection con = Database.getConnection();
        List<Student> students = new ArrayList<>();
        // Statement -- used to send basic SQL statements
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM students");

        // Finché ci sono altri risultati continua
        while (rs.next()){
            students.add(new Student(
                    rs.getString("cf"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("iban")
            ));
        }

        return students;
    }

    /**
     * insert method
     */
    public void insert(Student newStudent) throws SQLException{
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO students (cf, name, surname, level) VALUES (?, ?, ?, ?)");
        ps.setString(1, newStudent.getCF());
        ps.setString(2, newStudent.getName());
        ps.setString(3, newStudent.getSurname());
        ps.setString(4, newStudent.getLevel());

        ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);
    }

    /**
     * update method
     */
    @Override
    public void update(Student updatedStudent) throws SQLException{
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE students SET name = ?, surname = ?, level = ? WHERE cf = ?");
        ps.setString(1, updatedStudent.getName());
        ps.setString(2, updatedStudent.getSurname());
        ps.setString(3, updatedStudent.getLevel());
        ps.setString(4, updatedStudent.getCF());

        ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);
    }

    /**
     * delete method
     */
    @Override
    public boolean delete(String CF) throws SQLException{
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM students WHERE cf = ?");
        ps.setString(1, CF);
        int rows = ps.executeUpdate();
        ps.close();
        Database.closeConnection(con);
        return rows > 0;
    }

}

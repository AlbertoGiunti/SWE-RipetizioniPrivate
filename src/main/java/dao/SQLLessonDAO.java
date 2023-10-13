package dao;

import domainModel.Advertisement;
import domainModel.Lesson;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class SQLLessonDAO implements LessonDAO {

    private final StudentDAO studentDAO;

    public SQLLessonDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    // Get method

    @Override
    public Lesson get(Integer id) throws SQLException {
        Connection con = Database.getConnection();
        Lesson lesson = null;
        PreparedStatement ps = con.prepareStatement("SELECT * FROM lessons WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            lesson = new Lesson(
                    rs.getInt("adID"),
                    rs.getString("studentCF")
            );
        }


        rs.close();
        ps.close();
        Database.closeConnection(con);
        return lesson;
    }

    // Get all lessons
    @Override
    public List<Lesson> getAll() throws SQLException{
        Connection con = Database.getConnection();
        List<Lesson> lessons = new ArrayList<>();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM lessons");

        while (rs.next()){
            Lesson lesson = new Lesson(
                    rs.getInt("id"),
                    rs.getString("studentCF")
            );

            lessons.add(lesson);
        }


        rs.close();
        stmt.close();
        Database.closeConnection(con);
        return lessons;
    }


    // Insert method
    @Override
    public void insert(Lesson lesson) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO lessons (id, studentCF) VALUES (?, ?)");
        // id is not needed because is autoincremented
        ps.setInt(1, lesson.getAdID());
        ps.setString(2, lesson.getStudentCF());

        ps.close();
        Database.closeConnection(con);
    }

    // Update method
    //TODO  Ricontrolla!!
    //Modifica quale studente è prenotato a quella lezione, Gli id tanto non vengono eliminati
    @Override
    public void update(Lesson lesson) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE lessons SET studentCF = ? WHERE id =?");
        ps.setString(1, lesson.getStudentCF());
        ps.setInt(2, lesson.getAdID());

        ps.close();
        Database.closeConnection(con);
    }

    // Delete method
    @Override
    public boolean delete(Integer id) throws SQLException {
        // Usa il metodo get scritto prima
        Lesson lesson = get(id);
        if (lesson == null) {
            return false;
        }
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM lessons WHERE id = ?");
        ps.setInt(1, id);
        int rows = ps.executeUpdate();

        ps.close();
        Database.closeConnection(con);
        return rows > 0;
    }

}

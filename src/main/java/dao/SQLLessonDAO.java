package dao;

import domainModel.Lesson;
import org.w3c.dom.CDATASection;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;


public class SQLLessonDAO implements LessonDAO{
    private final TutorDAO tutorDAO;
    private final StudentDAO studentDAO;

    public SQLLessonDAO(TutorDAO tutorDAO, StudentDAO studentDAO){
        this.tutorDAO = tutorDAO;
        this.studentDAO = studentDAO;
    }

    // Get a specific lesson
    @Override
    public Lesson get(Integer id) throws SQLException{
        Connection con = Database.getConnection();
        Lesson l = null;
        PreparedStatement ps = con.prepareStatement("SELECT * FROM lessons WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            l = new Lesson(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("subject"),
                    rs.getString("level"),
                    LocalDateTime.parse(rs.getString("date")),
                    LocalDateTime.parse(rs.getString("startTime")),
                    LocalDateTime.parse(rs.getString("endTime")),
                    rs.getString("zone"),
                    rs.getInt("isOnline"),
                    rs.getFloat("price"),
                    rs.getString("tutorCF")
            );

            l.setBooked(rs.getInt("booked"));
            l.setStudentCF(rs.getString("studentCF"));
        }

        rs.close();
        ps.close();

        Database.closeConnection(con);
        return l;
    }

    // Get all lessons
    @Override
    public List<Lesson> getAll() throws SQLException{
        Connection con = Database.getConnection();
        List<Lesson> lessons = new ArrayList<>();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM lessons");

        while (rs.next()){
            Lesson l = new Lesson(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("subject"),
                    rs.getString("level"),
                    LocalDateTime.parse(rs.getString("date")),
                    LocalDateTime.parse(rs.getString("startTime")),
                    LocalDateTime.parse(rs.getString("endTime")),
                    rs.getString("zone"),
                    rs.getInt("isOnline"),
                    rs.getFloat("price"),
                    rs.getString("tutorCF")
            );
            l.setBooked(rs.getInt("booked"));
            l.setStudentCF(rs.getString("studentCF"));

            lessons.add(l);
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
                "INSERT INTO lessons (title, description, subject, level, date, startTime, endTime, zone, isOnline, price, tutorCF) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        // id is not needed because is autoincremented
        ps.setString(1, lesson.getTitle());
        ps.setString(2, lesson.getDescription());
        ps.setString(3, lesson.getSubject());
        ps.setString(4, lesson.getLevel());
        ps.setString(5, lesson.getDate().toString());
        ps.setString(6, lesson.getStartTime().toString());
        ps.setString(7, lesson.getEndTime().toString());
        ps.setString(8, lesson.getZone());
        ps.setInt(9, lesson.isOnline());
        ps.setFloat(10, lesson.getPrice());
        ps.setString(11, lesson.getTutorCF());

        ps.close();
        Database.closeConnection(con);
    }

    // Update method
    // TODO Solo il proprietario può modificare la lezione
    @Override
    public void update(Lesson lesson) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE lessons SET title = ?, description = ?, subject = ?, level = ?, date = ?, startTime = ?, endTime = ?, zone = ?, isOnline = ?, price = ? WHERE id =?"); // Non ho messo tutorCF, non ha senso
        ps.setString(1, lesson.getTitle());
        ps.setString(2, lesson.getDescription());
        ps.setString(3, lesson.getSubject());
        ps.setString(4, lesson.getLevel());
        ps.setString(5, lesson.getDate().toString());
        ps.setString(6, lesson.getStartTime().toString());
        ps.setString(7, lesson.getEndTime().toString());
        ps.setString(8, lesson.getZone());
        ps.setInt(9, lesson.isOnline());
        ps.setFloat(10, lesson.getPrice());
        ps.setInt(11, lesson.getId());

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

    // Get all the lessons booked by a student
    public List<Lesson> getStudentLessons(String stCF) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM lessons WHERE studentCF = ?");
        ps.setString(1, stCF);
        ResultSet rs = ps.executeQuery();

        List<Lesson> lessons = new ArrayList<>();
        // TODO Si può migliorare?
        while (rs.next()) {
            lessons.add(this.get(rs.getInt("id"))); // TODO riguardare

            /**
            Lesson l = new Lesson(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("subject"),
                    rs.getString("level"),
                    LocalDateTime.parse(rs.getString("date")),
                    LocalDateTime.parse(rs.getString("startTime")),
                    LocalDateTime.parse(rs.getString("endTime")),
                    rs.getString("zone"),
                    rs.getInt("isOnline"),
                    rs.getFloat("price"),
                    rs.getString("tutorCF")
            );
            l.setBooked(rs.getInt("booked"));
            l.setStudentCF(stCF);

            lessons.add(l);
             */
        }

        rs.close();
        ps.close();
        Database.closeConnection(con);
        return lessons;
    }

}

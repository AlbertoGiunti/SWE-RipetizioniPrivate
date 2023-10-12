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

    /**
     * get a specific lesson
     */
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

    /**
     * get all lessons
     */
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


}

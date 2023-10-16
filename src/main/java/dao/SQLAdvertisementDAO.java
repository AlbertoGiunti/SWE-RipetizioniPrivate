package dao;

import domainModel.Advertisement;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;


public class SQLAdvertisementDAO implements AdvertisementDAO {
    private final TutorDAO tutorDAO;

    public SQLAdvertisementDAO(TutorDAO tutorDAO, StudentDAO studentDAO){
        this.tutorDAO = tutorDAO;
    }

    // Get a specific advertisement
    @Override
    public Advertisement get(Integer id) throws SQLException{
        Connection con = Database.getConnection();
        Advertisement ad = null;
        PreparedStatement ps = con.prepareStatement("SELECT * FROM advertisements WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            ad = new Advertisement(
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

        }

        rs.close();
        ps.close();

        Database.closeConnection(con);
        return ad;
    }

    // Get all advertisements
    @Override
    public List<Advertisement> getAll() throws SQLException{
        Connection con = Database.getConnection();
        List<Advertisement> advertisements = new ArrayList<>();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM advertisements");

        while (rs.next()){
            Advertisement ad = new Advertisement(
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

            advertisements.add(ad);
        }
        rs.close();
        stmt.close();
        Database.closeConnection(con);
        return advertisements;
    }


    // Insert method

    @Override
    public void insert(Advertisement advertisement) throws Exception {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO advertisements (title, description, subject, level, date, startTime, endTime, zone, isOnline, price, tutorCF) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        // id is not needed because is autoincremented
        ps.setString(1, advertisement.getTitle());
        ps.setString(2, advertisement.getDescription());
        ps.setString(3, advertisement.getSubject());
        ps.setString(4, advertisement.getLevel());
        ps.setString(5, advertisement.getDate().toString());
        ps.setString(6, advertisement.getStartTime().toString());
        ps.setString(7, advertisement.getEndTime().toString());
        ps.setString(8, advertisement.getZone());
        ps.setInt(9, advertisement.isOnline());
        ps.setFloat(10, advertisement.getPrice());
        ps.setString(11, advertisement.getTutorCF());

        ps.close();
        Database.closeConnection(con);
    }

    // Update method
    // TODO Solo il proprietario può modificare la lezione
    @Override
    public void update(Advertisement advertisement) throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE advertisements SET title = ?, description = ?, subject = ?, level = ?, date = ?, startTime = ?, endTime = ?, zone = ?, isOnline = ?, price = ? WHERE id =?"); // Non ho messo tutorCF, non ha senso
        ps.setString(1, advertisement.getTitle());
        ps.setString(2, advertisement.getDescription());
        ps.setString(3, advertisement.getSubject());
        ps.setString(4, advertisement.getLevel());
        ps.setString(5, advertisement.getDate().toString());
        ps.setString(6, advertisement.getStartTime().toString());
        ps.setString(7, advertisement.getEndTime().toString());
        ps.setString(8, advertisement.getZone());
        ps.setInt(9, advertisement.isOnline());
        ps.setFloat(10, advertisement.getPrice());
        ps.setInt(11, advertisement.getId());

        ps.close();
        Database.closeConnection(con);
    }

    // Delete method
    @Override
    public boolean delete(Integer id) throws SQLException {
        // Usa il metodo get scritto prima
        Advertisement advertisement = get(id);
        if (advertisement == null) {
            return false;
        }
        Connection con = Database.getConnection();
        //TODO Cancella anche le lezioni?
        PreparedStatement ps = con.prepareStatement("DELETE FROM advertisements WHERE id = ?");
        ps.setInt(1, id);
        int rows = ps.executeUpdate();

        ps.close();
        Database.closeConnection(con);
        return rows > 0;
    }

    @Override
    // Get all the advertisements created by a Tutor
    public List<Advertisement> getTutorAdvertisements(String tCF) throws SQLException {
        Connection con = Database.getConnection();
        //TODO riguarda questa query
        PreparedStatement ps = con.prepareStatement("SELECT * FROM advertisements LEFT JOIN lessons ON advertisements.id == lessons.id WHERE tutorCF = ? AND lessons.studentCF == null");
        ps.setString(1, tCF);
        ResultSet rs = ps.executeQuery();

        List<Advertisement> advertisements = new ArrayList<>();
        while (rs.next()) {
            advertisements.add(this.get(rs.getInt("id"))); // TODO riguardare

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
        return advertisements;
    }

    @Override
    public int getLastAdID() throws SQLException{
        Connection connection = Database.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM main.advertisements");
        int id = rs.getInt(1) + 1;

        rs.close();
        stmt.close();
        Database.closeConnection(connection);
        return id;
    }

}

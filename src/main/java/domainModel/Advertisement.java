package domainModel;

import java.time.LocalDateTime;
import java.util.Objects;

public class Advertisement {
    // This is the ID of the advertisement
    private final int id;
    // This is the title of the advertisement
    private String title;
    // This is the subject of the lesson
    private String subject;
    // This is the level (Elementary, Highschool, College, ecc...) of the lesson
    private String level;
    // This is the description of the advertisement
    private String description;
    // This is the date of the lesson
    private LocalDateTime date;
    // This is the start time of the lesson and the end time of the lesson
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // This indicates where the tutor can do the lesson
    private String zone;
    // This indicates if the lesson is online or not
    private int isOnline;
    // This is the price of the lesson
    private float price;
    // This is the CF of the tutor
    private final String tutorCF;

    // Constructor
    public Advertisement(int id, String title, String description, String subject,
                         String level, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime,
                         String zone, int isOnline, float price, String tutorCF) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subject = subject;
        this.level = level;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.zone = zone;
        this.isOnline = isOnline;
        this.price = price;
        this.tutorCF = tutorCF;

        // Check if the time range is valid
        if (endTime.equals(startTime) || endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("Invalid time range");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertisement advertisement = (Advertisement) o;
        return Objects.equals(id, advertisement.id);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getZone() {
        return zone;
    }

    public int isOnline() {
        return isOnline;
    }

    public float getPrice() {
        return price;
    }

    public String getTutorCF() {
        return tutorCF;
    }

    // toString method

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subject='" + subject + '\'' +
                ", level='" + level + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", zone='" + zone + '\'' +
                ", isOnline=" + isOnline +
                ", price=" + price +
                ", tutorCF='" + tutorCF + '\'' +
                '}';
    }
}

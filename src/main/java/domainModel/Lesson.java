package domainModel;

import java.time.LocalDateTime;
import java.util.Objects;

public class Lesson {
    // This is the ID of the lesson
    private final String id;
    // This is the title of the lesson
    private String title;
    // This is the subject of the lesson
    private String subject;
    // This is the level (Elementary, Highschool, College, ecc...) of the lesson
    private String level;
    // This is the description of the lesson
    private String description;
    // This is the date of the lesson
    private LocalDateTime date;
    // This is the start time of the lesson and the end time of the lesson
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // This indicates where the tutor can do the lesson
    private String zone;
    // This indicates if the lesson is online or not
    private boolean isOnline;
    // This is the price of the lesson
    private float price;
    // This indicates if the lesson is booked or not, by default is "false"
    private String booked = "false";
    // This is the CF of the tutor and the CF of the student
    private final String tutorCF;
    private String studentCF;

    // Constructor
    public Lesson(String id, String title, String subject, String level, String description, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime, String zone, boolean isOnline, float price, String tutorCF) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.level = level;
        this.description = description;
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
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id);
    }

    // Getters
    public String getId() {
        return id;
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

    public boolean isOnline() {
        return isOnline;
    }

    public double getPrice() {
        return price;
    }

    public String getBooked() {
        return booked;
    }

    public String getTutorCF() {
        return tutorCF;
    }

    public String getStudentCF() {
        return studentCF;
    }

    // Setters
    public void setBooked(String booked) {
        this.booked = booked;
    }

    public void setStudentCF(String studentCF) {
        this.studentCF = studentCF;
    }
}

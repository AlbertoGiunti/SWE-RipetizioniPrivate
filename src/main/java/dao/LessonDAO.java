package dao;

import domainModel.Lesson;
import java.util.List;

public interface LessonDAO extends DAO<Lesson, Integer>{
    /**
     * Get all the lessons booked by a Student
     *
     * @param studentCF The fiscal code of the student
     */
    public List<Lesson> getStudentLessons(String studentCF) throws Exception;

    /**
     * Get all the lesson created by a Tutor that are booked
     *
     * @param tutorCF the fiscal code of the tutor who created the lesson
     */
    public List<Lesson> getTutorLessons(String tutorCF) throws Exception;

    /**
     * Get all tutor's advertisements (lessons with booked = 0 = FALSE)
     *
     * @param tutorCF the fiscal code of the tutor who created the advertisement
     */
    public List<Lesson> getTutorAds(String tutorCF) throws Exception;

    /**
     * Adds a booking for the given student to the lesson with the given id
     *
     * @param cf The fiscal code of the customer to book the course for
     * @param courseId The id of the course to book
     * @throws Exception when the course or the customer doesn't exist or if there are problems to access the data source
     */
    public void bookLesson(String cf, Integer courseId) throws Exception;

    /**
     * Removes a booking for the given customer from the course with the given id
     *
     * @param cf The fiscal code of the customer to remove the booking for
     * @param Id The id of the course to remove the booking from
     * @return true if successful, false otherwise (i.e. customer not found in course or courseId not exiting)
     * @throws Exception if there are problems to access the data source
     */
    public boolean deleteBooking(String cf, Integer Id) throws Exception;
}

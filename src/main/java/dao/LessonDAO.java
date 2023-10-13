package dao;

import domainModel.Lesson;

import java.sql.SQLException;
import java.util.List;

public interface LessonDAO extends DAO <Lesson, Integer> {
    /**
     * Get Tutor's lessons
     *
     * @param tCF tutor CF
     */
    public List<Lesson> getTutorLessons(String tCF) throws Exception;

    /**
     * Get Student's lessons
     *
     * @param sCF student CF
     */
    public List<Lesson> getStudentLessons(String sCF) throws Exception;
}

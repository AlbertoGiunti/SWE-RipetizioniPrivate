package businessLogic;

import dao.LessonDAO;
import dao.StudentDAO;
import domainModel.Lesson;
import domainModel.Advertisement;
import domainModel.Student;

import java.util.List;

public class LessonsController {
    private final LessonDAO lessonDAO;
    private final AdvertisementsController advertisementsController;
    private final StudentsController studentsController;

    public LessonsController(LessonDAO lessonDAO, AdvertisementsController advertisementsController, StudentsController studentsController){
        this.advertisementsController = advertisementsController;
        this.lessonDAO = lessonDAO;
        this.studentsController = studentsController;
    }

    /**
     * Book a lesson for the given Student
     * 
     * @param studentCF The fiscal code of the student for whom to book the lesson for
     * @param adID The id of the advertisement to book
     *             
     * @throws Exception when adID doesn't exist and bubbles up exceptions of LessonDAO::insert(), LessonDAO::getAll(), LessonDAO::getStudentLessons
     */
    public void bookLesson(String studentCF, int adID) throws Exception{
        Advertisement ad = advertisementsController.getAdvertisement(adID);
        Student student = studentsController.getPerson(studentCF);
        // Controllo l'esistenza dei parametri
        if (ad == null) throw new RuntimeException("The given advertisement id doesn't exist");
        if (student == null) throw new RuntimeException("The given student doesn't exist");
        
        // Controllo che non sia già prenotata
        List<Lesson> lessons = lessonDAO.getAll();
        for (Lesson l : lessons){
            if (l.getAdID() == adID) {throw new RuntimeException("The given advertisement is already booked");}
        }

        // Controllo che lo studente non abbia già altri corsi in quel lasso di tempo
        List<Lesson> studentLessons = lessonDAO.getStudentLessons(studentCF);
        for (Lesson l : studentLessons){
            //Prendo l'advertisement corrispondente alla lezione
            Advertisement bookedAD = advertisementsController.getAdvertisement(l.getAdID());
            // Controllo se sono nello stesso giorno
            if (ad.getDate() == bookedAD.getDate()){
                if (ad.getStartTime().isBefore(bookedAD.getEndTime()) // Se l'ad da prenotare inizia prima che un'altra lezione finisca
                    || ad.getEndTime().isAfter(bookedAD.getStartTime()) // Se l'ad finisce dopo l'inizio di un'altra lezione
                ){
                    throw new RuntimeException("The given student is already booked for a lesson at the same time");
                }
            }
        }

        Lesson bookedLesson = new Lesson(adID,student.getCF());
        lessonDAO.insert(bookedLesson);
    }

    /**
     * Deletes a booking for a given student
     *
     * @param studentCF The fiscal code of the student for whom to remove the booking
     * @param lessonID The lesson id to remove
     *
     * @return true if succesful, false otherwise
     *
     * @throws Exception bubbles up exceptions of LessonDAO::deleteLesson()
     */
    public boolean deleteLessonBooking(String studentCF, int lessonID) throws Exception{
        Lesson l = lessonDAO.get(lessonID);
        List<Lesson> studentLessons = lessonDAO.getStudentLessons(studentCF);
        for (Lesson lesson : studentLessons){
            if (lesson.getAdID() == l.getAdID()){
                return lessonDAO.delete(lessonID);
            }
        }
        throw new RuntimeException("The student has not booked any lessons with that ID.");
    }

    /**
     * Returns a list of the lessons that the given student booked
     *
     * @param studentCF The fiscal code of the student
     *
     * @return A list of lessons that the given student booked
     *
     * @throws Exception bubbles up exceptions of LessonDAO::getStudentLessons
     */
    public List<Lesson> getBookingsForStudent(String studentCF) throws Exception{
        return lessonDAO.getStudentLessons(studentCF);
    }

    /**
     * Returns a list of the lessons that the given tutor has
     *
     * @param tutorCF The fiscal code of the tutor
     *
     * @return A list of lessons that the given tutor has
     *
     * @throws Exception bubbles up exceptions of LessonDAO::getTutorLessons
     */
    public List<Lesson> getLessonsWhereTutorIsBooked(String tutorCF) throws Exception {
        return lessonDAO.getTutorLessons(tutorCF);
    }

    /**
     * Get all the lessons
     *
     * @return The list of all the lessons
     * @throws Exception
     */
    public List<Lesson> getAll() throws Exception{
        return lessonDAO.getAll();
    }

    /**
     * Delete lesson, used by the tutor who created the lesson
     *
     * @param id The lesson id
     *
     * @throws Exception bubbles up exceptions of LessonDAO::deleteLesson()
     */
    public boolean deleteLesson(int id) throws Exception  {
        //TODO notify Student?
        return lessonDAO.delete(id);
    }
}

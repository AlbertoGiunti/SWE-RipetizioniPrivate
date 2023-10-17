import businessLogic.*;
import dao.*;


import java.time.LocalDateTime;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception{
        Database.setDatabase("main.db");
        Database.initDatabase();

        // DAOs
        TutorDAO tutorDAO = new SQLTutorDAO();
        StudentDAO studentDAO = new SQLStudentDAO();
        AdvertisementDAO advertisementDAO = new SQLAdvertisementDAO(tutorDAO, studentDAO);
        LessonDAO lessonDAO = new SQLLessonDAO(studentDAO);

        // Controllers
        StudentsController studentsController = new StudentsController(studentDAO);
        TutorsController tutorsController = new TutorsController(tutorDAO);
        AdvertisementsController advertisementsController = new AdvertisementsController(advertisementDAO, tutorsController, lessonDAO);
        LessonsController lessonsController = new LessonsController(lessonDAO, advertisementsController, studentsController);

        //Add sample:
        // Sample tutors
        tutorsController.addPerson("Tutor1CF", "Tutor", "1", "PLACEHOLDER1");
        tutorsController.addPerson("Tutor2CF", "Tutor", "2", "PLACEHOLDER2");

        // Sample students
        studentsController.addPerson("Student1CF", "Student", "1", "LEVEL1");
        studentsController.addPerson("Student2CF", "Student", "2", "LEVEL2");
        studentsController.addPerson("Student3CF", "Student", "3", "LEVEL3");

        // Sample advertisement
        int firstAd = advertisementsController.addAdvertisement("First Ad", "PLACEHOLDER", "Science", "LEVEL1", LocalDate.of(2023, 7, 22) , LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1),
                "Florence", 1, 30.00, "Tutor1CF");
        int secondAd = advertisementsController.addAdvertisement("Second Ad", "PLACEHOLDER", "Biology", "LEVEL3", LocalDate.of(2023, 7, 12) , LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1),
                "Florence", 1, 30.00, "Tutor1CF");
        int thirdAd = advertisementsController.addAdvertisement("Third Ad", "PLACEHOLDER", "Math", "LEVEL1", LocalDate.of(2023, 7, 22) , LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1),
                "Florence", 1, 30.00, "Tutor2CF");


        lessonsController.bookLesson("Student1CF", firstAd);
        lessonsController.bookLesson("Student2CF", secondAd);
        lessonsController.bookLesson("Student3CF", thirdAd);


    }
}
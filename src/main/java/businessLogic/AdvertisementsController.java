package businessLogic;

import dao.AdvertisementDAO;
import dao.LessonDAO;
import domainModel.Advertisement;
import domainModel.Lesson;
import domainModel.Tutor;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.unmodifiableList;


public class AdvertisementsController {
    private final AdvertisementDAO advertisementDAO;
    private final PeopleController<Tutor> tutorsController;
    private final LessonDAO lessonDAO;

    public AdvertisementsController(AdvertisementDAO advertisementDAO, PeopleController<Tutor> tutorsController, LessonDAO lessonDAO){
        this.advertisementDAO = advertisementDAO;
        this.tutorsController = tutorsController;
        this.lessonDAO = lessonDAO;
    }

    /**
     * Adds a new advertisement to the list
     *
     * @param title The title of the advertisement
     * @param description The description of the advertisement
     * @param subject The subject of the lesson
     * @param level The level of the lesson
     * @param date The date of the lesson
     * @param startTime The time of start of the lesson
     * @param endTime The time of end of the lesson
     * @param zone Where the tutor can do the lesson
     * @param isOnline Tells if the lesson is online or not
     * @param price The price of the lesson
     * @param tutorCF The tutor who posted the advertisement
     *
     * @return The id of the newly created advertisement
     *
     * @throws Exception If the tutor is not found, bubbles up exceptions of AdvertisementDAO::insert()
     * @throws IllegalArgumentException If the trainer is already occupied in the given time range
     */
    public int addAdvertisement(String title, String description, String subject, String level, LocalDate date, LocalDateTime startTime, LocalDateTime endTime, String zone, int isOnline, double price, String tutorCF) throws Exception {
        Tutor tutor = tutorsController.getPerson(tutorCF);
        if (tutor == null)
            throw new IllegalArgumentException("Tutor not found");


        //  Check if the given tutor is not already occupied for the given time range
        for(Advertisement ad: this.advertisementDAO.getTutorAdvertisements(tutorCF)){
            // Se non ci sono ad di quel tutor non esegue il for

            // Prima controllo se il nuovo avviso ha startTime precedente e diverso ad endTime
            if(startTime.isAfter(endTime) || endTime.isEqual(startTime)){
                throw new IllegalArgumentException("The course start time must be before the course end time.");
            }

            // Dopo controllo se il nuovo avviso si sovrappone a uno già esistente
            if(ad.getDate().isEqual(date)){
                if(ad.getStartTime().isBefore(endTime) || ad.getEndTime().isAfter(startTime)){
                    throw new IllegalArgumentException("The given tutor is already occupied in the given time range (in course #" + ad.getId() + ")");
                }
            }
        }


        Advertisement ad = new Advertisement(advertisementDAO.getLastAdID(), title, description, subject, level, date, startTime, endTime, zone, isOnline, price, tutorCF);
        advertisementDAO.insert(ad);
        return ad.getId();

    }

    /**
     * Remove an advertisement
     *
     * @param id The id of the advertisement to delete
     *
     * @return true if successful, false otherwise
     *
     * @throws Exception bubbles up exceptions of AdvertisementDAO::delete()
     */
    //TODO lo può chiamare solo il professore proprietario del'advertisement
    //TODO Ricontrolla se è corretto come cancella la lezione
    //TODO Deve notificare lo studente?
    public boolean removeAdvertisement(int id) throws Exception{
        List<Lesson> lessons = lessonDAO.getTutorLessons(advertisementDAO.get(id).getTutorCF()); // Prendo tutte le lezioni di quel tutor
        for(Lesson l: lessons){
            if(l.getAdID() == id){
                //TODO notifica il lessonsController così che cancelli la lezione
                //TODO prima avevo passato il lessonsController ma così non potevo dichiarare advertisementsController senza prima aver dichiarato il lessonsController e viceversa!!
            }
        }

        return advertisementDAO.delete(id);
    }

    /**
     * Returns the given advertisement
     *
     * @param id The advertisement id to fetch
     *
     * @return The advertisement
     *
     * @throws Exception bubbles up exceptions of AdvertisementDAO::get()
     */
    public Advertisement getAdvertisement(int id) throws Exception{
        return advertisementDAO.get(id);
    }

    /**
     * Returns a read-only list of advertisements
     *
     * @return The list of advertisements
     *
     * @throws Exception bubbles up exceptions of AdvertisementDAO::getAll()
     */
    public List<Advertisement> getAll() throws Exception {
        return unmodifiableList(this.advertisementDAO.getAll());
    }


}

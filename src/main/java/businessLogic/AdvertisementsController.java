package businessLogic;

import dao.AdvertisementDAO;
import domainModel.Advertisement;
import domainModel.Tutor;

import java.time.LocalDateTime;
import java.util.List;


public class AdvertisementsController {
    private final AdvertisementDAO advertisementDAO;
    private final PeopleController<Tutor> tutorsController;

    public AdvertisementsController(AdvertisementDAO advertisementDAO, PeopleController<Tutor> tutorsController){
        this.advertisementDAO = advertisementDAO;
        this.tutorsController = tutorsController;
    }

    /**
     * Adds a new advertisement to the list
     *
     * int id, String title, String description, String subject,
     *                          String level, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime,
     *                          String zone, int isOnline, float price, String tutorCF
     * @param title
     * @param description
     * @param subject
     * @param level
     * @param date
     * @param startTime
     * @param endTime
     * @param zone
     * @param isOnline
     * @param price
     * @param tutorCF
     *
     * @return The id of the newly created advertisement
     *
     * @throws Exception If the tutor is not found, bubbles up exceptions of AdvertisementDAO::insert()
     * @throws IllegalArgumentException If the trainer is already occupied in the given time range
     */
    public int addAdvertisement(String title, String description, String subject, String level, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime, String zone, int isOnline, float price, String tutorCF) throws Exception {
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
}

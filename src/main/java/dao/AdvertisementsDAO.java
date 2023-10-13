package dao;

import domainModel.Advertisement;

import java.util.List;

public interface AdvertisementsDAO extends DAO<Advertisement, Integer>{
    /**
     * Get all tutor's advertisements
     *
     * @param tutorCF the fiscal code of the tutor who created the advertisement
     */
    public List<Advertisement> getTutorAdvertisements(String tutorCF) throws Exception;
}

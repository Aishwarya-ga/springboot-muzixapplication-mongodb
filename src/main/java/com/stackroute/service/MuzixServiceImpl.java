/**
 * Implimentation of the MuzixService interface
 */
package com.stackroute.service;

import com.stackroute.domain.Muzix;
import com.stackroute.exception.MuzixAlreadyExistsException;
import com.stackroute.exception.MuzixNotFoundException;
import com.stackroute.repository.MuzixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MuzixServiceImpl implements MuzixService {

    //Created a variable of MuzixRepository
    private MuzixRepository muzixRepository;

    //Autowired the constructor
    @Autowired
    public MuzixServiceImpl(MuzixRepository muzixRepository) {
        this.muzixRepository = muzixRepository;
    }

    //Overriden method to save the muzix
    @Override
    public Muzix saveMuzix(Muzix muzix) throws MuzixAlreadyExistsException{
        if(muzixRepository.existsById(muzix.getTrackId())){
            throw new MuzixAlreadyExistsException("Track already exists");
        }
        Muzix savedMuzix = muzixRepository.save(muzix);
        return savedMuzix;
    }

    //Overriden method to get all the muzixs
    @Override
    public List<Muzix> getAllMuzix() throws MuzixNotFoundException{
        if (muzixRepository.findAll().isEmpty()) {
            throw new MuzixNotFoundException("No tracks available");
        }
        return muzixRepository.findAll();
    }

    //Overriden method to update the muzix
    @Override
    public Muzix updateMuzix(int trackId,String comment) throws MuzixNotFoundException {

        if (muzixRepository.findById(trackId).isEmpty()) {
            throw new MuzixNotFoundException("Track not found to update");
        }
        Optional<Muzix> muzix = muzixRepository.findById(trackId);
        Muzix muzix1 = muzix.get();
        muzix1.setComment(comment);
        Muzix savedMuzix = muzixRepository.save(muzix1);
        return savedMuzix;
    }

    //Overriden method to remove the muzix
    @Override
    public void removeMuzix(int trackId) throws MuzixNotFoundException{
        if(!muzixRepository.existsById(trackId)){
            throw new MuzixNotFoundException("Track not found");
        }
        muzixRepository.deleteById(trackId);
    }

    //Overriden method to track by id
    @Override
    public Muzix trackByTrackId(int trackId) throws MuzixNotFoundException {
        if (muzixRepository.findById(trackId).isEmpty()) {
            throw new MuzixNotFoundException("Track not found to update");
        }
        Optional<Muzix> muzix1 = muzixRepository.findById(trackId);
        Muzix muzix = muzix1.get();
        return muzix;
    }

    //Service implimentation to track by name
    @Override
    public List<Muzix> trackByTrackName(String trackName)throws MuzixNotFoundException {
        if (muzixRepository.findByTrackName(trackName).isEmpty()){
            throw new MuzixNotFoundException("Track with given name is not found");
        }
        return muzixRepository.findByTrackName(trackName);
    }
}

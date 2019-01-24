/**
 * Interface that contains the services peerformed by muzix application
 */
package com.stackroute.service;

import com.stackroute.domain.Muzix;
import com.stackroute.exception.MuzixAlreadyExistsException;
import com.stackroute.exception.MuzixNotFoundException;

import java.util.List;

public interface MuzixService {
    public Muzix saveMuzix(Muzix muzix)throws MuzixAlreadyExistsException;
    public List<Muzix> getAllMuzix()throws MuzixNotFoundException;
    public Muzix updateMuzix(int trackId,String comment)throws MuzixNotFoundException;
    public void removeMuzix(int trackId) throws MuzixNotFoundException;
    public Muzix trackByTrackId(int trackId) throws MuzixNotFoundException;
    public List<Muzix> trackByTrackName(String trackName)throws MuzixNotFoundException;
}

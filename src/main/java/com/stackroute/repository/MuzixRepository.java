/**
 * A Repository interface that extends JpaRepository with type Muzix and primary key of type Integer
 */
package com.stackroute.repository;

import com.stackroute.domain.Muzix;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuzixRepository extends MongoRepository<Muzix,Integer> {

        //Query to track by name of track
        //@Query("SELECT m FROM Muzix m WHERE m.trackName=?1")
        public List<Muzix> findByTrackName(String trackName);
}

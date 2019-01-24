/**
 * Controller to control the Muzix Application
 */
package com.stackroute.controller;

import com.stackroute.domain.Muzix;
import com.stackroute.exception.MuzixAlreadyExistsException;
import com.stackroute.exception.MuzixNotFoundException;
import com.stackroute.service.MuzixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestController //used to enable @Controller, and  @Responsebody
@RequestMapping(value="api/v1")
public class MuzixController extends ResponseEntityExceptionHandler {

    //A variable of type MuzixService
    MuzixService muzixService;

    //Autowired constructor
    @Autowired
    public MuzixController(MuzixService muzixService) {
        this.muzixService = muzixService;
    }

    //Method to perform POST operation
    @PostMapping("muzix")
    public ResponseEntity<?> saveMuzix(@RequestBody Muzix muzix) throws MuzixAlreadyExistsException{
        muzixService.saveMuzix(muzix);
        return new ResponseEntity<String>("Successfully Created", HttpStatus.CREATED);
    }

    //Method to perform GET operation
    @GetMapping("muzixs")
    public ResponseEntity<?> getAllMuzix() throws MuzixNotFoundException {
        return new ResponseEntity<List<Muzix>>(muzixService.getAllMuzix(), HttpStatus.OK);
    }

    //Method to perform PUT operation
    @PutMapping("muzix/{trackId}")
    public ResponseEntity<?> updateMuzix(@PathVariable int trackId,@RequestBody Muzix muzix) throws MuzixNotFoundException{
        muzixService.updateMuzix(trackId,muzix.getComment());
        return new ResponseEntity<String>("Successfully updated", HttpStatus.FOUND);
    }

    //Method to perform DELETE operation
    @DeleteMapping("muzix/{trackId}")
    public ResponseEntity<?> deleteMuzix(@PathVariable int trackId) throws MuzixNotFoundException{
        muzixService.removeMuzix(trackId);
        return new ResponseEntity<String>("Successfully Deleted", HttpStatus.ACCEPTED);
    }

    //Method to trackby id
    @GetMapping("muzix/{trackId}")
    public ResponseEntity<?> getById(@PathVariable int trackId) throws MuzixNotFoundException {
        return new ResponseEntity (muzixService.trackByTrackId(trackId), HttpStatus.OK);
    }
    //Mehtod to track by name
    @GetMapping("muzixs/{trackName}")
    public ResponseEntity<?> getByName(@PathVariable String trackName) throws MuzixNotFoundException {
        return new ResponseEntity<List<Muzix>>(muzixService.trackByTrackName(trackName), HttpStatus.OK);
    }

}
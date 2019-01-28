package com.stackroute.service;

import com.stackroute.domain.Muzix;
import com.stackroute.exception.MuzixAlreadyExistsException;
import com.stackroute.exception.MuzixNotFoundException;
import com.stackroute.repository.MuzixRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MuzixServiceTest {

    private Muzix muzix;

    //Create a mock for MuzixRepository
    @Mock
    private MuzixRepository muzixRepository;

    //Inject the mocks as depedencies into MuzixServiceImpl
    @InjectMocks
    private MuzixServiceImpl muzixService;
    List<Muzix> list = null;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        muzix = new Muzix();
        muzix.setTrackId(10);
        muzix.setTrackName("Mounathabithu");
        muzix.setComment("by ashwath");
        list = new ArrayList<>();
        list.add(muzix);

    }

    @Test
    public void saveMuzixTestSuccess() throws MuzixAlreadyExistsException{
        when(muzixRepository.save((Muzix) any())).thenReturn(muzix);
        Muzix savedMuzix = muzixService.saveMuzix(muzix);
        Assert.assertEquals(muzix,savedMuzix);

        //verify here verifies that muzixRepository save method is only called once
        verify(muzixRepository,times(1)).save(muzix);
    }

    @Test(expected = MuzixAlreadyExistsException.class)
    public void saveMuzixTestFailure() throws MuzixAlreadyExistsException{
        when(muzixRepository.save((Muzix)any())).thenReturn(null);
        Muzix savedMuzix = muzixService.saveMuzix(muzix);
        Assert.assertEquals(muzix,savedMuzix);
    }

    @Test
    public void getAllMuzixTestSuccess() throws MuzixNotFoundException{
        when(muzixRepository.findAll()).thenReturn(list);
        List<Muzix> userList = muzixService.getAllMuzix();
        Assert.assertEquals(list,userList);

        //verify here verifies that muzixRepository findAll method is only called twice
        verify(muzixRepository,times(2)).findAll();
    }

    @Test(expected = MuzixNotFoundException.class)
    public void getAllMuzixFailure() throws MuzixNotFoundException{
        List<Muzix> muzixes = muzixService.getAllMuzix();
        Assert.assertNull(muzixes);
    }

    @Test
    public void updateMuzixTestSuccess() throws MuzixNotFoundException{
        when(muzixRepository.existsById(anyInt())).thenReturn(true);
        when(muzixRepository.findById(anyInt())).thenReturn(Optional.of(muzix));
        when(muzixRepository.save((Muzix)any())).thenReturn(muzix);
        Muzix muzix1 = muzixService.updateMuzix(muzix.getTrackId(),muzix.getComment());
        Assert.assertEquals(muzix1,muzix);

        //verify here verifies that muzixRepository existsById method is only called once
        verify(muzixRepository,times(1)).existsById(anyInt());

        //verify here verifies that muzixRepository findexistsById method is only called once
        verify(muzixRepository,times(1)).findById(anyInt());

        //verify here verifies that muzixRepository save method is only called once
        verify(muzixRepository,times(1)).save(muzix);
    }

    @Test(expected = MuzixNotFoundException.class)
    public void updateMuzixTestFailure() throws MuzixNotFoundException {
        when(muzixRepository.findById(11)).thenReturn(Optional.of(muzix));
        when(muzixRepository.save((Muzix) any())).thenReturn(muzix);
        Muzix savedMuzix = muzixService.updateMuzix(muzix.getTrackId(),"gfcghgv");
        System.out.println(savedMuzix);
        Assert.assertEquals(null, savedMuzix);
    }


    @Test
    public void removeMuzix() throws MuzixNotFoundException{
        muzixRepository.save(muzix);
        when(muzixRepository.existsById(any())).thenReturn(true);
        when(muzixRepository.findAll()).thenReturn(list);
        List<Muzix> list1 = muzixService.removeMuzix(muzix.getTrackId());
        Assert.assertEquals(list,list1);

        //verify here verifies that muzixRepository existsById method is only called once
        verify(muzixRepository,times(1)).existsById(anyInt());

        //verify here verifies that muzixRepository findexistsById method is only called once
        verify(muzixRepository,times(1)).findAll();
    }


    @Test(expected = MuzixNotFoundException.class)
    public void removeMuzixTestFaiure()throws MuzixNotFoundException{
        when(muzixRepository.existsById(1)).thenReturn(true);
        when(muzixRepository.findAll()).thenReturn(list);
        List<Muzix> actualoutput= muzixService.removeMuzix(muzix.getTrackId());
        Assert.assertEquals(list,actualoutput);
    }

    @Test
    public void trackByTrackId() throws MuzixNotFoundException{
        when(muzixRepository.existsById(anyInt())).thenReturn(true);
        when(muzixRepository.findById(anyInt())).thenReturn(Optional.of(muzix));
        Muzix muzix1 = muzixService.trackByTrackId(muzix.getTrackId());
        Assert.assertEquals(muzix1,muzix);

        //verify here verifies that muzixRepository existsById method is only called once
        verify(muzixRepository,times(1)).existsById(anyInt());

        //verify here verifies that muzixRepository findexistsById method is only called once
        verify(muzixRepository,times(1)).findById(anyInt());
    }

    @Test(expected = MuzixNotFoundException.class)
    public void trackByIdTestFailure()throws MuzixNotFoundException{
        when(muzixRepository.findById((101))).thenReturn(Optional.of(muzix));
        Muzix actual = muzixService.trackByTrackId(112);
        Assert.assertEquals(muzix,actual);
    }

    @Test
    public void trackByTrackNameTestSuccess()throws MuzixNotFoundException{
        when(muzixRepository.findByTrackName((anyString()))).thenReturn(list);
        List<Muzix> actual = muzixService.trackByTrackName("Mounathabithu");
        Assert.assertEquals(list,actual);

        //verify here verifies that muzixRepository findbytrackname method is only called twice
        verify(muzixRepository, times(2)).findByTrackName("Mounathabithu");
    }

    @Test(expected = MuzixNotFoundException.class)
    public void trackByTrackNameTestFailure()throws MuzixNotFoundException{
        when(muzixRepository.findByTrackName(("Jaagododharana"))).thenReturn(list);
        List<Muzix> actualOutput = muzixService.trackByTrackName("kaanadakadalige");
        Assert.assertEquals(muzix,actualOutput);
    }
}

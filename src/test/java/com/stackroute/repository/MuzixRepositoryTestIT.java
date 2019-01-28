package com.stackroute.repository;

import com.stackroute.domain.Muzix;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//JUnit will invoke the class it references to run the tests in that class instead of the runner built into JUnit.
@RunWith(SpringRunner.class)

// Used when a test focuses only on MongoDB components.
@DataMongoTest
public class MuzixRepositoryTestIT {

    @Autowired
    private MuzixRepository muzixRepository;
    private Muzix muzix;

    private List<Muzix> list;
    @Before
    public void setUp()
    {
        muzix = new Muzix();
        muzix.setTrackId(1);
        muzix.setTrackName("Kannadakadalige");
        muzix.setComment("by ashwath");
        list = new ArrayList<>();
        list.add(muzix);
    }

    @After
    public void tearDown(){
        muzixRepository.deleteAll();
    }

    @Test
    public void testSaveUser(){
        muzixRepository.save(muzix);
        Muzix fetchMuzix = muzixRepository.findById(muzix.getTrackId()).get();
        Assert.assertEquals(1,fetchMuzix.getTrackId());
    }

    @Test
    public void testSaveMuzixFailure(){
        Muzix testMuzix = new Muzix(1,"Neera bittu","Awesome");
        muzixRepository.save(muzix);
        Muzix fetchMuzix = muzixRepository.findById(muzix.getTrackId()).get();
        Assert.assertNotSame(testMuzix,muzix);
    }

    @Test
    public void  testGetAllMuzix(){
        Muzix muzix1 = new Muzix(1,"kanndadakadalige","by Ashwath");
        Muzix muzix2 = new Muzix(2,"kannadakadalige","by ashwath");
        muzixRepository.save(muzix1);
        muzixRepository.save(muzix2);
        List<Muzix> list = muzixRepository.findAll();
        Assert.assertEquals("kanndadakadalige",list.get(0).getTrackName());
    }

    @Test
    public void testDeleteMuzix(){
        Muzix muzix1 = new Muzix(1,"kanndadakadalige","by Ashwath");
        muzixRepository.save(muzix1);
        muzixRepository.delete(muzix1);
        Assert.assertEquals(Optional.empty(),muzixRepository.findById(1));
    }

    @Test
    public void testDeleteMuzixFailure(){
        Muzix muzix1 = new Muzix(1,"Yaavamohana","By pallavi");
        muzixRepository.save(muzix1);
        muzixRepository.delete(muzix1);
        Assert.assertNotEquals(muzix1,muzix);
    }

    @Test
    public void testFindById(){
        muzixRepository.save(muzix);
        Muzix fetchMuzix = muzixRepository.findById(muzix.getTrackId()).get();
        Assert.assertEquals(fetchMuzix,muzix);
    }

    @Test
    public void testFindByIdFailure(){
        Muzix muzix1 = new Muzix(1,"Yaavamohana","By pallava");
        muzixRepository.save(muzix1);
        Muzix fetchMuzix = muzixRepository.findById(muzix.getTrackId()).get();
        Assert.assertNotSame(fetchMuzix,muzix1);
    }

    @Test
    public void testFindByName(){
        muzixRepository.save(muzix);
        List<Muzix> fetchMuzix = muzixRepository.findByTrackName(muzix.getTrackName());
        Assert.assertEquals(fetchMuzix,list);
    }

    @Test
    public void testFindByNameFailure(){
        Muzix muzix1 = new Muzix(1,"Yaavamohana","By pallava");
        muzixRepository.save(muzix1);
        List<Muzix> fetchMuzix = muzixRepository.findByTrackName(muzix.getTrackName());
        Assert.assertNotEquals(fetchMuzix,list);
    }

    @Test
    public void testUpdate(){
        muzix.setComment("By balasubramanyam");
        muzixRepository.save(muzix);
        Assert.assertEquals("By balasubramanyam",muzixRepository.findById(1).get().getComment());
    }

    @Test
    public void testUpdateFailure(){
        muzix.setComment("by ashwath");
        muzixRepository.save(muzix);
        Assert.assertNotSame("by balasubramnya",muzixRepository.findById(1).get().getComment());
    }
}

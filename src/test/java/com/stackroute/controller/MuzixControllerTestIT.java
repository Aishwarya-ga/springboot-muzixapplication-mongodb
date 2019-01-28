package com.stackroute.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.domain.Muzix;
import com.stackroute.exception.MuzixAlreadyExistsException;
import com.stackroute.exception.MuzixNotFoundException;
import com.stackroute.service.MuzixService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MuzixControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    private Muzix muzix;

    @MockBean
    private MuzixService muzixService;

    @InjectMocks
    private MuzixController muzixController;

    private List<Muzix> list = null;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(muzixController).build();
        muzix = new Muzix();
        muzix.setTrackId(2);
        muzix.setTrackName("Edethumbihaduvenu");
        muzix.setComment("by balasubramanyam");
        list = new ArrayList<>();
        list.add(muzix);
    }

    @Test
    public void saveMusic() throws Exception{
        when(muzixService.saveMuzix(any())).thenReturn(muzix);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/muzix")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(muzix)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test(expected = MuzixAlreadyExistsException.class)
    public void saveUserFailure() throws Exception {
        when(muzixService.saveMuzix(any())).thenThrow(MuzixAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/muzix")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(muzix)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllMuzix() throws Exception{
        when(muzixService.getAllMuzix()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/muzixs")
        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(muzix)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateMuzix() throws Exception{
        when(muzixService.updateMuzix(muzix.getTrackId(),muzix.getComment())).thenReturn(muzix);
        when(muzixService.saveMuzix(any())).thenReturn(muzix);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/muzix/2")
               .contentType(MediaType.APPLICATION_JSON).content(asJsonString(muzix)))
               .andExpect(MockMvcResultMatchers.status().isFound())
               .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void deleteMuzix() throws Exception{
        when(muzixService.removeMuzix(muzix.getTrackId())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/muzix/1")
               .contentType(MediaType.APPLICATION_JSON).content(asJsonString(muzix)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getById() throws Exception{
        muzixService.saveMuzix(muzix);
        when(muzixService.trackByTrackId(anyInt())).thenReturn(muzix);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/muzix/2")
               .contentType(MediaType.APPLICATION_JSON).content(asJsonString(muzix)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getByTrackIdFailure() throws Exception {
        when(muzixService.trackByTrackId(anyInt())).thenThrow(MuzixNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/muzix/123")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(muzix)))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getByTrackName() throws Exception {
        when(muzixService.trackByTrackName(anyString())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/muzixs/Edethumbihaduvenu")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(muzix)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getByTrackNameFailure() throws Exception {
        when(muzixService.trackByTrackName(anyString())).thenThrow(MuzixNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/muzixs/abcd")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(muzix)))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed())
                .andDo(MockMvcResultHandlers.print());
    }

    //method to convert a muzix object to Json String
    private static String asJsonString(final Object obj)
    {
        try{
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}

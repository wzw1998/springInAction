package com.zexing.spittr.web;

import com.zexing.spittr.bean.Spitter;
import com.zexing.spittr.repository.SpitterRepository;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpitterControllerTest {


    @Test
    public void showRegistrationForm() throws Exception {

        SpitterRepository mockSpitterRepository = mock(SpitterRepository.class);

        SpitterController spitterController = new SpitterController(mockSpitterRepository);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(spitterController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/spitter/register"))
                .andExpect(MockMvcResultMatchers.view().name("registerForm"));
    }

    @Test
    public void  processRegistration() throws Exception {

        Spitter unSavedSpitter = new Spitter("Joson","12345","zhang","jacoo","12345@168.com");
        Spitter savedSpitter = new Spitter(10L,"Joson","12345","zhang","jacoo","12345@168.com");

        SpitterRepository mockSpitterRepository = mock(SpitterRepository.class);
        when(mockSpitterRepository.save(unSavedSpitter)).thenReturn(savedSpitter);

        SpitterController spitterController = new SpitterController(mockSpitterRepository);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(spitterController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/spitter/register")
                .param("username","Joson")
                .param("password","12345")
                .param("firstName","zhang")
                .param("lastName","jacoo")
                .param("email","12345@168.com"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/spitter/Joson"));

    }

    @Test
    public void showSpitterProfile() throws Exception {
        Spitter savedSpitter = new Spitter(10L,"Joson","12345","zhang","jacoo","12345@168.com");

        SpitterRepository mockSpitterRepository = mock(SpitterRepository.class);
        when(mockSpitterRepository.findSpitterByUserName(savedSpitter.getUsername())).thenReturn(savedSpitter);

        SpitterController spitterController = new SpitterController(mockSpitterRepository);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(spitterController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/spitter/Joson"))
                .andExpect(MockMvcResultMatchers.view().name("profile"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("spitter"));
    }
}
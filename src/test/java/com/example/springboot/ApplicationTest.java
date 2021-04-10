package com.example.springboot;
        import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=stringToDelete")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/delete?post_text=stringToDelete"))
                .andExpect(content().string(containsString("has been deleted")));
        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
                .andExpect(content().string(not(containsString("stringToDelete"))));
    }

    @Test
    void testSensitive() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=Case")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=case")).andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("case")));
        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("Case")));

        mockMvc.perform(MockMvcRequestBuilders.post("/delete?post_text=case").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("has been deleted")));

        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
                .andExpect(content().string(not(containsString("case"))));
        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("Case")));
    }
}
package dev.bura.jetty.df.web.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author a.bloshchetsov
 */
public class SimpleControllerTests {

    @SpringBootTest
    @AutoConfigureMockMvc
    static class HelloTests {

        @Autowired
        private MockMvc mvc;

        @Test
        public void testPause() throws Exception {
            mvc.perform(MockMvcRequestBuilders.get("/hello"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.jsonPath("result", Matchers.equalTo(0)))
                    .andExpect(MockMvcResultMatchers.jsonPath("message", Matchers.equalTo("Hello!")));
        }

    }

    @SpringBootTest(properties = "jetty.dosFilter.maxRequestMs=1000")
    @AutoConfigureMockMvc
    static class TimeoutHappenedTests {

        @Autowired
        private MockMvc mvc;

        @Test
        public void testPause() throws Exception {
            mvc.perform(MockMvcRequestBuilders.get("/hello-with-pause-2000"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.jsonPath("result", Matchers.equalTo(99)))
                    .andExpect(MockMvcResultMatchers.jsonPath("message", Matchers.equalTo("Internal processing timeout")));
        }

    }

    @SpringBootTest(properties = "jetty.dosFilter.maxRequestMs=3000")
    @AutoConfigureMockMvc
    static class TimeoutNotHappenedTests {

        @Autowired
        private MockMvc mvc;

        @Test
        public void testPause() throws Exception {
            mvc.perform(MockMvcRequestBuilders.get("/hello-with-pause-2000"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.jsonPath("result", Matchers.equalTo(0)))
                    .andExpect(MockMvcResultMatchers.jsonPath("message", Matchers.equalTo("Hello!")));
        }

    }

    @SpringBootTest(properties = "jetty.dosFilter.maxRequestMs=2000")
    @AutoConfigureMockMvc
    static class RaceTests {

        @Autowired
        private MockMvc mvc;

        @Test
        public void testPause() throws Exception {
            mvc.perform(MockMvcRequestBuilders.get("/hello-with-pause-2000"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.jsonPath("result", Matchers.equalTo(99)))
                    .andExpect(MockMvcResultMatchers.jsonPath("message", Matchers.equalTo("Internal processing timeout")));
        }

    }

}

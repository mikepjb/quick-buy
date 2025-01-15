package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuickBuyTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void shouldReturnUser() throws Exception {
        mockMvc.perform(get("/api/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))   // This checks for id:1
                .andExpect(jsonPath("$.name").value("John Doe"))  // This checks for name:"John Doe"
                .andDo(print());
    }

    // Unit test
//    @Test public void appHasAGreeting() {
//        App classUnderTest = new App(resourceLoader);
//        assertNotNull("app should have a greeting", classUnderTest.testFunction());
//    }
}

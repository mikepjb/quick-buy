package org.example;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

@SpringBootTest
public class AppTest {
    @Autowired
    private ResourceLoader resourceLoader;

    // Unit test
    @Test public void appHasAGreeting() {
        App classUnderTest = new App(resourceLoader);
        assertNotNull("app should have a greeting", classUnderTest.testFunction());
    }
}

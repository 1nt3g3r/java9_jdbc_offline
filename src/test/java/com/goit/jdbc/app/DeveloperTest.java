package com.goit.jdbc.app;

import com.goit.jdbc.app.dao.Developer;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

public class DeveloperTest {
    @Test
    public void testEquals() {
        assertEquals(new Developer(), new Developer());
    }
}

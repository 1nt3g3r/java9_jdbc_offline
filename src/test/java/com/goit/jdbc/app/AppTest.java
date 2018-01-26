package com.goit.jdbc.app;

import com.goit.jdbc.app.dao.Developer;
import com.goit.jdbc.app.dao.JdbcDeveloperDao;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void testSum() {
        Calc calc = new Calc();

        int actual = calc.sum(3, 5);
        int expected = 8;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSub() {
        Calc calc = new Calc();
        Assert.assertEquals(3, calc.sub(8, 5));
    }

    @Test
    public void testAdd() {
        Developer expected = new Developer();
        expected.setFirstName("Misha");
        expected.setLastName("Ivanov");
        expected.setSpecialty("Java");

        JdbcDeveloperDao developerDao = new JdbcDeveloperDao();
        developerDao.add(expected);

        long devId = expected.getId();

        Developer actual = developerDao.getById(devId);

        Assert.assertEquals(expected, actual);
    }
}

package com.goit.jdbc.app;

import com.goit.jdbc.app.dao.Developer;
import com.goit.jdbc.app.dao.JdbcDeveloperDao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JdbcTest {

    private JdbcDeveloperDao developerDao;

    @Before
    public void init(){
        developerDao = new JdbcDeveloperDao();
    }

    @Test
    public void testGetSave(){
        Developer expected = randomDeveloper();

        developerDao.add(expected);
        Developer actual = developerDao.getById(expected.getId());

        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testUpdate(){
        Developer expected = randomDeveloper();
        developerDao.add(expected);

        expected.setFirstName("123");
        expected.setLastName("123");
        expected.setSpecialty("123");
        developerDao.update(expected);

        Developer actual = developerDao.getById(expected.getId());
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testDelete(){
        Developer expected = randomDeveloper();
        developerDao.add(expected);
        developerDao.delete(expected);

        Assert.assertNull(developerDao.getById(expected.getId()));
    }

    @Test
    public void testTruncateTable() {
        Developer developer = randomDeveloper();
        developerDao.add(developer);
        developerDao.clear();
        int size = developerDao.getAll().size();
        Assert.assertEquals(0, size);
    }

    @Test
    public void testList() {
        developerDao.clear();
        List<Developer> expected = developerGenerator(10);

        developerDao.addDevelopers(expected);
        List<Developer> actual = developerDao.getAll();

        printList(actual);

        Assert.assertTrue(expected.containsAll(actual));
        Assert.assertTrue(actual.containsAll(expected));
    }

    private void printList(List<Developer> developers) {
        for(Developer d: developers) {
            System.out.println(d);
        }
    }

    public static List<Developer> developerGenerator(int count) {
        ArrayList<Developer> developerList = new ArrayList<Developer>();
        for (int i = 0; i < count; i++) {
            developerList.add(randomDeveloper());
        }
        return developerList;
    }
    public static Developer randomDeveloper() {
        Random r = new Random();
        Developer developer = new Developer();
        developer.setFirstName(Float.toString(r.nextFloat()));
        developer.setLastName(Float.toString(r.nextFloat()));
        developer.setSpecialty(Float.toString(r.nextFloat()));
        return developer;
    }
}

package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class UserDaoImplTest {

    @Autowired
    UserDao userDao;


    @Test
    public void deleteUser() {
    }

    @Test
    public void selectUser() {
    }

    
    @Test
    public void insertUser() {
    }

    @Test
    public void updateUser() throws Exception{
        Calendar cal=Calendar.getInstance();
        cal.clear();
        cal.set(2021,1,1);

        userDao.deleteUser("asdf");
        User user = new User("asdf", "1234", "asd", "dd@dd", new Date(cal.getTimeInMillis()), "face", new Date(cal.getTimeInMillis()));
        int rowCnt = userDao.insertUser(user);
        assertTrue(rowCnt == 1);
        user.setPwd("413");
        user.setEmail("sdfas@ddsa");
        rowCnt = userDao.updateUser(user);
        assertTrue(rowCnt == 1);

        User user2 = userDao.selectUser(user.getId());
        System.out.println("user = " + user);
        System.out.println("user2 = " + user2);
        assertTrue(user.equals(user2));

    }


}
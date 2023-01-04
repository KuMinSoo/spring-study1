package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class A1DaoTest {
    @Autowired
    A1Dao a1Dao;
    @Autowired
    DataSourceTransactionManager tm;


    @Test
    public void insertTest() throws Exception{
      //  PlatformTransactionManager tm=new DataSourceTransactionManager(ds);
        TransactionStatus status=tm.getTransaction(new DefaultTransactionDefinition());

        try {
            a1Dao.deleteAll();
            a1Dao.insert(1,100);
            a1Dao.insert(2,200);
            tm.commit(status);
        } catch (Exception e) {
           e.printStackTrace();
           tm.rollback(status);
        } finally {
        }
    }
}
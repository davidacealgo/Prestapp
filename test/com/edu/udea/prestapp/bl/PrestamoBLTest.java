package com.edu.udea.prestapp.bl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional 
@ContextConfiguration(locations="classpath:SpringBeanDefinition.xml")
public class PrestamoBLTest {
	@Autowired
	PrestamoBL prestamo;
	@Test
	public void test() {
		try {
			prestamo.realizarPrestamo("SONORKS", 1);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}

package com.edu.udea.prestapp.bl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.bl.ObjetoBL;
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional 
@ContextConfiguration(locations="classpath:SpringBeanDefinition.xml")
public class ObjetoBLTest {
	@Autowired
	ObjetoBL objeto = null;
	@Test
	public void test() {
		try {
			objeto.mostrarObjetos();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}

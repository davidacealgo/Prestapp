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
public class UsuarioBLTest {

	@Autowired
	UsuarioBL usuario;
	@Test
	public void test() {
		try {
			usuario.doLogin("SONORKS", "CONTRASENA");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}

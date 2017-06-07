package com.edu.udea.prestapp.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations="classpath:SpringBeanDefinition.xml")
public class UsuarioDaoTest {
	
		@Autowired
		InterfaceUsuarioDao usuarioDao;
		final Logger log = Logger.getLogger(ObjetoDaoTest.class.getName());
		
		@Test
		public void testGetUsuario() {
			Usuario user = null;
			try{
				log.info("Iniciando prueba de obtener un usuario de la BD");
				user = usuarioDao.getUsuario("elver");
				assertTrue(user!=null);
				System.out.println(user.getNombres()+"-"+user.getApellidos()+"-"+user.getCorreo());
			}catch(ExceptionController e){
				e.printStackTrace();
				fail(e.getMessage());
			}
		}

		@Test
		public void testRestablecerContrasena() {
			Usuario user = null;
			try{
				log.info("Iniciando prueba de restablecer la contrasena de un usuario de la BD");
				usuarioDao.restablecerContrasena("elver", "elver@udea.edu.co", "ingweb", "elverpass");
				user = usuarioDao.getUsuario("elver");
				assertTrue(user!=null);
				System.out.println(user.getNombres()+"-"+user.getApellidos()+"-"+user.getCorreo());
			}catch(ExceptionController e){
				e.printStackTrace();
				fail(e.getMessage());
			}
		}

		@Test
		public void testRegistrarUsuario() {
			
		}

		@Test
		public void testModificarUsuario() {
			
		}

		@Test
		public void testEliminarUsuario() {
			
		}
		
		@Test
		public void testGetUsuarios() {
			log.info("Iniciando prueba de obtener todos los usuarios de la BD");
			List<Usuario> lista = null;
			try {
				lista = usuarioDao.getUsuarios(); //llamado del metodo
				assertTrue(lista.size()>0);  //si se obtienen datos
			}catch(ExceptionController e) {
				e.printStackTrace(); //manda todo el error a consola
				fail(e.getMessage());  //mensaje personalizado
			}
		}
}
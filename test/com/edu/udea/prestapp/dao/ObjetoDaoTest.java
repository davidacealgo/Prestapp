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
import com.edu.udea.prestapp.exception.ExceptionController;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations="classpath:SpringBeanDefinition.xml")
public class ObjetoDaoTest {
	@Autowired
	InterfaceObjetoDao objetoDao;
	final Logger log = Logger.getLogger(ObjetoDaoTest.class.getName());
	@Test
	public void testGetObjetos() {
		log.info("Iniciando prueba de obtener todos los Objetos de la BD");
		List<Objeto> lista = null;
		try {
			//ciudadDao = new CiudadDaoSpring(); //se construye el objeto con la implementacion de la interfaz
			lista = objetoDao.getObjetos(); //llamado del metodo
			assertTrue(lista.size()>0);  //si se obtienen datos
		}catch(ExceptionController e) {
			e.printStackTrace(); //manda todo el error a consola
			fail(e.getMessage());  //mensaje personalizado
		}
	}

	@Test
	public void testGetObjetosDisponibles() {
		log.info("Iniciando prueba de obtener todos los Objetos disponibles de la BD");
		List<Objeto> lista = null;
		try {
			//ciudadDao = new CiudadDaoSpring(); //se construye el objeto con la implementacion de la interfaz
			lista = objetoDao.getObjetosDisponibles(); //llamado del metodo
			assertTrue(lista.size()>0);  //si se obtienen datos
		}catch(ExceptionController e) {
			e.printStackTrace(); //manda todo el error a consola
			fail(e.getMessage());  //mensaje personalizado
		}
	}

	@Test
	public void testModificarDisponibilidad() {
		List<Objeto> lista = null; //lista donde se guardará el resultado del query
		try {
			log.info("Iniciando prueba de modificar disponibilidad de los objetos en la BD");
			objetoDao.modificarDisponibilidad(1,1); //llamado del metodo quemado en la clase por temas de facilidad a la hora de estudiar el framework
			lista=objetoDao.getObjetos();
			assertTrue(lista.size()>0);
		}catch(ExceptionController e) {
			e.printStackTrace(); //manda todo el error a consola
			fail(e.getMessage());  //mensaje personalizado
		}
	}

	@Test
	public void testEliminarObjeto() {
		List<Objeto> lista = null; //lista donde se guardará el resultado del query
		try {
			log.info("Iniciando prueba de eliminar objeto en la BD");
			objetoDao.eliminarObjeto(2); //llamado del metodo quemado en la clase por temas de facilidad a la hora de estudiar el framework
			lista=objetoDao.getObjetos();
			assertTrue(lista.size()>0);
		}catch(ExceptionController e) {
			e.printStackTrace(); //manda todo el error a consola
			fail(e.getMessage());  //mensaje personalizado
		}
	}

	@Test
	public void testGetObjeto() {
		Objeto obj = null; //Objeto en el cual se imprimira el resultado
		try{
			log.info("Iniciando prueba de obtener un objeto de la BD");
			obj = objetoDao.getObjeto(1);//llamado del metodo quemado en la clase por temas de facilidad a la hora de estudiar el framework
			assertTrue(obj!=null);//Se verifica que no sea null
			System.out.println(obj.getNombre()+"-"+obj.getEstado());//Se imprime en consola
		}catch(ExceptionController e){
			e.printStackTrace();//Manda todo el error a consola
			fail(e.getMessage());//mensaje personalizado
		}
	}

}

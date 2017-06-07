package com.edu.udea.prestapp.dao;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.exception.ExceptionController;

public class ObjetoDaoImp implements InterfaceObjetoDao {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

	public List<Objeto> getObjetos() throws ExceptionController{
		List<Objeto> lista = new ArrayList<Objeto>();//Lista en donde se guardaran todos los objetos de la bd
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Objeto.class); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando objetos",e);
		}
		
		return lista;
	}
	public List<Objeto> getObjetosDisponibles() throws ExceptionController{
		List<Objeto> lista = new ArrayList<Objeto>(); //Lista en donde se guardaran los objetos disponibles
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Objeto.class);//retorna la busqueda en la tabla seleccionada
			criteria.add( Restrictions.eq("disponibilidad", new Integer(1))); //Se le añade la restriccion de que la disponibilidad sea 0, es decir, esté disponible
			lista = criteria.list();
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando disponibilidad",e);
		}
		
		return lista;
	}
	public List<Objeto> getObjetosNoDisponibles() throws ExceptionController{
		List<Objeto> lista = new ArrayList<Objeto>(); //Lista en donde se guardaran los objetos disponibles
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Objeto.class);//retorna la busqueda en la tabla seleccionada
			criteria.add( Restrictions.eq("disponibilidad", new Integer(2) ) ); //Se le añade la restriccion de que la disponibilidad sea 0, es decir, esté disponible
			lista = criteria.list();
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando disponibilidad",e);
		}
		
		return lista;
	}
	
	public String modificarDisponibilidad (int id, int tipoCambio) throws ExceptionController{
		Session session = null;
		Objeto objeto = getObjeto(id);//Objeto a modificar
		if(tipoCambio == 1) {//Si el tipoCambio es 1 el objeto será disponible, si es 0 no estará disponible
			objeto.setDisponibilidad(1);
		}else if (tipoCambio == 2) {
			objeto.setDisponibilidad(0);
		}else if (tipoCambio == 3) {
			objeto.setReservado(1);
		}else if (tipoCambio == 4) {
			objeto.setReservado(0);
		}
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			session.update(objeto);//Actualiza el objeto en la bd
			return "Objeto actualizado";
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando disponibilidad",e);
		}
	}
	
	public String eliminarObjeto(int idObjeto) throws ExceptionController{
		Session session = null;
		Objeto objeto = getObjeto(idObjeto);//Objeto a eliminar
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			session.delete(objeto);//Se elimina el objeto de la bd
			return "Objeto eliminado";
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando disponibilidad",e);
		}
	}
	public Objeto getObjeto(int id) throws ExceptionController{
		Session session = null;
		Objeto objeto;
		try {
			session = sessionFactory.getCurrentSession();//para conectarse con el BEAN definido en SpringConf.xml
			objeto = (Objeto) session.get(Objeto.class,id);
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando objeto con id "+id,e);
		}
		return objeto;
	}

}

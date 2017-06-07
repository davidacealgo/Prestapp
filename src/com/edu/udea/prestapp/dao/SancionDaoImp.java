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
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.udea.prestapp.dto.Sancion;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.exception.ExceptionController;

public class SancionDaoImp {
	@Autowired
	private UsuarioDaoImp user;
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void sancionarUsuario(String usuario, String tipoSancion, Date inicioSancion, Date finSancion) throws ExceptionController{
		Date date = new Date(2017,1,1);//Fecha para validaciones
		
		if(tipoSancion.isEmpty() || tipoSancion == null) {//validando que se reciba el tipo de sancion
			throw new ExceptionController("El tipo de sanción no puede estar vacio");
			
		}
		if(inicioSancion.after(date) || inicioSancion == null) {//validando que la fecha de sancion sea de este año
			throw new ExceptionController("La fecha inicial no puede estar vacia");
		}
		
		if(finSancion == null || finSancion.before(inicioSancion)) {//validando que la fecha sea despues de la fecha de inicio de la sancion
			throw new ExceptionController("La fecha final no puede estar vacio");
		}
		Usuario usuarito;
		usuarito = user.getUsuario(usuario);
		System.out.println(usuarito);
		if(usuarito!=null){//Si el usuario existe->
			Usuario sancionado = user.getUsuario(usuario); //Variable para guardar el usuario a sancionar
			Session session = null;
			System.out.println(sancionado+"789");
			try{
				session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
				session.save(sancionado);
			}catch(HibernateException e) {
				throw new ExceptionController("Error al sancionar el usuario");
			}finally {
				//session.close();
			}
		}else{
			throw new ExceptionController("El usuario no existe");
		}
	}
	public void eliminarSancion(String usuario) throws ExceptionController{
		if(usuario.isEmpty() || usuario == null) {//validando que se reciba el usuario
			throw new ExceptionController("El usuario no puede estar vacio");
		}
		if(user.getUsuario(usuario)!=null){//Si el usuario existe ->
			Usuario sancionado = user.getUsuario(usuario);//Variable para identificar el usuario sancionado
			Session session = null;
			try{
				session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
				session.delete(sancionado);
			}catch(HibernateException e){
				throw new ExceptionController("Error consultando usuario");
			}finally{
				//session.close();
			}
		}else{
			throw new ExceptionController("Credenciales incorrectas");
		}
	}
	
	public List<Sancion> getSanciones() throws ExceptionController{
		List<Sancion> lista = new ArrayList<Sancion>();//Lista donde se guardarán las sanciones de la bd
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Sancion.class); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando sanciones",e);
		}finally {
			//session.close();
		}
		return lista;
		
	}
}

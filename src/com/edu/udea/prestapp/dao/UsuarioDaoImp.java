package com.edu.udea.prestapp.dao;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

//Implementación del usuario 
public class UsuarioDaoImp {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}	
	//Método que devuelve el usuario segun su nombre de usuario
	public Usuario getUsuario(String usuario) throws ExceptionController {
		Session session = null;
		Usuario user = new Usuario();
		try{
			session = sessionFactory.getCurrentSession();//Se obtiene la sesion
			Criteria criteria = session.createCriteria(Usuario.class);
			criteria.add(Restrictions.eq("usuario", usuario));//Se agrega la condicion con la que se hace la consulta
			user = (Usuario)criteria.uniqueResult();//Unique porque sé y estoy seguro que me va a arrojar solo 1 valor
			//uniqueResult retorna un objeto tipo "object"
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando usuario", e);
		}finally{
			//session.close();
		}
		return user;
	}

	//Método con el que él usuario puede restablecer la contraseña 
	public void restablecerContrasena(String contrasenaNueva, Usuario user) throws ExceptionController{
		user.setContrasena(contrasenaNueva);
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			session.update(user);
		}catch (HibernateException e) {
			throw new ExceptionController("Error cambiando contraseña",e);
		}finally {
			//session.close();
		}
	}
	
	//Método para registrar un usuario nuevo en la BD
	public void registrarUsuario(int id, String nombres, String apellidos, String correo, String usuario, String contrasena, String tipoId, String telefono, int admin) throws ExceptionController{
		if(nombres.isEmpty() || nombres == null) {
			throw new ExceptionController("El nombre no puede estar vacio");
		}
		if(apellidos.isEmpty() || apellidos == null) {
			throw new ExceptionController("El apellido no puede estar vacio");
		}
		if(correo.isEmpty() || correo == null) {
			throw new ExceptionController("El email no puede estar vacio");
		}
		if(usuario.isEmpty() || usuario == null) {
			throw new ExceptionController("El usuario no puede estar vacio");
		}
		if(contrasena.isEmpty() || contrasena == null) {
			throw new ExceptionController("La contrasena no puede estar vacio");
		}
		if(tipoId.isEmpty() || tipoId == null) {
			throw new ExceptionController("Elija el tipo de identificación");
		}
		if(telefono.isEmpty() || telefono == null) {
			throw new ExceptionController("El telefono no puede estar vacio");
		}
		if(getUsuario(usuario)!=null){
			if(getUsuario(usuario).getId()==id || getUsuario(usuario).getCorreo()==correo){
				throw new ExceptionController("El usuario ya existe");
			}
		}else{
			Usuario user = new Usuario();
			user.setId(id);
			user.setNombres(nombres);
			user.setApellidos(apellidos);
			user.setCorreo(correo);
			user.setUsuario(usuario);
			user.setContrasena(contrasena);
			user.setTipoId(tipoId);
			user.setTelefono(telefono);
			user.setAdmin(admin);
			Session session = null;
			try{
				session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
				session.save(user);
			}catch (HibernateException e) {
				throw new ExceptionController("Error guardando usuario",e);
			}finally {
				//session.close();
			}
		}
	}
	
	//Método para modificar un usuario existente
	public void modificarUsuario(int id, String usuario, String nombres, String apellidos, String telefono, String correo) throws ExceptionController{
		if(nombres.isEmpty() || nombres == null) {
			throw new ExceptionController("El nombre no puede estar vacio");
		}
		if(apellidos.isEmpty() || apellidos == null) {
			throw new ExceptionController("El apellido no puede estar vacio");
		}
		if(usuario.isEmpty() || usuario == null) {
			throw new ExceptionController("El usuario no puede estar vacio");
		}
		if(correo.isEmpty() || correo == null) {
			throw new ExceptionController("El email no puede estar vacio");
		}
		if(telefono.isEmpty() || telefono == null) {
			throw new ExceptionController("El telefono no puede estar vacio");
		}
		Usuario user = getUsuario(usuario); //se obtiene el usuario por medio del getUsuario
		if(user.getId()==id && user.getCorreo()==correo){	//Validando que el correo y el id sean los del usuario
			user.setNombres(nombres);
			user.setApellidos(apellidos);
			user.setTelefono(telefono);
			user.setCorreo(correo);
			Session session = null;
			try{
				session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
				session.update(user);
			}catch (HibernateException e) {
				throw new ExceptionController("Error modificando usuario",e);
			}finally{
				//session.close();
			}
		}else{
			throw new ExceptionController("Credenciales incorrectas");
		}
		
	}
	
	//Método para eliminar un usuario existente con parametros de id y nombre de usuario
	public String eliminarUsuario(int id, String usuario) throws ExceptionController{
		if(usuario.isEmpty() || usuario == null) {
			throw new ExceptionController("El usuario no puede estar vacio");
		}
		Usuario user = getUsuario(usuario); //se obtiene el usuario por medio del getUsuario
		Session session = null;
		if(user.getId()==id){
			try{
				session = sessionFactory.getCurrentSession();//Se obtiene la sesion
				session.delete(usuario);
				return "Usuario eliminado";
			}catch(HibernateException e){
				throw new ExceptionController("Error consultando usuario");
			}finally{
				//session.close();
			}
		}else{
			throw new ExceptionController("Credenciales incorrectas");
		}
		
	}
	
	//Método que devuelve una lista de usuarios
	public List<Usuario> getUsuarios() throws ExceptionController{
		List<Usuario> lista = new ArrayList<Usuario>();
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Usuario.class); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando objetos",e);
		}finally {
			//session.close();
		}
		return lista;
	}
}

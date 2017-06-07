package com.edu.udea.prestapp.bl;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.exception.ExceptionController;
import com.edu.udea.prestapp.dao.PrestamoDaoImp;
import com.edu.udea.prestapp.dao.ReservaDaoImp;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Prestamo;
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Usuario;

@Transactional
public class UsuarioBL {
	final Logger log = Logger.getLogger(UsuarioBL.class.getName());
	@Autowired
	private UsuarioDaoImp usuarioDaoImp;
	@Autowired
	private ReservaDaoImp reservaDaoImp;
	@Autowired
	private PrestamoDaoImp prestamoDaoImp;
	public UsuarioDaoImp getUsuarioDaoImp() {
		return usuarioDaoImp;
	}

	public void setUsuarioDaoImp(UsuarioDaoImp usuarioDaoImp) {
		this.usuarioDaoImp = usuarioDaoImp;
	}
	
	public String doLogin(String user, String password) throws ExceptionController {
		log.info("Iniciando metodo logear usuario");
		if(user == null || user.isEmpty()) { //validando que se reciba un usuario
			throw new ExceptionController("El usuario no puede estar vacía");
		}
		if( password == null || password.isEmpty()) { //validando que se reciba una contraseña
			throw new ExceptionController("La contraseña no puede estar vacía");
		}
		for(int i = 0 ; i < user.length(); i++) {
			char c = user.charAt(i);
			if(!Character.isLetterOrDigit(c)) {
				throw new ExceptionController("El usuario contiene caracteres inválidos");
			}
		}
		for(int i = 0 ; i < password.length(); i++) {
			char c = password.charAt(i);
			if(!Character.isLetterOrDigit(c)) {
				throw new ExceptionController("La contraseña contiene caracteres inválidos");
			}
		}
		Usuario usuario = usuarioDaoImp.getUsuario(user); //se obtiene el usuario por medio del obtener(login)
		if(usuario == null) {
			throw new ExceptionController("Usuario o contraseña incorrecta");
		}
		if(!usuario.getContrasena().equals(password)) {
			throw new ExceptionController("Credenciales incorrectas");
		}
		if(usuario.getAdmin()== 1) {
			return "admin";
		}
		return "usuario";
	}
	public void restablecerContrasena(String usuario, String correo, String contrasenaActual, String contrasenaNueva, String usr) throws ExceptionController{
		Usuario username = usuarioDaoImp.getUsuario(usr);
		log.info("Iniciando metodo restablecer contraseña");
		if(usuario.isEmpty() || usuario == null) { //validando que se reciba un usuario
			throw new ExceptionController("El usuario no puede estar vacía");
		}
		if(contrasenaActual.isEmpty() || contrasenaActual == null) { //validando que se reciba una contraseña
			throw new ExceptionController("Digite la contraseña actual");
		}
		if(contrasenaNueva.isEmpty() || contrasenaNueva == null) { //validando que se reciba una contraseña
			throw new ExceptionController("Digite la nueva contraseña");
		}
		Usuario user = usuarioDaoImp.getUsuario(usuario); //se obtiene el usuario por medio del getUsuario
		if(user == null) {
			throw new ExceptionController("Usuario o contraseña incorrecta");
		}
		if(user.getContrasena().equals(contrasenaActual) && user.getCorreo().equals(correo)) {
			usuarioDaoImp.restablecerContrasena(contrasenaNueva, user);
		}else{
			throw new ExceptionController("Credenciales incorrectas");
		}
	}
	public void registrarUsuario(String usuario, 
			String contrasena, 
			String contrasena2, 
			String tipoId, int id, String nombres, String apellidos, String correo, String telefono,int admin, String usrAdmin) throws ExceptionController {
		Usuario usuarioAdmin = usuarioDaoImp.getUsuario(usrAdmin);
		log.info("Iniciando metodo registrar usuario");
		
		if(contrasena.equals(contrasena2) == false ) {
			
			return;
		}
		for(int i = 0 ; i < usuario.length(); i++) {
			char c = usuario.charAt(i);
			if(!Character.isLetterOrDigit(c)) {
				
				throw new ExceptionController("El usuario contiene caracteres inválidos");
			}
			
		}
		for(int i = 0 ; i < contrasena.length(); i++) {
			char c = contrasena.charAt(i);
			if(!Character.isLetterOrDigit(c)) {
				
				throw new ExceptionController("la contraseña contiene caracteres inválidos");
				
			}
			
		}
		for(int i = 0 ; i < tipoId.length(); i++) {
			char c = tipoId.charAt(i);
			if(!Character.isLetter(c)) {
				
				throw new ExceptionController("El usuario contiene caracteres inválidos");
			}
			
		}
		List<Usuario> listaUsuarios = usuarioDaoImp.getUsuarios();
		if(listaUsuarios.isEmpty() == false){
		for (int i = 0 ; i < listaUsuarios.size(); i ++) {
			if(listaUsuarios.get(i).getId()== id) {
				System.out.println("5");
				throw new ExceptionController("El id ya se encuentra registrado");
			}
			
		}
		}
		
		
		for(int i = 0 ; i < nombres.length(); i++) {
			char c = nombres.charAt(i);
			if(!Character.isLetter(c)) {
				throw new ExceptionController("El nombre contiene caracteres inválidos");
			}
			
		}
		
		for(int i = 0 ; i < apellidos.length(); i++) {
			char c = apellidos.charAt(i);
			if(!Character.isLetter(c)) {
				throw new ExceptionController("El apellido contiene caracteres inválidos");
			}
			
		}
		for(int i = 0 ; i < telefono.length(); i++) {
			char c = telefono.charAt(i);
			if(!Character.isDigit(c)) {
				throw new ExceptionController("El telefono contiene caracteres inválidos");
			}
			
		}
		if((usrAdmin)!=null) {
			usuarioDaoImp.registrarUsuario(id, nombres, apellidos, correo, usuario, contrasena, tipoId, telefono, admin);
			
		}
		else {
			throw new ExceptionController("El usuario que intenta registrar no es administrador");
		}
		
	}
	public void modificarDatosDeUsuario(String usuario, String contraseña, String nombres, String apellidos, String telefono, String correo, String usrManipulador) throws ExceptionController {
		Usuario usuarioManipulador = usuarioDaoImp.getUsuario(usrManipulador);
		log.info("Iniciando metodo modificar usuario");
		for(int i = 0 ; i < usuario.length(); i++) {
			char c = usuario.charAt(i);
			if(!Character.isLetterOrDigit(c)) {
				throw new ExceptionController("El usuario contiene caracteres inválidos");
			}
		}
		for(int i = 0 ; i < contraseña.length(); i++) {
			char c = contraseña.charAt(i);
			if(!Character.isLetterOrDigit(c)) {
				throw new ExceptionController("la contraseña contiene caracteres inválidos");
			}
		}
		List<Usuario> listaUsuarios = usuarioDaoImp.getUsuarios();
		boolean encontrado=false;
		for (int i = 0 ; i < listaUsuarios.size(); i++) {
			System.out.println(listaUsuarios.get(i).getCorreo()+"   esta es la lista");
			if((listaUsuarios.get(i).getUsuario().equals(usuario))) {
				encontrado=true;
			}
			
		}
		if(encontrado==false){
			throw new ExceptionController("Error al iniciar sesión");
		}
		for(int i = 0 ; i < nombres.length(); i++) {
			char c = nombres.charAt(i);
			if(!Character.isLetter(c)) {
				throw new ExceptionController("El nombre contiene caracteres inválidos");
			}
		}
		for(int i = 0 ; i < apellidos.length(); i++) {
			char c = apellidos.charAt(i);
			if(!Character.isLetter(c)) {
				throw new ExceptionController("El apellido contiene caracteres inválidos");
			}
		}
		for(int i = 0 ; i < telefono.length(); i++) {
			char c = telefono.charAt(i);
			if(!Character.isDigit(c)) {
				throw new ExceptionController("El telefono contiene caracteres inválidos");
			}
		}
		if(usuarioManipulador.getAdmin()==1) {
			if(usuarioManipulador.getContrasena().equals(contraseña)) {
				usuarioDaoImp.modificarUsuario(usuarioManipulador.getId(), usuario, nombres, apellidos, telefono, correo);
			}
		}
		else {
			throw new ExceptionController("El usuario que intenta registrar no es administrador");
		}
		
	}
	public void eliminarUsuario(String usuario, String adm) throws ExceptionController {
		Usuario admin = usuarioDaoImp.getUsuario(adm);
		log.info("Iniciando metodo eliminar usuario");
		Usuario user = null;
		try {
			user = usuarioDaoImp.getUsuario(usuario);
		}catch(Exception e) {
			throw new ExceptionController("Error obteniendo usuario");
		}
		List<Reserva> lista;
		lista = reservaDaoImp.getReservas();
		List<Prestamo> listaPrestamo;
		listaPrestamo = prestamoDaoImp.getPrestamos();
		System.out.println(lista);
		System.out.println(listaPrestamo);
		int cantReservasPorUsuario=0;
		int cantPrestamosPorUsuario=0;
		if(lista!=null || listaPrestamo!=null){
			for (int i = 0 ; i < lista.size(); i++) {
				if(lista.get(i).getUsuario().getId() == user.getId()) {
					cantReservasPorUsuario++;
				}
			}
			
			
			for (int i = 0 ; i < lista.size(); i++) {
				if(listaPrestamo.get(i).getId().getUsuario().getId() == user.getId()) {
					cantPrestamosPorUsuario++;
				}
			}
		}
		if(user != null && admin.getAdmin()==1 && cantPrestamosPorUsuario == 0 && cantReservasPorUsuario == 0) {
			usuarioDaoImp.eliminarUsuario(user.getId(), usuario);
		}
		else {
			throw new ExceptionController("Error eliminando usuario");
		}
		
	}
}

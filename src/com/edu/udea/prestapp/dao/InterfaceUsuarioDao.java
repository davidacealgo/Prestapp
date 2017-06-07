package com.edu.udea.prestapp.dao;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.List;

import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

public interface InterfaceUsuarioDao {
	public void doLogin(String usuario, String contrasena) throws ExceptionController;
	public Usuario getUsuario(String usuario) throws ExceptionController;
	public void restablecerContrasena(String usuario, String correo, String contrasenaActual, String contrasenaNueva) throws ExceptionController;
	public void registrarUsuario(int id, String nombres, String apellidos, String correo, String usuario, String contrasena, String tipoId, String telefono, boolean admin) throws ExceptionController;
	public void modificarUsuario(int id, String usuario, String nombres, String apellidos, String telefono, String correo) throws ExceptionController;
	public void eliminarUsuario(int id, String usuario) throws ExceptionController;
	public List<Usuario> getUsuarios() throws ExceptionController;
}

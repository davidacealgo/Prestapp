package com.edu.udea.prestapp.dao;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.Date;
import java.util.List;

import com.edu.udea.prestapp.dto.Sancion;
import com.edu.udea.prestapp.exception.ExceptionController;

public interface InterfaceSancionDao {
	public void sancionarUsuario(int idUsuario, String tipoSancion, Date inicioSancion, Date finSancion) throws ExceptionController;
	public void eliminarSancion(int id) throws ExceptionController;
	public List<Sancion> getSanciones() throws ExceptionController;
}

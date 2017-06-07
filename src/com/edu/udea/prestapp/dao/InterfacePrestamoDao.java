package com.edu.udea.prestapp.dao;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.Date;
import java.util.List;

import com.edu.udea.prestapp.dto.Prestamo;
import com.edu.udea.prestapp.exception.ExceptionController;

public interface InterfacePrestamoDao {
	public void realizarPrestamo(int idUsuario, int idObjeto, Date fechaPrestamo) throws ExceptionController; //o enviarle el usuario completo (?)
	public List<Prestamo> getPrestamos() throws ExceptionController; 
	public void realizarDevolucion(int idUsuario, int idObjeto, Date fechaDevolucion) throws ExceptionController;
	public List<Prestamo> prestamosACaducar() throws ExceptionController;
}

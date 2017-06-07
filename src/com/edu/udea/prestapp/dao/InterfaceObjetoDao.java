package com.edu.udea.prestapp.dao;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.Date;
import java.util.List;

import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.exception.ExceptionController;

public interface InterfaceObjetoDao {
	public List<Objeto> getObjetos() throws ExceptionController;
	public List<Objeto> getObjetosDisponibles() throws ExceptionController;
	public List<Objeto> getObjetosNoDisponibles() throws ExceptionController;
	public String modificarDisponibilidad (int id, int tipoCambio) throws ExceptionController;
	public String eliminarObjeto(int idObjeto) throws ExceptionController;
	public Objeto getObjeto(int id) throws ExceptionController;
}

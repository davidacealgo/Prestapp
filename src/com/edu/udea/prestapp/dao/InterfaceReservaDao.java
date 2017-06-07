package com.edu.udea.prestapp.dao;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.Date;
import java.util.List;

import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.exception.ExceptionController;

public interface InterfaceReservaDao {
	public void realizarReserva (int idUsuario, int idObjeto, Date fechaPrestamo) throws ExceptionController;
	public void cancelarReserva (int id) throws ExceptionController;
	public Reserva getReserva(int idUsuario) throws ExceptionController;
	public void modificarReserva (int id, Date nuevaFecha) throws ExceptionController; 
	public List<Reserva> getReservas() throws ExceptionController;
}

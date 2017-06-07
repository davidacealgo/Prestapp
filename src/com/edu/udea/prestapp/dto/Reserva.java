package com.edu.udea.prestapp.dto;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 * 
 * En esta clase se determinan los atributos de Reserva
 * id se refiere al identificador unico
 * Usuario es el usuario que realiza la reserva
 * Objeto es el objeto a reservar
 * fechaReserva es la fecha en la que se realizó la reserva
 * fechaPrestamo es la fecha en la que se debe realizar el prestamo solicitado en reserva.
 */

import java.util.Date;

public class Reserva {
	private int id;
	private Usuario usuario;
	private Objeto objeto;
	private Date fechaReserva;
	private Date fechaPrestamo;
	
	
	public Reserva(){
		
	}
	
	public Reserva(Usuario usuario, Objeto objeto, Date fechaReserva, Date fechaPrestamo) {
		this.usuario = usuario;
		this.objeto = objeto;
		this.fechaReserva = fechaReserva;
		this.fechaPrestamo = fechaPrestamo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public Date getFechaReserva() {
		return fechaReserva;
	}
	public void setFechaReserva(Date fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	
	
}

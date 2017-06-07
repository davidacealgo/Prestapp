package com.edu.udea.prestapp.dto;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 * 
 * en esta clase se determina la clave primaria de Prestamo
 * objeto se refiere al objeto que se presta
 * usuario al usuario que realiza el prestamo.
 */

import java.io.Serializable;

public class PrestamoID implements Serializable{

	private Objeto objeto;
	private Usuario usuario;
	
	public PrestamoID(){}
	
	public PrestamoID(Objeto objeto, Usuario usuario) {
		super();
		this.objeto = objeto;
		this.usuario = usuario;
	}
	public Objeto getObjeto() {
		return objeto;
	}
	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

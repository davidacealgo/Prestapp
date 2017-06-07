package com.edu.udea.prestapp.dto;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 * 
 * En esta clase se determinan los atributos de Sancion
 * id se refiere al identificador unico
 * Usuario es el usuario que se sanciona
 * tipoSancion es el tipo de sanción a aplicar: Prestamo vencido, Incumplir reserva,
 * Objeto perdido y Objeto dañado.
 * inicioSancion es la fecha en la cual se sanciona al usuario
 * finSancion es la fecha cuando se retira la sancion, puede ser programada o retirada manualmente
 */

import java.util.Date;

public class Sancion {

	private int id;
	private Usuario usuario;
	private String tipoSancion;
	private Date inicioSancion;
	private Date finSancion;
	
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
	public String getTipoSancion() {
		return tipoSancion;
	}
	public void setTipoSancion(String tipoSancion) {
		this.tipoSancion = tipoSancion;
	}
	public Date getInicioSancion() {
		return inicioSancion;
	}
	public void setInicioSancion(Date inicioSancion) {
		this.inicioSancion = inicioSancion;
	}
	public Date getFinSancion() {
		return finSancion;
	}
	public void setFinSancion(Date finSancion) {
		this.finSancion = finSancion;
	}
	
	
}

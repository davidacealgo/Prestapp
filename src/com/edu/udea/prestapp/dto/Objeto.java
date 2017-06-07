package com.edu.udea.prestapp.dto;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 * 
 * Esta clase tipo pojo contiene los datos de los Objetos.
 * El id se refiere al identificador unico de cada objeto
 * El nombre corresponde al nombre completo del objeto
 * Disponibilidad determina si un objeto se encuentra en stock(true) o prestado(false)
 * Reservado se refiere a si un objeto se encuentra reservado
 * Estado es el indicador de que el objeto se puede prestar con estados:"funcional", "malo"
 */

public class Objeto {
	//Dispositivo que se presta en el laboratorio
	private int id;
	private String nombre;
	private int disponibilidad;
	private int reservado;
	private String estado;
		
	public Objeto(){
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public int getReservado() {
		return reservado;
	}
	public void setReservado(int reservado) {
		this.reservado = reservado;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}

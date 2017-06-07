package com.edu.udea.prestapp.bl;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 * 
 * Esta clase contiene la logica del negocio pertinente a los prestamos.
 * 
 * Contiene los metodos realizarPrestamo y realizarDevolucion
 */

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.udea.prestapp.dao.ObjetoDaoImp;
import com.edu.udea.prestapp.dao.PrestamoDaoImp;
import com.edu.udea.prestapp.dao.ReservaDaoImp;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Prestamo;
import com.edu.udea.prestapp.dto.PrestamoID;
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

@Transactional
public class PrestamoBL {
	final Logger log = Logger.getLogger(PrestamoBL.class.getName());
	//Daos necesarios para hacer la logica del negocio
	@Autowired
	private PrestamoDaoImp prestamoDaoImp;
	
	@Autowired
	private UsuarioDaoImp usuarioDaoImp;
	
	@Autowired
	private ObjetoDaoImp objetoDaoImp;
	
	@Autowired
	private ReservaDaoImp reservaDaoImp;
	//Getters y setters
	public PrestamoDaoImp getPrestamoDaoImp() {
		return prestamoDaoImp;
	}
	public void setPrestamoDaoImp(PrestamoDaoImp prestamoDaoImp) {
		this.prestamoDaoImp = prestamoDaoImp;
	}
	//Metodo para realizar un prestamo de un objeto
	public void realizarPrestamo(String usuarioPrestamista, int idObjeto) throws ExceptionController {
		log.info("Iniciando metodo realizar prestamo");
		Usuario user = usuarioDaoImp.getUsuario(usuarioPrestamista);//se obtiene el usuario que realiza el prestamo
		Objeto obj = objetoDaoImp.getObjeto(idObjeto);//se obtiene el objeto a prestar
		List<Reserva> lista;//Lista de reservas
		lista = reservaDaoImp.getReservas();//se obtienen todas las reservas de la bd
		int cantReservasPorUsuario=0;
		for (int i = 0 ; i < lista.size(); i++) {
			if(lista.get(i).getUsuario().getId() == user.getId()) {//se verifica que el usuario tenga reservas
				cantReservasPorUsuario++;
			}
		}
		if(obj != null && user != null && cantReservasPorUsuario==0) {//se verifica que el objeto a prestar exista
			//que el usuario exista y que no tenga reservas
			if(obj.getDisponibilidad()==1) {//si esta disponible
				Date fechaPrestamo = new Date();
				prestamoDaoImp.realizarPrestamo(usuarioPrestamista, idObjeto, fechaPrestamo);//se realiza el prestamo
			}
			else {
				throw new ExceptionController("El objeto no se encuentra disponible");
			}
		}
		else {
			throw new ExceptionController("Objeto o usuario inexistente");
		}
	}
	
	//Metodo para realzar la devolucion de un objeto, y terminar el prestamo
	public void realizarDevolucion(String user, int idObjeto, int idUsuario) throws ExceptionController {
		log.info("Iniciando metodo realizar devolucion");
		PrestamoID prestamoId = null;
		Prestamo prestamo = null;
		Usuario usuario = usuarioDaoImp.getUsuario(user);
		Date fechaDevolucion = new Date();
		if(usuario.getAdmin() == 1) {//se verifica que sea administrador
			throw new ExceptionController("El usuario no es administrador");
		}
		else {
			List<Prestamo> listaPrestamos = prestamoDaoImp.getPrestamos();// lista de objetos prestados
			for ( int i = 0 ; i< listaPrestamos.size(); i++) {
				//se verifica que sea el usuario que realizó el prestamo y sea el objeto que prestó
				if(listaPrestamos.get(i).getId().getUsuario().getId() == idUsuario
						&& listaPrestamos.get(i).getId().getObjeto().getId() == idObjeto) {
					prestamo = listaPrestamos.get(i);//se obtiene tal prestamo
				}
			}
		prestamo.setFechaDevolucion(fechaDevolucion); //se le agrega la fecha de devolucion y se termina el prestamo
		}
		
	}
}
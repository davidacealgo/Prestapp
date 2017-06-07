package com.edu.udea.prestapp.bl;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.udea.prestapp.dao.ObjetoDaoImp;
import com.edu.udea.prestapp.dao.ReservaDaoImp;
import com.edu.udea.prestapp.dao.SancionDaoImp;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Sancion;
import com.edu.udea.prestapp.exception.ExceptionController;

@Transactional
public class ReservaBL {
	final Logger log = Logger.getLogger(ReservaBL.class.getName());
	//Daos necesarios para hacer la logica del negocio
	@Autowired
	private ReservaDaoImp reservaDaoImp;
	
	@Autowired
	private UsuarioDaoImp usuarioDaoImp;
	
	@Autowired
	private ObjetoDaoImp objetoDaoImp;
	
	@Autowired
	private SancionDaoImp sancionDaoImp;
	
	//Getters y setters
	public ReservaDaoImp getReservaDaoImp() {
		return reservaDaoImp;
	}

	public void setReservaDaoImp(ReservaDaoImp reservaDaoImp) {
		this.reservaDaoImp = reservaDaoImp;
	}
	
	//Metodo para realizar la reserva de un objeto
	public void reservarObjeto(String usuario, int idObjeto, Date fechaPrestamo) throws ExceptionController {
		log.info("Iniciando metodo reservar objeto");
		Date fechaActual = new Date(); 
		List<Reserva> lista = reservaDaoImp.getReservas();//lista de las reservas
		int cantReservasPorUsuario=0;
		for (int i = 0 ; i < lista.size(); i++) {
			//se verifica si el usuario tiene reservas
			if(lista.get(i).getUsuario().getId() == usuarioDaoImp.getUsuario(usuario).getId()) {
				cantReservasPorUsuario++;//se añade al contador
			}
		}
		List<Sancion> listaSancion = sancionDaoImp.getSanciones();//lista de sanciones
		for (int j = 0 ; j < listaSancion.size(); j++) {
			//se verifica que el usuario no tenga sanciones
			if(listaSancion.get(j).getUsuario().getUsuario().equals(usuario)) {
				throw new ExceptionController("El usuario está sancionado.");
			}
		}
		Objeto obj = objetoDaoImp.getObjeto(idObjeto);//se obtiene el objeto
		//se verifica que el usuario no tenga reservas, que el objeto este disponible, y que la fecha del prestamos no sea mayor a 3 dias
		if(fechaPrestamo.getTime() >= fechaActual.getTime()+259200000 && cantReservasPorUsuario == 0 && obj.getDisponibilidad()==1) {
			reservaDaoImp.realizarReserva(usuario, idObjeto, fechaPrestamo);//se realiza la reserva
		}
		else {
			throw new ExceptionController("Minimo 3 dias de anticipacion para reservar");
		}
	}
	//Metodo para realizar una cancelacion de una reserva
	public void cancelarReserva(int id, String usuario) throws ExceptionController {
		log.info("Iniciando metodo cancelar Reserva");
		Reserva reserva = reservaDaoImp.getReserva(id);//Se obtiene la reserva por la id
		Date fechaPrestamo = reserva.getFechaPrestamo();//Se obtiene la fecha del prestamo
		Date actual = new Date();
		//Verifica si 1 dia antes de la fecha del prestamo es mayor a la actual
		if(fechaPrestamo.getTime()-86400000 < actual.getTime()) {
			throw new ExceptionController("La reserva no se puede cancelar faltando un dia");
		}else {
			//verifica si la reserva existe, y si el usuario es el que realizó la reserva
			if(reserva != null && reserva.getUsuario().getUsuario().equals(usuario)) {
				reservaDaoImp.cancelarReserva(id);//se cancela la reserva
			}
			else {
				throw new ExceptionController("La reserva no existe o el usuario no es el mismo");
			}
		}
		
	}
	//Metodo para modificar la reserva
	public void modificarReserva(int id, String usuario, Date nuevaFecha) throws ExceptionController {
		log.info("Iniciando metodo modificar Reserva");
		Reserva reserva = reservaDaoImp.getReserva(id);//Se obtiene la reserva por la id
		Date fechaPrestamo = reserva.getFechaPrestamo();//Se obtiene la fecha del prestamo
		Date actual = new Date();
		//Se verifica que no falten 3 dias para realizar el prestamo
		if(fechaPrestamo.getTime()-(86400000*3) < actual.getTime()) {
			throw new ExceptionController("La reserva no se puede modificar faltando 3 dias");
		}else {
			//Se verifica que la reserva exista, y que el usuario fue el que realizó la reserva
			if(reserva != null && reserva.getUsuario().getUsuario().equals(usuario)) {
				reservaDaoImp.modificarReserva(id, nuevaFecha);//Se modifica la reserva
			}
			else {
				throw new ExceptionController("La reserva no existe o el usuario no es el mismo");
			}
		}
	}
}

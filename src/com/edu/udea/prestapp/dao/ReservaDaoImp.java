package com.edu.udea.prestapp.dao;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

public class ReservaDaoImp {
	@Autowired
	private ObjetoDaoImp objeto;
	
	@Autowired
	private UsuarioDaoImp user;
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void realizarReserva (String usuario, int idObjeto, Date fechaPrestamo) throws ExceptionController{
		if(fechaPrestamo == null) {//validando que se reciba la fecha
			throw new ExceptionController("La fecha no puede estar vacia");
		}
		if(usuario == null) {//validando que se reciba el usuario
			throw new ExceptionController("El usuario no puede estar vacio");
		}
		
		if(objeto.getObjeto(idObjeto)!=null && user.getUsuario(usuario)!=null){	//se valida si la reserva existe
				Objeto prestamo = objeto.getObjeto(idObjeto); //Id del objeto a reservar
				Usuario prestamista = user.getUsuario(usuario);//usuario de la persona que va a realizar el prestamo
				Date fechaReserva = new Date();
				Reserva reserva = new Reserva(prestamista,prestamo,fechaReserva,fechaPrestamo);//Se crea una reserva con los datos
				Session session = null;
				try{
					session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
					session.save(reserva);
				}catch(HibernateException e){
					throw new ExceptionController("Error al realizar el prestamo");
				}finally{
					//session.close();
				}
		}else{
			throw new ExceptionController("El objeto no existe");
		}
	}
	
	public void cancelarReserva (int id) throws ExceptionController{
		Reserva reserva = getReserva(id); //se obtiene el usuario por medio del getReserva
		Session session = null;
		if(reserva.getId()==id){//Se verifican las id's
			try{
				session = sessionFactory.getCurrentSession();//Se obtiene la sesion
				session.delete(reserva);
			}catch(HibernateException e){
				throw new ExceptionController("Error consultando reserva");
			}finally{
				//session.close();
			}
		}else{
			throw new ExceptionController("Credenciales incorrectas");
		}
	}
	
	public Reserva getReserva(int id) throws ExceptionController{
		Session session = null;
		Reserva reserva = new Reserva();
		try{
			session = sessionFactory.getCurrentSession();//Se obtiene la sesion
			Criteria criteria = session.createCriteria(Reserva.class);
			criteria.add(Restrictions.eq("id", id));//Se agrega la condicion con la que se hace la consulta
			reserva = (Reserva)criteria.uniqueResult();//Unique porque sé y estoy seguro que me va a arrojar solo 1 valor
			//uniqueResult retorna un objeto tipo "object"
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando reserva", e);
		}finally{
			//session.close();
		}
		return reserva;
	}
	public void modificarReserva (int id, Date nuevaFecha) throws ExceptionController{
		if(nuevaFecha==null){
			throw new ExceptionController("La fecha no puede estar vacia");
		}
		Reserva reserva = getReserva(id); //se obtiene la reserva por medio del getReserva
		if(reserva!=null || nuevaFecha.after(reserva.getFechaReserva())){//Se verifica que la nueva fecha sea despues de la anterior
			reserva.setFechaReserva(nuevaFecha);
			Session session = null;
			try {
				session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
				session.update(reserva);
			} catch (HibernateException e) {
				throw new ExceptionController("Error consultando reserva");
			}finally {
				//session.close();
			}
		}else{
			throw new ExceptionController("Error en la fecha de reserva");
		}
		
	}
	public List<Reserva> getReservas() throws ExceptionController{
		List<Reserva> lista = new ArrayList<Reserva>();//Lista donde se guardarán las reservas
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Reserva.class); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando reservas",e);
		}
		return lista;
	}
}

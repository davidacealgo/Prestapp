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
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Prestamo;
import com.edu.udea.prestapp.dto.PrestamoID;
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Sancion;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

public class PrestamoDaoImp {
	private SessionFactory sessionFactory;
	@Autowired
	private ObjetoDaoImp objeto;
	@Autowired
	private UsuarioDaoImp user;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void realizarPrestamo(String usuario, int idObjeto, Date fechaPrestamo) throws ExceptionController{
		Date date = new Date(2017,1,1);//Validacioin para que la fecha sea del año actual
		System.out.println(fechaPrestamo+" "+date);
		Date fechaDevolucion = fechaPrestamo; //Fecha limite del prestamo
		fechaDevolucion.setTime(fechaPrestamo.getTime()+604800000);//Se le suma en milisegundos una semana
		if(usuario.isEmpty() || usuario == null) {
			throw new ExceptionController("El usuario no puede estar vacio");
		}
		if(fechaPrestamo == null) {
			throw new ExceptionController("La fecha de prestamo no puede estar vacia");
		}
		//UsuarioDaoImp user = new UsuarioDaoImp();
		
		if(objeto.getObjeto(idObjeto)!=null && user.getUsuario(usuario)!=null){	//se valida si la reserva existe
			Objeto prestado = objeto.getObjeto(idObjeto); //Id del objeto
			Usuario prestamista = user.getUsuario(usuario);//usuario del que presta el objeto
			Date fechaPrestacion = new Date();
			PrestamoID prest = new PrestamoID(prestado,prestamista);
			Prestamo prestamo = new Prestamo(prest,fechaPrestamo,fechaDevolucion,fechaPrestamo);//Se crea un prestamo con los datos
			/*Buscar si hay reservas de este objeto con el id del prestamista para la fecha de reserva*/
			Session session = null;
			try{
				session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
				session.save(prestamo);//se guarda el prestamo
			}catch(HibernateException e){
				throw new ExceptionController("Error al realizar el prestamo");
			}finally{
				//session.close();
			}
	}else{
		throw new ExceptionController("El objeto no existe");
	}
	}
	public List<Prestamo> getPrestamos() throws ExceptionController{
		List<Prestamo> lista = new ArrayList<Prestamo>();//Lista en donde se guardaran los prestamos
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Prestamo.class); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
		}catch (HibernateException e) {
			throw new ExceptionController("Error consultando reservas",e);
		}finally {
			//session.close();
		}
		return lista;
	}
	public void realizarDevolucion(int idUsuario, int idObjeto, Date fechaDevolucion) throws ExceptionController{
		
	}
	public List<Prestamo> prestamosACaducar() throws ExceptionController{
		List<Prestamo> lista = new ArrayList<Prestamo>(); // lista en donde se guardan todos los prestamos de la bd
		Session session = null;
		Date fechaActual = new Date(); //Fecha actual para tomar un referente del vencimiento del prestamo
		Date fechaDevolucion = null;
		long dia = 86400000;//Logitud del dia en milisegundos
		List<Prestamo> vencidos = new ArrayList<Prestamo>();//Lista en donde se guardarán los prestamos a caducar
		try{
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Prestamo.class); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
			for(int i=0;i<lista.size();i++){//Ciclo para recorrer todos los prestamos de la bd
				fechaDevolucion=lista.get(i).getFechaDevolucion();//Se obtiene la fecha de devolucion de cada prestamo
				if(dia<=fechaDevolucion.getTime()-fechaActual.getTime()){//Si la diferencia entre la fecha de devolucion y la fecha actual es menor que 1 dia
					vencidos.add(lista.get(i));//se agrega a la lista de proximos a caducar
				}
			}
		}catch (HibernateException e) {
			throw new ExceptionController("Error consultando reservas",e);
		}finally {
			//session.close();
		}
		return vencidos;
	}
}

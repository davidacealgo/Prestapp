package com.edu.udea.prestapp.bl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.udea.prestapp.dao.ObjetoDaoImp;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 * 
 * La logica del negocio de los objetos contienen los metodos:
 * 
 * mostrarObjetos, el cual retorna una lista de los objetos disponibles al
 * momento de ejecutar la operacion
 * 
 * modificarDisponibilidad, el cual tendrá 4 estados de disponibilidad,
 * disponible, prestado, reservado y no reservado, y el metodo permitirá modificar este estado
 * 
 * mostrarObjetosPrestado, el cual retornara una lista de los objetos que 
 * cumplan la condicion de estar prestados al momento de ejecutar la operacion
 * 
 * eliminarObjeto, el cual eliminara un objeto de la base de datos
 */

@Transactional
public class ObjetoBL {
	final Logger log = Logger.getLogger(ObjetoBL.class.getName());
	@Autowired
	private ObjetoDaoImp objetoDaoImp;
	@Autowired
	private UsuarioDaoImp usuarioDaoImp;
	//Daos necesarios para realizar la logica de negocio de objeto
	
	//getters y setters
	public ObjetoDaoImp getObjetoDaoImp() {
		return objetoDaoImp;
	}

	public void setObjetoDaoImp(ObjetoDaoImp objetoDaoImp) {
		this.objetoDaoImp = objetoDaoImp;
	}
	
	//Metodo para mosstrar objetos disponibles
	public List<Objeto> mostrarObjetos() throws ExceptionController {
		log.info("Iniciando metodo mostrar objetos");
		int cantDisponibles=0;//Contador de objetos disponibles
		List<Objeto> lista;//lista para los objetos de la bd
		List<Objeto> listaDisponibles = new ArrayList();//lista para los disponibles
		try {
			lista= objetoDaoImp.getObjetos();//Se obtienen todos los objetos de la bd
		}catch(Exception e){
			throw new ExceptionController("Error consultando objetos", e);
		}
		for (int i = 0; i<lista.size(); i++) {//Se recorre toda la lista de objetos
			//Se verifica la disonibilidad y si esta funcional
			if(lista.get(i).getDisponibilidad()==1 && lista.get(i).getReservado()==1 && lista.get(i).getEstado().equals("funcional")) {
				listaDisponibles.add(lista.get(i));//se agrega a la lista de disponibles
				log.debug(lista.get(i).getNombre() + " disponible");
				cantDisponibles++;//se aumenta el contador
			}
			else {
				log.debug(lista.get(i).getNombre() + " no disponible");
			}
		}
		return listaDisponibles;
	}
	
	//Metodo para modificar la disponibilidad de un objeto
	public void modificarDisponibilidad(int id, int tipoCambio) throws ExceptionController {
		log.info("Iniciando metodo modificar disponibilidad con id: "+id+ " y tipoCambio: "+tipoCambio);
		//tipos de cambio: 1=Disponible, 2=Prestado, 3 = Reservado 4 = cancelar Reserva
		Objeto obj = null;
		//Date fecha = new Date();
		try{
			obj = objetoDaoImp.getObjeto(id);//se obtiene el objeto por su id
		}catch(Exception e) {
			throw new ExceptionController("Error consultando objeto con id "+id,e);//error si no lo encuentra
		}
		if(tipoCambio==1 && obj.getDisponibilidad()==0) {//
			/*System.out.println("Cambio tipo 1");*/
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);//Se modifica a disponible
		}
		/*else {
			throw new ExceptionController(tipoCambio+ "El objeto ya se encuentra disponible "+obj.isDisponibilidad());
		}*/
		else if(tipoCambio==2 && obj.getDisponibilidad()==1) {
			/*System.out.println("Cambio tipo 2");*/
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);//se modifica a prestado
		}
		/*else {
			throw new ExceptionController(tipoCambio+ "El objeto ya se encuentra prestado "+obj.isDisponibilidad());
		}*/
		else if(tipoCambio==3 && obj.getReservado()==0) {
			/*System.out.println("Cambio tipo 3");*/
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);//se modifica a reservado
		}
		/*else {
			throw new ExceptionController(tipoCambio+ "El objeto ya se encuentra reservado "+obj.isReservado());
			}*/
		else if(tipoCambio==4 && obj.getReservado()==1) {
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);//se modifica a no reservado
		}
		else {
			throw new ExceptionController(tipoCambio+ "El objeto no se encontraba reservado "+obj.getReservado());
		}
	}
	//Metodo para mostrar los objetos Prestados
	public List<Objeto> mostrarObjetosPrestados() throws ExceptionController{
		log.info("Iniciando metodo mostrar objetos prestados");
		List<Objeto> lista;//lista para todos los objetos de la bd
		List<Objeto> listaPrestados = new ArrayList();//lista para los prestados
		lista = objetoDaoImp.getObjetosNoDisponibles();//obtiene los objetos no disponibles
		System.out.println( lista.size()+"123");
		for(int i = 0 ; i < lista.size(); i++) {//recorre la lista de todos los objetos de la bd
			System.out.println( lista.size()+"123");
			if(lista.get(i).getReservado()==2) {//se verifica que no este reservado
				System.out.println(lista.get(i).getNombre());
				listaPrestados.add(lista.get(i));//se añade a los prestados
			}
		}
		return listaPrestados;
	}
	//Metodo para eliminar un objeto de la bd
	public void eliminarObjeto(String user,int idObjeto) throws ExceptionController {
		log.info("Iniciando metodo eliminar objeto");
		Usuario usuario = usuarioDaoImp.getUsuario(user);
		System.out.println(user);
		if(usuario.getAdmin()==0) {//se verifica que sea administrador
			throw new ExceptionController("El usuario no es administrador");
		}
		else {
			Objeto obj = objetoDaoImp.getObjeto(idObjeto);//se obtiene el objeto por su id
			if(obj != null && obj.getDisponibilidad()==1 && obj.getReservado()==0) {//se verifica que no este prestado ni reservado
				objetoDaoImp.eliminarObjeto(idObjeto);//se elimina el objeto
			}
			else {
				throw new ExceptionController("El objeto no puede ser borrado");
			}
		}
	}
}
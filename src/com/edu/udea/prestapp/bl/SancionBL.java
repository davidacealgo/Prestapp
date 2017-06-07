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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.dao.ObjetoDaoImp;
import com.edu.udea.prestapp.dao.PrestamoDaoImp;
import com.edu.udea.prestapp.dao.ReservaDaoImp;
import com.edu.udea.prestapp.dao.SancionDaoImp;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Prestamo;
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Sancion;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

@Transactional
public class SancionBL {
	final Logger log = Logger.getLogger(SancionBL.class.getName());
	//Daos necesarios para hacer la logica del negocio
	@Autowired
	private SancionDaoImp sancionDaoImp;
	@Autowired
	private UsuarioDaoImp usuarioDaoImp;
	@Autowired
	private ReservaDaoImp reservaDaoImp;
	@Autowired
	private ObjetoDaoImp objetoDaoImp;
	@Autowired
	private PrestamoDaoImp prestamoDaoImp;
	
	//Getters and setters
	public SancionDaoImp getSancionDaoImp() {
		return sancionDaoImp;
	}

	public void setSancionDaoImp(SancionDaoImp sancionDaoImp) {
		this.sancionDaoImp = sancionDaoImp;
	}
	//Metodo para sancionar el usuario dependiendo del tipo de sancion
	public void sancionarUsuario(String tipoSancion, String usuario, String adm, int idObjeto, int idReserva) throws ExceptionController {
		log.info("Iniciando metodo Sancionar usuario");
		Usuario admin = usuarioDaoImp.getUsuario(adm);//Obtiene el usuario admin
		Usuario user = usuarioDaoImp.getUsuario(usuario);//Obtiene el usuario a sancionar
		Date inicioSancion = new Date();
		Date finSancion = new Date();
		//Verifica si el usuario existe, si el admin existe, y si de verdad es administrador
		if(user != null && admin != null  && admin.getAdmin()==1) {
			//Sancion para prestamo vencido
			if(tipoSancion.equals("Prestamo vencido")) {
				finSancion.setTime(inicioSancion.getTime()+(86400000*7));//Se va a sancionar 7 dias
				try {
					System.out.println(usuario+""+ tipoSancion+""+inicioSancion+""+finSancion);
					sancionDaoImp.sancionarUsuario(usuario, tipoSancion, inicioSancion, finSancion);
				}catch(Exception e) {
					throw new ExceptionController("Error creando sancion");
				}
			}
			//Sancion para incumplir reserva
			else if(tipoSancion.equals("Incumplir reserva")){
				Reserva reserva = reservaDaoImp.getReserva(idReserva);//Se obtiene la reserva por id
				Usuario usuarito = usuarioDaoImp.getUsuario(usuario);//Se obtiene el usuario a comparar
				Usuario usuarito2 = reserva.getUsuario();//Se obtiene el usuario que realizo la reserva
				finSancion.setTime(inicioSancion.getTime()+(86400000*7));//Se va a sancionar 7 dias
				//Se verfica que exista la reserva
				//Que existan ambos usuarios
				//Y que los usuarios coincidan
				if(reserva != null && usuarito!=null && usuarito2!=null && usuarito.equals(usuarito2)){
					try{
						sancionDaoImp.sancionarUsuario(usuario, tipoSancion, inicioSancion, finSancion);//Se realiza la sancion
					}catch(ExceptionController e){
						throw new ExceptionController("Error en la sanción");
					}
				}else{
					throw new ExceptionController("Credenciales incorrectas");
				}
				
			}//Sancion para objeto perdido
			else if(tipoSancion.equals("Objeto perdido")) {
				Usuario usuarito = usuarioDaoImp.getUsuario(usuario);//Se obtiene el usuario a sancionar
				finSancion.setTime(inicioSancion.getTime()+(86400000*7));//Se va a sancionar 7 dias
				if(usuarito == null) {//Se verifica que exista
					throw new ExceptionController("Usuario no existe");
				}
				Objeto obj = objetoDaoImp.getObjeto(idObjeto);//Se obtiene la id del objeto
				List<Prestamo> lista = prestamoDaoImp.getPrestamos();//Se obtiene la lista de prestamos
				boolean existePrestamo= false;
				for ( int i = 0; i<lista.size(); i++) {//Se recorren los prestamos
					//se verifica que el usuario realizo el prestamo y que el objeto se encuentre en la lista de prestamos
					if(lista.get(i).getId().getUsuario().equals(usuarito) && lista.get(i).getId().getObjeto().equals(obj)) {
						existePrestamo = true;//Es decir que existe el prestamo
					}
				}
				//Se verifica que el objeto exista
				if(obj != null) {
					sancionDaoImp.sancionarUsuario(usuario, tipoSancion, inicioSancion, finSancion);//Se realiza la sancion
				}else {
					throw new ExceptionController("No existe objeto");
				}
			}
			//Sancion por objeto dañado
			else if(tipoSancion.equals("Objeto dañado")) {
				Usuario usuarito = usuarioDaoImp.getUsuario(usuario);//Se obtiene el usuario por el username
				Objeto obj = objetoDaoImp.getObjeto(idObjeto);//Se obtiene el objeto por la id
				List<Prestamo> lista = prestamoDaoImp.getPrestamos();//Se obtiene la lista de prestamos
				//Se verifica que el usuario exista y objeto exista
				if(usuarito!=null && obj != null){
					for(int i = 0; i<lista.size(); i++) {//Se recorre la lista de prestamos
						//Se verifica que el usuario haya realizado el prestamo, y que el objeto esté prestado
						if(lista.get(i).getId().getUsuario().equals(usuarito) && lista.get(i).getId().getObjeto().equals(obj)){
							try{
								sancionDaoImp.sancionarUsuario(usuario, tipoSancion, inicioSancion, finSancion);//Se realiza la sancion
							}catch (ExceptionController e) {
								throw new ExceptionController("No se realizó la sanción");
							}
						}else{
							throw new ExceptionController("EL objeto no se ha prestado");
						}
					}
				}else{
					throw new ExceptionController("Credenciales incorrectas");
				}
			}
		}
	}
	//Metodo para eliminar una sancion aplicada a un usuario
	public void eliminarSancion(int id, String usuario, String tipoSancion) throws ExceptionController{
		log.info("Iniciando metodo eliminar sancion");
		boolean existeSancion = false;
		Sancion sancion = null;//Variable para comparar sanciones
		Usuario user = usuarioDaoImp.getUsuario(usuario);
		String username = usuarioDaoImp.getUsuario(usuario).getUsuario();
		List<Objeto> listaObjetos = objetoDaoImp.getObjetos();
		if(user!=null && user.getAdmin()==1){
			try {
				List<Sancion> sanciones = sancionDaoImp.getSanciones();
				for (int i = 0 ; i < sanciones.size(); i++) {
					if(sanciones.get(i).getId() == id && username!=null) {
						sancion = sanciones.get(i);
						Date limitesancion = new Date();
						if(limitesancion.getTime() <= sancion.getFinSancion().getTime()){
							//Vamos a verificar si el objeto ya fue devuelt
							if(tipoSancion.equals("Objeto perdido")){
							}else{
								sancionDaoImp.eliminarSancion(usuario);
							}
						}else{
							throw new ExceptionController("La sancion no esta activa");
						}
					}else{
						throw new ExceptionController("Credenciales incorrectas");
					}
				}
			}catch(ExceptionController e) {
				throw new ExceptionController("Error obteniendo sancion");
			}
		}
	}
}

package com.edu.udea.prestapp.exception;

import org.apache.log4j.Logger;
 /**
 * @Author Julian Vasquez - julivas96@gmail.com 
 * @Version = 1.0 
 * */ 
public class ExceptionController extends Exception {
	private Logger logger = Logger.getLogger(this.getClass()); // para escribir el log en un archivo
	//clase personalizada para el manejo de las excepciones 
	public ExceptionController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExceptionController(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ExceptionController(String message, Throwable cause) {
		super(message, cause);
		logger.error(message,cause);//se puede replicar en cada uno de los constructores
		// TODO Auto-generated constructor stub
	}

	public ExceptionController(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ExceptionController(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}

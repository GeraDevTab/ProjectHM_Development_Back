package com.HM.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HM.model.Payment;
import com.HM.model.Product;
import com.HM.repository.PaymentRepository;
import com.HM.repository.ProductRepository;

@Service
public class PaymentService {
	
	private final PaymentRepository paymentRepository;
	
	// Creo un cable para conectar entidades (ProductRepository - ProductService)
	@Autowired
	
	public PaymentService(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}
	
	
	
	/****************************** CREATE ******************************/
	// Crear payment(Create)
	public void createPayment(Payment payment) {
		
		// Primero guardamos el dato en un optional
		// Si hay algo en ese Optional.isPresent nos regresa un true o un false
		
		// Necesitamos saber si el producto buscado existe
		Optional<Payment> findPayment = paymentRepository.findById(payment.getIdPayment()); // Buscamos por nombre
		
		if(findPayment.isPresent()) {
			throw new IllegalStateException("The payment id" + payment.getIdPayment() + "  already exists");
		}
		else {
			paymentRepository.save(payment);
		}
		// Si no existe, entonces lo guardamos en la base de datos
		
	}
	
	
	
	/****************************** READ ******************************/
	// Leer payment (Read)
	public List<Payment> readPayments() {
		return paymentRepository.findAll();
	}
	
	public Payment readPayment(Integer idPayment){
		 return paymentRepository.findById(idPayment).orElseThrow(()-> new IllegalStateException("The payment " + "with id " + idPayment + " does not exist.") );
	}
	
	
	
/****************************** UPDATE ******************************/
	
	// Actualizar producto (Update)
	
	//Necesitamos pasar todos los parametros, para contemplar todas las modificaciones posibles
	public void updatePayment(Integer idPayment, Date paymentDate, Integer fk_idOrder) {		
		//Si el producto existe, entonces se modifica
		if (paymentRepository.existsById(idPayment)) {
			//entonces lo modifico
			
			//traigo el objeto, y lo pongo en la variable temporal
			Payment findPayment = paymentRepository.getById(idPayment);
			if (paymentDate!=null) findPayment.setPaymentDate(paymentDate);
			if (fk_idOrder!=null) findPayment.setFk_idOrder(fk_idOrder);
			
			//cuando termino de editar el objeto...
			paymentRepository.save(findPayment);
			//Si el producto NO existe, no puedo modificar nada y muestro un mensaje
		}else {
			System.out.println("The payment with id " + idPayment + " does not exist");
		}	
	}
	
	
	
/****************************** DELETE ******************************/
	
	//Borrar producto (Delete)
		public void deletePayment(Integer idPayment) {
			//Buscamos un producto por id, y si existe...
			if (paymentRepository.existsById(idPayment)) {//true
				//Lo borramos
				paymentRepository.deleteById(idPayment);
			}
		}



public void updatePayment(Date paymentDate, Integer fk_idOrder) {
	// TODO Auto-generated method stub
	
}
	
}
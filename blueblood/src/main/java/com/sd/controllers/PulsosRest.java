package com.sd.controllers;

import com.sd.blueblood.EmailServiceImpl;
import com.sd.dao.Posts;
import com.sd.dao.Pulsos;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SPRING - PULSOS REST
 * 
 * @author BlueFox - SD
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/pulsos")
public class PulsosRest {

	@Autowired
	private EmailServiceImpl mailer;
	
	@GetMapping
	ArrayList<Pulsos> getAll() {
		try {
			return Pulsos.getAllRecords(0);
		} catch (SQLException ex) {
			Logger.getLogger(PulsosRest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@GetMapping("/test")
	String test() {
		Map<String,String> parametros=new HashMap<String, String>();
		parametros.put("usuario_mail", "saguirre@sd-bo.com");
		parametros.put("jefe_nm", "Sergio Aguirre");
		parametros.put("tipo", "HACEMOS QUE SUCEDA DE LA FORMA CORRECTA");
		try {
			mailer.sendSimpleMessage(parametros);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "el correo no se pudo enviar";
		}
		return "enviado";
	}

	@GetMapping("/{id}")
	@ResponseBody
	Pulsos getRecord(@PathVariable int id) {
		try {
			return Pulsos.getRecord(id);
		} catch (SQLException ex) {
			Logger.getLogger(PulsosRest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@PostMapping
	@ResponseBody
	ResponseEntity<Posts> save(@RequestBody Pulsos obj) {
		Map<String,String> parametros;
		Posts respuesta = new Posts();
		try {
			parametros=obj.insertPulso();
			parametros.put("post_id", ""+obj.getPost_id());
			respuesta=Posts.getRecord(obj.getPost_id(), obj.getUsuario_id()).get(0);
		} catch (SQLException ex) {
			ex.printStackTrace();
			respuesta.setMensaje(ex.getMessage());
			return ResponseEntity.badRequest().body(respuesta);
		}
		try {
			mailer.sendSimpleMessage(parametros);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(respuesta);
		}
		return ResponseEntity.ok(respuesta);
	}
}

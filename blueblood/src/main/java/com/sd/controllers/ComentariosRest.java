package com.sd.controllers;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sd.dao.Comentario;
import com.sd.dao.Posts;

/**
 * SPRING - GLOBULOS REST
 * @author BlueFox - SD
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/comentarios")
public class ComentariosRest {
    
    @PostMapping
    @ResponseBody ResponseEntity<Posts> save(@RequestBody Comentario obj) {
        try {
        	System.out.println(obj);
        	obj.insert();
        	return ResponseEntity.ok(Posts.getRecord(obj.getPost_id(), obj.getUsuario_id()).get(0));
        } catch (SQLException ex) {
        	ex.printStackTrace();
        	Posts error = new Posts();
			error.setMensaje(ex.getMessage());
			return ResponseEntity.badRequest().body(error);
        }
    }    
}


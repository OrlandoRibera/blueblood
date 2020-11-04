package com.sd.controllers;

import com.sd.dao.Globulos;
import com.sd.dao.Posts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * SPRING - GLOBULOS REST
 * @author BlueFox - SD
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/globulos")
public class GlobulosRest {
    
    @GetMapping
    ArrayList<Globulos> getAll() {
        try {
            return Globulos.getAllRecords(0);
        } catch (SQLException ex) {
            Logger.getLogger(GlobulosRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping("/{id}")
    @ResponseBody Globulos getRecord(@PathVariable int id) {
        try {
            return Globulos.getRecord(id);
        } catch (SQLException ex) {
            Logger.getLogger(GlobulosRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PostMapping
    @ResponseBody ResponseEntity<Posts> save(@RequestBody Globulos obj) {
        try {
        	obj.insert();
        	return ResponseEntity.ok(Posts.getRecord(obj.getPost_id(), obj.getUsuario_id()).get(0));
        } catch (SQLException ex) {
        	Posts error=new Posts();
        	error.setMensaje(ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }    
}


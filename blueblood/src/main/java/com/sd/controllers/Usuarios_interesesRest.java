package com.sd.controllers;

import com.sd.dao.Usuarios_intereses;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SPRING - USUARIOS_INTERESES REST
 * @author BlueFox - SD
 */

@RestController
@RequestMapping("/usuarios_intereses")
public class Usuarios_interesesRest {
    
    @GetMapping
    ArrayList<Usuarios_intereses> getAll() {
        try {
            return Usuarios_intereses.getAllRecords(0);
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios_interesesRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping("/{id}")
    @ResponseBody Usuarios_intereses getRecord(@PathVariable int id) {
        try {
            return Usuarios_intereses.getRecord(id);
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios_interesesRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PostMapping
    @ResponseBody String save(@RequestBody Usuarios_intereses obj) {
        try {
        	obj.insert();
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios_interesesRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    @PutMapping
    @ResponseBody String update(@RequestBody Usuarios_intereses obj) {
        try {
        	obj.insert();
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios_interesesRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    
    @DeleteMapping("/{id}")
    @ResponseBody long delete(@PathVariable int id) {
    	Usuarios_intereses obj=new Usuarios_intereses(id);
    	try {
        	return obj.delete();
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios_interesesRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}


package com.sd.controllers;

import com.sd.dao.Grupo;
import com.sd.dao.Intereses;
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
 * SPRING - INTERESES REST
 * @author BlueFox - SD
 */

@RestController
@RequestMapping("/intereses")
public class InteresesRest {
    
	@GetMapping
    ArrayList<Grupo> getAll() {
        try {
            return Grupo.getAllRecords(0);
        } catch (SQLException ex) {
            Logger.getLogger(GrupoRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping("/{id}")
    @ResponseBody ArrayList<Usuarios_intereses> getRecord(@PathVariable int id) {
        try {
            return Usuarios_intereses.getAllRecords(id);
        } catch (SQLException ex) {
            Logger.getLogger(InteresesRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PostMapping
    @ResponseBody String save(@RequestBody Usuarios_intereses[] obj) {
        try {
        	for (Usuarios_intereses interes : obj) {
        		interes.insert();	
			}
        	
        } catch (SQLException ex) {
            Logger.getLogger(InteresesRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    @PutMapping
    @ResponseBody String update(@RequestBody Intereses obj) {
        try {
        	obj.insert();
        } catch (SQLException ex) {
            Logger.getLogger(InteresesRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    
    @DeleteMapping("/{id}")
    @ResponseBody long delete(@PathVariable String id) {
    	Intereses obj=new Intereses(id);
    	try {
        	return obj.delete();
        } catch (SQLException ex) {
            Logger.getLogger(InteresesRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}


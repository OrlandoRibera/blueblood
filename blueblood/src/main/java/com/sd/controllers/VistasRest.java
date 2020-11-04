package com.sd.controllers;

import com.sd.dao.Vistas;
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
 * SPRING - VISTAS REST
 * @author BlueFox - SD
 */

@RestController
@RequestMapping("/vistas")
public class VistasRest {
    
    @GetMapping
    ArrayList<Vistas> getAll() {
        try {
            return Vistas.getAllRecords(0);
        } catch (SQLException ex) {
            Logger.getLogger(VistasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping("/{id}")
    @ResponseBody Vistas getRecord(@PathVariable int id) {
        try {
            return Vistas.getRecord(id);
        } catch (SQLException ex) {
            Logger.getLogger(VistasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PostMapping
    @ResponseBody String save(@RequestBody Vistas obj) {
        try {
        	obj.insert();
        } catch (SQLException ex) {
            Logger.getLogger(VistasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    @PutMapping
    @ResponseBody String update(@RequestBody Vistas obj) {
        try {
        	obj.insert();
        } catch (SQLException ex) {
            Logger.getLogger(VistasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    
    @DeleteMapping("/{id}")
    @ResponseBody long delete(@PathVariable int id) {
    	Vistas obj=new Vistas(id);
    	try {
        	return obj.delete();
        } catch (SQLException ex) {
            Logger.getLogger(VistasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}


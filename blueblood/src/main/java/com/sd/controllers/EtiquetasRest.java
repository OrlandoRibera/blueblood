package com.sd.controllers;

import com.sd.dao.Etiquetas;
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
 * SPRING - ETIQUETAS REST
 * @author BlueFox - SD
 */

@RestController
@RequestMapping("/etiquetas")
public class EtiquetasRest {
    
    @GetMapping
    ArrayList<Etiquetas> getAll() {
        try {
            return Etiquetas.getAllRecords(0);
        } catch (SQLException ex) {
            Logger.getLogger(EtiquetasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping("/{id}")
    @ResponseBody Etiquetas getRecord(@PathVariable int id) {
        try {
            return Etiquetas.getRecord(id);
        } catch (SQLException ex) {
            Logger.getLogger(EtiquetasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PostMapping
    @ResponseBody String save(@RequestBody Etiquetas obj) {
        try {
        	obj.insert();
        } catch (SQLException ex) {
            Logger.getLogger(EtiquetasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    @PutMapping
    @ResponseBody String update(@RequestBody Etiquetas obj) {
        try {
        	obj.insert();
        } catch (SQLException ex) {
            Logger.getLogger(EtiquetasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    
    @DeleteMapping("/{id}")
    @ResponseBody long delete(@PathVariable int id) {
    	Etiquetas obj=new Etiquetas(id);
    	try {
        	return obj.delete();
        } catch (SQLException ex) {
            Logger.getLogger(EtiquetasRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}


package com.sd.controllers;

import com.sd.dao.Grupo;
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
 * SPRING - GRUPO REST
 * @author BlueFox - SD
 */

@RestController
@RequestMapping("/grupo")
public class GrupoRest {
    
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
    @ResponseBody Grupo getRecord(@PathVariable int id) {
        try {
            return Grupo.getRecord(id);
        } catch (SQLException ex) {
            Logger.getLogger(GrupoRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PostMapping
    @ResponseBody String save(@RequestBody Grupo obj) {
        try {
        	obj.insert();
        } catch (SQLException ex) {
            Logger.getLogger(GrupoRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    @PutMapping
    @ResponseBody String update(@RequestBody Grupo obj) {
        try {
        	obj.insert();
        } catch (SQLException ex) {
            Logger.getLogger(GrupoRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    
    @DeleteMapping("/{id}")
    @ResponseBody long delete(@PathVariable String id) {
    	Grupo obj=new Grupo(id);
    	try {
        	return obj.delete();
        } catch (SQLException ex) {
            Logger.getLogger(GrupoRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}


package com.sd.controllers;

import com.sd.dao.Posts_tags;
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
 * SPRING - POSTS_TAGS REST
 * @author BlueFox - SD
 */

@RestController
@RequestMapping("/posts_tags")
public class Posts_tagsRest {
    
    @GetMapping
    ArrayList<Posts_tags> getAll() {
        try {
            return Posts_tags.getAllRecords(0);
        } catch (SQLException ex) {
            Logger.getLogger(Posts_tagsRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping("/{id}")
    @ResponseBody Posts_tags getRecord(@PathVariable int id) {
        try {
            return Posts_tags.getRecord(id);
        } catch (SQLException ex) {
            Logger.getLogger(Posts_tagsRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PostMapping
    @ResponseBody String save(@RequestBody Posts_tags obj) {
        try {
        	obj.insert();
        } catch (SQLException ex) {
            Logger.getLogger(Posts_tagsRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    @PutMapping
    @ResponseBody String update(@RequestBody Posts_tags obj) {
        try {
        	obj.insert();
        } catch (SQLException ex) {
            Logger.getLogger(Posts_tagsRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listo!";
    }
    
    
    @DeleteMapping("/{id}")
    @ResponseBody long delete(@PathVariable long id) {
    	Posts_tags obj=new Posts_tags(id);
    	try {
        	return obj.delete();
        } catch (SQLException ex) {
            Logger.getLogger(Posts_tagsRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}


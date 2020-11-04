package com.sd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * SPRING - BEAN
 * @author BlueFox - SD
 */
public class Grupo implements IDAO{
// <editor-fold defaultstate="collapsed" desc="Atributos">
    private String nombre_id;
    private Intereses[] intereses;
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores + toString">
    public Grupo() {
    }
    
    public Grupo(String nombre_id) {
        this.nombre_id = nombre_id;
    }

    public Grupo(String nombre_id, Intereses[] intereses) {
		this.nombre_id = nombre_id;
		this.intereses = intereses;
	}
    
	@Override
    public String toString() {
    return nombre_id ;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="CRUD">   
    @Override
    public long insert() throws SQLException {
        return DAO_PA.insertRecord("params","grupo", this);
    }

    @Override
    public long update() throws SQLException {
        return DAO_PA.updateRecord("params","grupo", this);
    }

    @Override
    public long delete() throws SQLException {
        return DAO_PA.deleteRecord("params","grupo", this);
    }
    
    public static ArrayList<Grupo> getAllRecords(int limit) throws SQLException {
            return DAO_PA.getTableDAO("params","grupo",limit,Grupo.class);
    }
    public static Grupo getRecord(int id) throws SQLException {
        return (Grupo)DAO_PA.getTableDAO("params", "grupo", id, Grupo.class).get(0);
    }
// </editor-fold>  
// <editor-fold defaultstate="collapsed" desc="Accessors">
    public String getNombre_id() {
        return nombre_id;
    }
    public void setNombre_id(String nombre_id) {
        this.nombre_id = nombre_id;
    }
    
    public Intereses[] getIntereses() {
		return intereses;
	}

	public void setIntereses(Intereses[] intereses) {
		this.intereses = intereses;
	}

	// </editor-fold>   
    public ArrayList<Grupo> filler(ResultSet rs) throws SQLException {
        ArrayList<Grupo> vec = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
        while (rs.next()) {
        	Intereses[] interests = null;
			try {
				String json = rs.getString(2);
				if (json != null) {
					interests = mapper.readValue(rs.getString(2), Intereses[].class);
				}
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            vec.add(new Grupo(rs.getString(1),interests));
        }
        rs.close();
        System.gc();
        return vec;
    }

    @Override
	public Object[] rip() {
		Object[] data=new Object[1];
        data[0] = nombre_id;
		return data;
	}
}


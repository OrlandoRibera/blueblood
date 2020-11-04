package com.sd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * SPRING - BEAN
 * @author BlueFox - SD
 */
public class Intereses implements IDAO{
// <editor-fold defaultstate="collapsed" desc="Atributos">
	@JsonProperty(access = Access.WRITE_ONLY)
	private String grupo_id;
    private String nombre_id;
    @JsonProperty(access = Access.WRITE_ONLY)
    private String descripcion;
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores + toString">
    public Intereses() {
    }
    public Intereses(String grupo_id) {
        this.grupo_id = grupo_id;
    }
    public Intereses(String grupo_id, String nombre_id, String descripcion) {
        this.grupo_id = grupo_id;
        this.nombre_id = nombre_id;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
    return grupo_id +", "+nombre_id +", "+descripcion ;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="CRUD">   
    @Override
    public long insert() throws SQLException {
        return DAO_PA.insertRecord("public","intereses", this);
    }

    @Override
    public long update() throws SQLException {
        return DAO_PA.updateRecord("public","intereses", this);
    }

    @Override
    public long delete() throws SQLException {
        return DAO_PA.deleteRecord("public","intereses", this);
    }
    
    public static ArrayList<Intereses> getAllRecords(int limit) throws SQLException {
            return DAO_PA.getTableDAO("public","intereses",limit,Intereses.class);
    }
    public static Intereses getRecord(int id) throws SQLException {
        return (Intereses)DAO_PA.getTableDAO("public", "intereses", id, Intereses.class).get(0);
    }
// </editor-fold>  
// <editor-fold defaultstate="collapsed" desc="Accessors">
    public String getGrupo_id() {
        return grupo_id;
    }
    public void setGrupo_id(String grupo_id) {
        this.grupo_id = grupo_id;
    }

    public String getNombre_id() {
        return nombre_id;
    }
    public void setNombre_id(String nombre_id) {
        this.nombre_id = nombre_id;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

// </editor-fold>   
    public ArrayList<Intereses> filler(ResultSet rs) throws SQLException {
        ArrayList<Intereses> vec = new ArrayList<>();
        while (rs.next()) {
            vec.add(new Intereses(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
        rs.close();
        System.gc();
        return vec;
    }

    @Override
	public Object[] rip() {
		Object[] data=new Object[3];
        data[0] = grupo_id;
        data[1] = nombre_id;
        data[2] = descripcion;
		return data;
	}
}


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
public class Usuarios_intereses implements IDAO{
// <editor-fold defaultstate="collapsed" desc="Atributos">
    @JsonProperty(access = Access.WRITE_ONLY)
	private int usuario_id;
    private String grupo_id;
    private String nombre_id;
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores + toString">
    public Usuarios_intereses() {
    }
    public Usuarios_intereses(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    public Usuarios_intereses(int usuario_id, String grupo_id, String nombre_id) {
        this.usuario_id = usuario_id;
        this.grupo_id = grupo_id;
        this.nombre_id = nombre_id;
    }

    @Override
    public String toString() {
    return usuario_id +", "+grupo_id +", "+nombre_id ;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="CRUD">   
    @Override
    public long insert() throws SQLException {
        return DAO_PA.insertRecord("public","usuarios_intereses", this);
    }

    @Override
    public long update() throws SQLException {
        return DAO_PA.updateRecord("public","usuarios_intereses", this);
    }

    @Override
    public long delete() throws SQLException {
        return DAO_PA.deleteRecord("public","usuarios_intereses", this);
    }
    
    public static ArrayList<Usuarios_intereses> getAllRecords(int limit) throws SQLException {
            return DAO_PA.getTableDAO("public","usuarios_intereses",limit,Usuarios_intereses.class);
    }
    public static Usuarios_intereses getRecord(int id) throws SQLException {
        return (Usuarios_intereses)DAO_PA.getTableDAO("public", "usuarios_intereses", id, Usuarios_intereses.class).get(0);
    }
// </editor-fold>  
// <editor-fold defaultstate="collapsed" desc="Accessors">
    public int getUsuario_id() {
        return usuario_id;
    }
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

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

// </editor-fold>   
    public ArrayList<Usuarios_intereses> filler(ResultSet rs) throws SQLException {
        ArrayList<Usuarios_intereses> vec = new ArrayList<>();
        while (rs.next()) {
            vec.add(new Usuarios_intereses(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        rs.close();
        System.gc();
        return vec;
    }

    @Override
	public Object[] rip() {
		Object[] data=new Object[3];
        data[0] = usuario_id;
        data[1] = grupo_id;
        data[2] = nombre_id;
		return data;
	}
}


package com.sd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SPRING - BEAN
 * @author BlueFox - SD
 */
public class Globulos implements IDAO{
// <editor-fold defaultstate="collapsed" desc="Atributos">
    private int usuario_id;
    private long post_id;
    private String fecha;
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores + toString">
    public Globulos() {
    }
    public Globulos(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    public Globulos(int usuario_id, long post_id, String fecha) {
        this.usuario_id = usuario_id;
        this.post_id = post_id;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
    return usuario_id +", "+post_id +", "+fecha ;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="CRUD">   
    @Override
    public long insert() throws SQLException {
        return DAO_PA.insertRecord("public","globulos", this);
    }

    @Override
    public long update() throws SQLException {
        return DAO_PA.updateRecord("public","globulos", this);
    }

    @Override
    public long delete() throws SQLException {
        return DAO_PA.deleteRecord("public","globulos", this);
    }
    
    public static ArrayList<Globulos> getAllRecords(int limit) throws SQLException {
            return DAO_PA.getTableDAO("public","globulos",limit,Globulos.class);
    }
    public static Globulos getRecord(int id) throws SQLException {
        return (Globulos)DAO_PA.getTableDAO("public", "globulos", id, Globulos.class).get(0);
    }
// </editor-fold>  
// <editor-fold defaultstate="collapsed" desc="Accessors">
    public int getUsuario_id() {
        return usuario_id;
    }
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public long getPost_id() {
        return post_id;
    }
    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

// </editor-fold>   
    public ArrayList<Globulos> filler(ResultSet rs) throws SQLException {
        ArrayList<Globulos> vec = new ArrayList<>();
        while (rs.next()) {
            vec.add(new Globulos(rs.getInt(1), rs.getLong(2), rs.getString(3)));
        }
        rs.close();
        System.gc();
        return vec;
    }

    @Override
	public Object[] rip() {
		Object[] data=new Object[3];
        data[0] = usuario_id;
        data[1] = post_id;
        data[2] = fecha;
		return data;
	}
}


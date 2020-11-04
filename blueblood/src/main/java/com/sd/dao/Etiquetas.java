package com.sd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SPRING - BEAN
 * @author BlueFox - SD
 */
public class Etiquetas implements IDAO{
// <editor-fold defaultstate="collapsed" desc="Atributos">
    private int usuario_id;
    private String usuario_nm;
    private transient String usuario_mail;
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores + toString">
    public Etiquetas() {
    }
    public Etiquetas(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Etiquetas(int usuario_id, String usuario_nm) {
		this.usuario_id = usuario_id;
		this.usuario_nm = usuario_nm;
	}
    
	public Etiquetas(int usuario_id, String usuario_nm, String usuario_mail) {
		super();
		this.usuario_id = usuario_id;
		this.usuario_nm = usuario_nm;
		this.usuario_mail = usuario_mail;
	}
	@Override
    public String toString() {
    return usuario_id +", "+usuario_nm ;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="CRUD">   
    @Override
    public long insert() throws SQLException {
        return DAO_PA.insertRecord("public","etiquetas", this);
    }

    @Override
    public long update() throws SQLException {
        return DAO_PA.updateRecord("public","etiquetas", this);
    }

    @Override
    public long delete() throws SQLException {
        return DAO_PA.deleteRecord("public","etiquetas", this);
    }
    
    public static ArrayList<Etiquetas> getAllRecords(int limit) throws SQLException {
            return DAO_PA.getTableDAO("public","etiquetas",limit,Etiquetas.class);
    }
    public static Etiquetas getRecord(int id) throws SQLException {
        return (Etiquetas)DAO_PA.getTableDAO("public", "etiquetas", id, Etiquetas.class).get(0);
    }
// </editor-fold>  
// <editor-fold defaultstate="collapsed" desc="Accessors">
    public int getUsuario_id() {
        return usuario_id;
    }
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsuario_nm() {
		return usuario_nm;
	}
    
	public void setUsuario_nm(String usuario_nm) {
		this.usuario_nm = usuario_nm;
	}
	
	
	public String getUsuario_mail() {
		return usuario_mail;
	}
	public void setUsuario_mail(String usuario_mail) {
		this.usuario_mail = usuario_mail;
	}
	// </editor-fold>   
    public ArrayList<Etiquetas> filler(ResultSet rs) throws SQLException {
        ArrayList<Etiquetas> vec = new ArrayList<>();
        while (rs.next()) {
            vec.add(new Etiquetas(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        rs.close();
        System.gc();
        return vec;
    }

    @Override
	public Object[] rip() {
		Object[] data=new Object[2];
        data[0] = usuario_id;
        data[1] = usuario_nm;
		return data;
	}
}


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
public class Comentario implements IDAO{
// <editor-fold defaultstate="collapsed" desc="Atributos">
    private long comentario_id;
	private int usuario_id;
    @JsonProperty(access = Access.WRITE_ONLY)
    private long post_id;
    private String fecha;
    private String comentario;
    private transient String usuario_nm;
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores + toString">
    public Comentario() {
    }
    
    public Comentario(long comentario_id) {
        this.comentario_id=comentario_id;
    }

    public Comentario(long comentario_id, int usuario_id, String fecha, String comentario,
			String usuario_nm) {
		this.comentario_id = comentario_id;
		this.usuario_id = usuario_id;
		this.fecha = fecha;
		this.comentario = comentario;
		this.usuario_nm = usuario_nm;
	}
    
	@Override
    public String toString() {
    return usuario_id +", "+post_id +", "+fecha ;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="CRUD">   
    @Override
    public long insert() throws SQLException {
        return DAO_PA.insertRecord("public","comentarios", this);
    }

    @Override
    public long update() throws SQLException {
        return DAO_PA.updateRecord("public","comentarios", this);
    }

    @Override
    public long delete() throws SQLException {
        return DAO_PA.deleteRecord("public","comentarios", this);
    }
    
    @SuppressWarnings("unchecked")
	public static ArrayList<Comentario> getAllRecords(int limit) throws SQLException {
            return DAO_PA.getTableDAO("public","comentarios",limit,Comentario.class);
    }
    public static Comentario getRecord(int id) throws SQLException {
        return (Comentario)DAO_PA.getTableDAO("public", "comentarios", id, Comentario.class).get(0);
    }
// </editor-fold>  
// <editor-fold defaultstate="collapsed" desc="Accessors">

    
    public int getUsuario_id() {
        return usuario_id;
    }
    public long getComentario_id() {
		return comentario_id;
	}
	public void setComentario_id(long comentario_id) {
		this.comentario_id = comentario_id;
	}
	public String getUsuario_nm() {
		return usuario_nm;
	}
	public void setUsuario_nm(String usuario_nm) {
		this.usuario_nm = usuario_nm;
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

    public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	// </editor-fold>   
    public ArrayList<Comentario> filler(ResultSet rs) throws SQLException {
        ArrayList<Comentario> vec = new ArrayList<>();
        while (rs.next()) {
            vec.add(new Comentario(rs.getLong(1),rs.getInt(2),  rs.getString(3), rs.getString(4),rs.getString(5)));
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


package com.sd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * SPRING - BEAN
 * 
 * @author BlueFox - SD
 */
public class Pulsos implements IDAO {
// <editor-fold defaultstate="collapsed" desc="Atributos">
	private int usuario_id;
	private long post_id;
	private String fecha;
	private String tipo;

	// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores + toString">
	public Pulsos() {
	}

	public Pulsos(int usuario_id) {
		this.usuario_id = usuario_id;
	}

	public Pulsos(int usuario_id, long post_id, String fecha, String tipo) {
		this.usuario_id = usuario_id;
		this.post_id = post_id;
		this.fecha = fecha;
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return usuario_id + ", " + post_id + ", " + fecha + ", " + tipo;
	}

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="CRUD">   
	@Override
	public long insert() throws SQLException {
		return DAO_PA.insertRecord("public", "pulsos", this);
	}

	public Map<String, String> insertPulso() throws SQLException {
		ResultSet rs = DAO_PA.getStoredProcedure("public", "mk_", "pulsos", "" + usuario_id, "" + post_id, tipo);
		rs.next();
		Map<String, String> parametros = new HashMap<>();
		parametros.put("usuario_mail", rs.getString(1));
		parametros.put("jefe_nm", rs.getString(2));
		parametros.put("tipo", rs.getString(3).toUpperCase());
		parametros.put("grupo", rs.getString(4));
		rs.close();
		return parametros;
	}

	@Override
	public long update() throws SQLException {
		return DAO_PA.updateRecord("public", "pulsos", this);
	}

	@Override
	public long delete() throws SQLException {
		return DAO_PA.deleteRecord("public", "pulsos", this);
	}

	public static ArrayList<Pulsos> getAllRecords(int limit) throws SQLException {
		return DAO_PA.getTableDAO("public", "pulsos", limit, Pulsos.class);
	}

	public static Pulsos getRecord(int id) throws SQLException {
		return (Pulsos) DAO_PA.getTableDAO("public", "pulsos", id, Pulsos.class).get(0);
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

// </editor-fold>   
	public ArrayList<Pulsos> filler(ResultSet rs) throws SQLException {
		ArrayList<Pulsos> vec = new ArrayList<>();
		while (rs.next()) {
			vec.add(new Pulsos(rs.getInt(1), rs.getLong(2), rs.getString(3), rs.getString(4)));
		}
		rs.close();
		System.gc();
		return vec;
	}

	@Override
	public Object[] rip() {
		Object[] data = new Object[4];
		data[0] = usuario_id;
		data[1] = post_id;
		data[2] = fecha;
		data[3] = tipo;
		return data;
	}
}

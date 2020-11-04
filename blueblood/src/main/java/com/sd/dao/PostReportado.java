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
public class PostReportado implements IDAO {
// <editor-fold defaultstate="collapsed" desc="Atributos">
	private long post_id;
	private int usuario_id;
	private String fecha;
	private String motivo;
	private boolean censurado;
	private transient String usuario_nm;
	private transient String denunciante_nm;

	// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores + toString">
	public PostReportado() {
	}

	public PostReportado(int usuario_id) {
		this.usuario_id = usuario_id;
	}

	public PostReportado(long post_id, int usuario_id, String fecha, String motivo, 
			boolean censurado, String usuario_nm, String denunciante_nm) {
		this.usuario_id = usuario_id;
		this.post_id = post_id;
		this.fecha = fecha;
		this.motivo = motivo;
		this.censurado = censurado;
		this.usuario_nm = usuario_nm;
		this.denunciante_nm=denunciante_nm;
	}

	@Override
	public String toString() {
		return usuario_id + ", " + post_id + ", " + fecha + ", " + motivo;
	}

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="CRUD">   
	@Override
	public long insert() throws SQLException {
		return DAO_PA.insertRecord("public", "posts_reportados", this);
	}

	@Override
	public long update() throws SQLException {
		return DAO_PA.updateRecord("public", "posts_reportados", this);
	}

	@Override
	public long delete() throws SQLException {
		return DAO_PA.deleteRecord("public", "posts_reportados", this);
	}

	public static ArrayList<PostReportado> getAllRecords(int limit) throws SQLException {
		return DAO_PA.getTableDAO("public", "posts_reportados", limit, PostReportado.class);
	}

	public static PostReportado getRecord(int id) throws SQLException {
		return (PostReportado) DAO_PA.getTableDAO("public", "posts_reportados", id, PostReportado.class).get(0);
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

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public boolean isCensurado() {
		return censurado;
	}

	public void setCensurado(boolean censurado) {
		this.censurado = censurado;
	}

	public String getUsuario_nm() {
		return usuario_nm;
	}

	public void setUsuario_nm(String usuario_nm) {
		this.usuario_nm = usuario_nm;
	}

	public String getDenunciante_nm() {
		return denunciante_nm;
	}

	public void setDenunciante_nm(String denunciante_nm) {
		this.denunciante_nm = denunciante_nm;
	}

	// </editor-fold>
	public ArrayList<PostReportado> filler(ResultSet rs) throws SQLException {
		ArrayList<PostReportado> vec = new ArrayList<>();
		while (rs.next()) {
			vec.add(new PostReportado(rs.getLong(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getBoolean(5),
					rs.getString(6),rs.getString(7)));
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
		data[3] = motivo;
		return data;
	}
}

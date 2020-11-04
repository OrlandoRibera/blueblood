package com.sd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * SPRING - BEAN
 * 
 * @author BlueFox - SD
 */
@SuppressWarnings("rawtypes")
public class Posts implements IDAO {
// <editor-fold defaultstate="collapsed" desc="Atributos">
	private long codigo_id;
	private String fecha;
	private int usuario_id;
	private String mensaje;
	private String tipo;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String path_media;
	private int destino_id;
	private transient String usuario_nm;
	private transient int globulos;
	private transient int pulsos;
	private transient String desc_fecha;
	private transient boolean globuleado;
	private transient boolean pulseado;
	@JsonRawValue
	private transient String comments;
	private transient boolean isJefe;
	@JsonRawValue
	private transient String pulsos_detalle;
	private transient String destino_nm;
	@JsonRawValue
	private transient String etiquetados;
	@JsonProperty(access = Access.WRITE_ONLY)
	private transient String tipo_pulso;
	@JsonProperty(access = Access.WRITE_ONLY)
	private transient int[] etiquetas;
	

	// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores + toString">
	public Posts() {
	}

	public Posts(long codigo_id) {
		this.codigo_id = codigo_id;
	}

	public Posts(long codigo_id, String fecha, int usuario_id, String mensaje, String tipo, String path_media) {
		this.codigo_id = codigo_id;
		this.fecha = fecha;
		this.usuario_id = usuario_id;
		this.mensaje = mensaje;
		this.tipo = tipo;
		this.path_media = path_media;
	}

	public Posts(long codigo_id, Timestamp fecha, int usuario_id, String mensaje, String tipo, String path_media) {
		this.codigo_id = codigo_id;
		this.fecha = fecha.toString();
		this.usuario_id = usuario_id;
		this.mensaje = mensaje;
		this.tipo = tipo;
		this.path_media = path_media;
		System.out.println(fecha);
		PrettyTime pt = new PrettyTime(new Locale("es"));
		this.desc_fecha = pt.format(fecha);
	}

	public Posts(long codigo_id, Timestamp fecha, int usuario_id, String mensaje, String tipo, String path_media,
			String usuario_nm, int globulos, int pulsos, boolean globea, boolean pulsea, String comments,
			boolean isJefe, String pulsos_detalle, int destino_id, String destino_nm,String etiquetados) throws SQLException {
		this.codigo_id = codigo_id;
		this.fecha = fecha.toString();
		this.usuario_id = usuario_id;
		this.mensaje = mensaje;
		this.tipo = tipo;
		this.path_media = path_media;
		this.usuario_nm = usuario_nm;
		this.pulsos = pulsos;
		this.globulos = globulos;
		PrettyTime pt = new PrettyTime(new Locale("es"));
		this.desc_fecha = pt.format(fecha);
		this.pulseado = pulsea;
		this.globuleado = globea;
		this.comments = comments;
		this.isJefe = isJefe;
		this.pulsos_detalle = pulsos_detalle;
		this.destino_id = destino_id;
		this.destino_nm = destino_nm;
		this.etiquetados=etiquetados;
	}

	@Override
	public String toString() {
		return codigo_id + ", " + fecha + ", " + usuario_id + ", " + mensaje + ", " + tipo + ", " + path_media;
	}

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="CRUD">   
	@Override
	public long insert() throws SQLException {
		return DAO_PA.insertRecord("public", "posts", this);
	}

	@Override
	public long update() throws SQLException {
		return DAO_PA.updateRecord("public", "posts", this);
	}

	@Override
	public long delete() throws SQLException {
		return DAO_PA.deleteRecord("public", "posts", this);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Posts> getAllRecords(int id) throws SQLException {
		return DAO_PA.getTableDAO("public", "posts", id, Posts.class);
	}

	public static ArrayList<Posts> getRecordsbyUser(int id) throws SQLException {
		return DAO_PA.getTableDAO("public", "postsbyuser", id, Posts.class);
	}

	public static ArrayList<Posts> getRecordsbyUser(int id, int usuario_id) throws SQLException {
		ResultSet rs = DAO_PA.getStoredProcedure("public", "get_", "postsbyuser", "" + id, "" + usuario_id);
		ArrayList<Posts> posts = new Posts().filler(rs);
		rs.close();
		return posts;
	}

	public static Posts getRecord(int id) throws SQLException {
		return (Posts) DAO_PA.getTableDAO("public", "posts", id, Posts.class).get(0);
	}

	public static ArrayList<Posts> getRecord(long id, int user) throws SQLException {
		ResultSet rs = DAO_PA.getStoredProcedure("public", "get_", "posts", "" + id, "" + user);
		ArrayList<Posts> data = new Posts().filler(rs);
		rs.close();
		return data;
	}

	public static Posts uploadPic(String user, String path) throws SQLException {
		ResultSet rs;
		Posts usuario = null;
		rs = DAO_PA.getStoredProcedure("public", "pic_", "posts", user, path);
		if (rs.next()) {
			usuario = getRecord(rs.getInt(1));
		}
		rs.close();
		return usuario;
	}

	public static String getProfilePic(String user_id) throws SQLException {
		ResultSet rs;
		String usuario = null;
		rs = DAO_PA.getStoredProcedure("public", "path_", "posts", user_id);
		if (rs.next()) {
			usuario = rs.getString(1);
		}
		rs.close();
		return usuario;
	}

	public ArrayList<Etiquetas> addTags() throws SQLException {
		ArrayList<Etiquetas> et = null;
		if (codigo_id != 0 && etiquetas!=null) {
			ResultSet rs;
			System.out.println(Arrays.toString(etiquetas));
			rs = DAO_PA.getStoredProcedure("public", "mk_", "etiquetas", "" + codigo_id, Arrays.toString(etiquetas).replace("[","{").replace("]", "}"));
			et = new Etiquetas().filler(rs);
			rs.close();
		}
		return et;
	}

// </editor-fold>  
// <editor-fold defaultstate="collapsed" desc="Accessors">
	public long getCodigo_id() {
		return codigo_id;
	}

	public void setCodigo_id(long codigo_id) {
		this.codigo_id = codigo_id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPath_media() {
		return path_media;
	}

	public void setPath_media(String path_media) {
		this.path_media = path_media;
	}

	public String getDesc_fecha() {
		return desc_fecha;
	}

	public void setDesc_fecha(String desc_fecha) {
		this.desc_fecha = desc_fecha;
	}

	public String getUsuario_nm() {
		return usuario_nm;
	}

	public void setUsuario_nm(String usuario_nm) {
		this.usuario_nm = usuario_nm;
	}

	public int getPulsos() {
		return pulsos;
	}

	public void setPulsos(int pulsos) {
		this.pulsos = pulsos;
	}

	public int getGlobulos() {
		return globulos;
	}

	public void setGlobulos(int globulos) {
		this.globulos = globulos;
	}

	public boolean isPulseado() {
		return pulseado;
	}

	public void setPulseado(boolean pulseado) {
		this.pulseado = pulseado;
	}

	public boolean isGlobuleado() {
		return globuleado;
	}

	public void setGlobuleado(boolean globuleado) {
		this.globuleado = globuleado;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isJefe() {
		return isJefe;
	}

	public void setJefe(boolean isJefe) {
		this.isJefe = isJefe;
	}

	public String getPulsos_detalle() {
		return pulsos_detalle;
	}

	public void setPulsos_detalle(String pulsos_detalle) {
		this.pulsos_detalle = pulsos_detalle;
	}

	public int getDestino_id() {
		return destino_id;
	}

	public void setDestino_id(int destino_id) {
		this.destino_id = destino_id;
	}

	public String getDestino_nm() {
		return destino_nm;
	}

	public void setDestino_nm(String destino_nm) {
		this.destino_nm = destino_nm;
	}

	public String getTipo_pulso() {
		return tipo_pulso;
	}

	public void setTipo_pulso(String tipo_pulso) {
		this.tipo_pulso = tipo_pulso;
	}
	
	

	public String getEtiquetados() {
		return etiquetados;
	}

	public void setEtiquetados(String etiquetados) {
		this.etiquetados = etiquetados;
	}

	public int[] getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(int[] etiquetas) {
		this.etiquetas = etiquetas;
	}

	// </editor-fold>
	public ArrayList<Posts> filler(ResultSet rs) throws SQLException {
		ArrayList<Posts> vec = new ArrayList<>();
		while (rs.next()) {

			vec.add(new Posts(rs.getLong(1), rs.getTimestamp(2), rs.getInt(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getBoolean(10), rs.getBoolean(11),
					rs.getString(12), rs.getBoolean(13), rs.getString(14), rs.getInt(15), rs.getString(16), rs.getString(17)));
		}
		rs.close();
		System.gc();
		return vec;
	}

	@Override
	public Object[] rip() {
		Object[] data = new Object[6];
		data[0] = codigo_id;
		data[1] = fecha;
		data[2] = usuario_id;
		data[3] = mensaje;
		data[4] = tipo;
		data[5] = path_media;
		return data;
	}
}

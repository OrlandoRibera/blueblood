package com.sd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * SPRING - BEAN
 * 
 * @author BlueFox - SD
 */
public class Usuarios implements IDAO {
// <editor-fold defaultstate="collapsed" desc="Atributos">
	private int codigo_id;
	private String nombre;
	private String nick;
	private String correo;
	private String sucursal_id;
	private String area_id;
	private String cargo_id;
	private String ciudad_id;
	private String dtnacimiento;
	private String dtcontrato;
	private int jefe_id;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String spassword;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String path_foto;
	private boolean moderador;
	private transient int total_globulos;
	private transient int total_pulsos;
	@JsonRawValue
	private transient String detalle_pulsos;

	// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores + toString">
	public Usuarios() {
	}

	public Usuarios(int codigo_id) {
		this.codigo_id = codigo_id;
	}

	public Usuarios(int codigo_id, String nombre, String nick, String correo, String sucursal_id, String area_id,
			String cargo_id, String ciudad_id, String dtnacimiento, String dtcontrato, int jefe_id) {
		this.codigo_id = codigo_id;
		this.nombre = nombre;
		this.nick = nick;
		this.correo = correo;
		this.sucursal_id = sucursal_id;
		this.area_id = area_id;
		this.cargo_id = cargo_id;
		this.ciudad_id = ciudad_id;
		this.dtnacimiento = dtnacimiento;
		this.dtcontrato = dtcontrato;
		this.jefe_id = jefe_id;
	}

	public Usuarios(int codigo_id, String nombre, String nick, String correo, String sucursal_id, String area_id,
			String cargo_id, String ciudad_id, String dtnacimiento, String dtcontrato, int jefe_id, String spassword,
			String path_foto) {
		this.codigo_id = codigo_id;
		this.nombre = nombre;
		this.nick = nick;
		this.correo = correo;
		this.sucursal_id = sucursal_id;
		this.area_id = area_id;
		this.cargo_id = cargo_id;
		this.ciudad_id = ciudad_id;
		this.dtnacimiento = dtnacimiento;
		this.dtcontrato = dtcontrato;
		this.jefe_id = jefe_id;
		this.spassword = spassword;
		this.path_foto = path_foto;
	}

	public Usuarios(int codigo_id, String nombre, String nick, String correo, String sucursal_id, String area_id,
			String cargo_id, String ciudad_id, String dtnacimiento, String dtcontrato, int jefe_id, String spassword,
			String path_foto, boolean moderador, int total_globulos, int total_pulsos, String detalle_pulsos) {
		this.codigo_id = codigo_id;
		this.nombre = nombre;
		if (nick != null && !nick.isEmpty()) {
			this.nick = nick;
		} else {
			this.nick = nombre;
		}
		this.correo = correo;
		this.sucursal_id = sucursal_id;
		this.area_id = area_id;
		this.cargo_id = cargo_id;
		this.ciudad_id = ciudad_id;
		this.dtnacimiento = dtnacimiento;
		this.dtcontrato = dtcontrato;
		this.jefe_id = jefe_id;
		this.spassword = spassword;
		this.path_foto = path_foto;
		this.moderador = moderador;
		this.total_globulos = total_globulos;
		this.total_pulsos = total_pulsos;
		this.detalle_pulsos = detalle_pulsos;
	}
	
	public Usuarios(int codigo_id, String nombre, String nick, String correo, String sucursal_id, String area_id,
			String cargo_id, String ciudad_id, String dtnacimiento, String dtcontrato, int jefe_id, String spassword,
			String path_foto, boolean moderador, int total_globulos, int total_pulsos, String detalle_pulsos,boolean whatever) {
		this.codigo_id = codigo_id;
		this.nombre = nombre;
		this.nick = nick;
		this.correo = correo;
		this.sucursal_id = sucursal_id;
		this.area_id = area_id;
		this.cargo_id = cargo_id;
		this.ciudad_id = ciudad_id;
		this.dtnacimiento = dtnacimiento;
		this.dtcontrato = dtcontrato;
		this.jefe_id = jefe_id;
		this.spassword = spassword;
		this.path_foto = path_foto;
		this.moderador = moderador;
		this.total_globulos = total_globulos;
		this.total_pulsos = total_pulsos;
		this.detalle_pulsos = detalle_pulsos;
	}

	@Override
	public String toString() {
		return codigo_id + ", " + nombre + ", " + nick + ", " + correo + ", " + sucursal_id + ", " + area_id + ", "
				+ cargo_id + ", " + ciudad_id + ", " + dtnacimiento + ", " + dtcontrato + ", " + jefe_id;
	}

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="CRUD">   
	@Override
	public long insert() throws SQLException {
		codigo_id = (int) DAO_PA.insertRecord("public", "usuarios", this);
		return codigo_id;
	}

	@Override
	public long update() throws SQLException {
		return DAO_PA.updateRecord("public", "usuarios", this);
	}

	@Override
	public long delete() throws SQLException {
		return DAO_PA.deleteRecord("public", "usuarios", this);
	}

	public static ArrayList<Usuarios> getAllRecords(int limit) throws SQLException {
		return DAO_PA.getTableDAO("public", "usuarios", limit, Usuarios.class);
	}

	public static ArrayList<Map<String, String>> getAllShort() throws SQLException {
		ArrayList<Map<String, String>> array = new ArrayList<>();
		ArrayList<Usuarios> data = DAO_PA.getTableDAO("public", "usuarios", 0, Usuarios.class);
		for (Usuarios usuario : data) {
			Map<String, String> obj = new HashMap<>();
			obj.put("nick", usuario.getNick());
			obj.put("codigo_id", "" + usuario.getCodigo_id());
			array.add(obj);
		}
		return array;

	}
	
	

	public static Usuarios getRecord(int id) throws SQLException {
		return (Usuarios) DAO_PA.getTableDAO("public", "usuarios", id, Usuarios.class).get(0);
	}
	public static Usuarios getLoginRecord(int id) throws SQLException {
		ResultSet rs = DAO_PA.getStoredProcedure("public", "get_", "usuarios", ""+id,"");
		return  new Usuarios().patchfiller(rs);
	}

	public static ArrayList<Usuarios> searchUsers(String pattern) throws SQLException {
		ResultSet rs = DAO_PA.getStoredProcedure("public", "get_", "usuarios", pattern);
		ArrayList<Usuarios> users = new Usuarios().filler(rs);
		rs.close();
		return users;
	}

	public static Usuarios login(String user, String pass) throws SQLException {
		ResultSet rs;
		Usuarios usuario = null;
		rs = DAO_PA.getStoredProcedure("public", "vfy_", "usuarios", user, pass);
		if (rs.next()) {
			usuario = getLoginRecord(rs.getInt(1));
		}
		rs.close();
		return usuario;
	}

	public static Usuarios uploadPic(String user, String path) throws SQLException {
		ResultSet rs;
		Usuarios usuario = null;
		rs = DAO_PA.getStoredProcedure("public", "pic_", "usuarios", user, path);
		if (rs.next()) {
			usuario = getRecord(rs.getInt(1));
		}
		rs.close();
		return usuario;
	}
	
	public static Usuarios updateNick(String user, String nick) throws SQLException {
		ResultSet rs;
		Usuarios usuario = null;
		rs = DAO_PA.getStoredProcedure("public", "upd_", "usuarios", user, nick);
		if (rs.next()) {
			usuario = getRecord(rs.getInt(1));
		}
		rs.close();
		return usuario;
	}

	public static String getProfilePic(String user_id) throws SQLException {
		ResultSet rs;
		String usuario = null;
		rs = DAO_PA.getStoredProcedure("public", "path_", "usuarios", user_id);
		if (rs.next()) {
			usuario = rs.getString(1);
		}
		rs.close();
		return usuario;
	}
	
	public static boolean getJefe(int user_id, int jefe_id) throws SQLException {
		ResultSet rs;
		boolean usuario = false;
		rs = DAO_PA.getStoredProcedure("public", "es", "jefe", ""+user_id, ""+jefe_id);
		if (rs.next()) {
			usuario=rs.getBoolean((1));
		}
		rs.close();
		return usuario;
	}

// </editor-fold>  
// <editor-fold defaultstate="collapsed" desc="Accessors">
	public int getCodigo_id() {
		return codigo_id;
	}

	public void setCodigo_id(int codigo_id) {
		this.codigo_id = codigo_id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getSucursal_id() {
		return sucursal_id;
	}

	public void setSucursal_id(String sucursal_id) {
		this.sucursal_id = sucursal_id;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getCargo_id() {
		return cargo_id;
	}

	public void setCargo_id(String cargo_id) {
		this.cargo_id = cargo_id;
	}

	public String getCiudad_id() {
		return ciudad_id;
	}

	public void setCiudad_id(String ciudad_id) {
		this.ciudad_id = ciudad_id;
	}

	public String getDtnacimiento() {
		return dtnacimiento;
	}

	public void setDtnacimiento(String dtnacimiento) {
		this.dtnacimiento = dtnacimiento;
	}

	public String getDtcontrato() {
		return dtcontrato;
	}

	public void setDtcontrato(String dtcontrato) {
		this.dtcontrato = dtcontrato;
	}

	public int getJefe_id() {
		return jefe_id;
	}

	public void setJefe_id(int jefe_id) {
		this.jefe_id = jefe_id;
	}

	public String getSpassword() {
		return spassword;
	}

	public void setSpassword(String spassword) {
		this.spassword = spassword;
	}

	public String getPath_foto() {
		return path_foto;
	}

	public void setPath_foto(String path_foto) {
		this.path_foto = path_foto;
	}

	public int getTotal_globulos() {
		return total_globulos;
	}

	public void setTotal_globulos(int total_globulos) {
		this.total_globulos = total_globulos;
	}

	public int getTotal_pulsos() {
		return total_pulsos;
	}

	public void setTotal_pulsos(int total_pulsos) {
		this.total_pulsos = total_pulsos;
	}

	public String getDetalle_pulsos() {
		return detalle_pulsos;
	}

	public void setDetalle_pulsos(String detalle_pulsos) {
		this.detalle_pulsos = detalle_pulsos;
	}

	public boolean isModerador() {
		return moderador;
	}

	public void setModerador(boolean moderador) {
		this.moderador = moderador;
	}

	// </editor-fold>
	public ArrayList<Usuarios> filler(ResultSet rs) throws SQLException {
		ArrayList<Usuarios> vec = new ArrayList<>();
		while (rs.next()) {
			vec.add(new Usuarios(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11),
					"", rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getInt(16), rs.getString(17)));
		}
		rs.close();
		System.gc();
		return vec;
	}
	public Usuarios patchfiller(ResultSet rs) throws SQLException {
		Usuarios vec = null;
		if (rs.next()) {
			vec=new Usuarios(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11),
					"", rs.getString(13), rs.getBoolean(14), rs.getInt(15), rs.getInt(16), rs.getString(17),false);
		}
		rs.close();
		System.gc();
		return vec;
	}

	@Override
	public Object[] rip() {
		Object[] data = new Object[11];
		data[0] = codigo_id;
		data[1] = nombre;
		data[2] = nick;
		data[3] = correo;
		data[4] = sucursal_id;
		data[5] = area_id;
		data[6] = cargo_id;
		data[7] = ciudad_id;
		data[8] = dtnacimiento;
		data[9] = dtcontrato;
		data[10] = jefe_id;
		return data;
	}
}

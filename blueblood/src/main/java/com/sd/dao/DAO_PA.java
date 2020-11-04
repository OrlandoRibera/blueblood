/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.dao;

import com.sd.conexion.Conexion;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author BlueFox
 */
public abstract class DAO_PA {
	
	public static final String DB_SCHEMA_SECURITY = "lock";
	public static final String DB_SCHEMA_LITEPOS = "litepos";
	public static final String DB_SCHEMA_DRACMA = "dracma";
	public static final String DB_SCHEMA_PARAMETERS = "parameters";

	private static long makeRecord(String estructura, String tabla, Object dto) throws SQLException {
		Conexion con = Conexion.autoConnect();
		PreparedStatement stmt = null;
		String statement = "SELECT " + estructura + ".MK_" + tabla + " (";
		int omitidos = 0;
		for (Field campo : dto.getClass().getDeclaredFields()) {
			if (Modifier.isTransient(campo.getModifiers())) {
				omitidos++;
			}
		}
		Field[] campos = dto.getClass().getDeclaredFields();
		stmt = con.getCon().prepareStatement(prepareSQL(dto.getClass().getDeclaredFields().length - omitidos, statement));
		long value = con.executeSQL(fillSQL(stmt, dto));
		stmt.close();
		return value;
	}

	/**
	 * Inserci&oacute;n de Registros
	 *
	 * @param tabla
	 *            Nombre de la Tabla donde se va a ingresar el registro
	 * @param datos
	 *            Array de todos los datos que se ingresan a la tabla
	 * @return Devuelve el estado true o false indicando si el registro se
	 *         ingres&oacute; correctamente
	 * @throws SQLException
	 */
	protected static long insertRecord(String estructura, String tabla, Object objeto) throws SQLException {
		return makeRecord(estructura, tabla, objeto);
	}

	/**
	 * Actualizaci&oacute;n de Registros
	 *
	 * @param tabla
	 *            Nombre de la Tabla donde se va a ingresar el registro
	 * @param datos
	 *            Array de todos los datos que cuenta la tabla
	 * @return Devuelve el estado true o false indicando si el registro se
	 *         actualiz&oacute; correctamente
	 * @throws SQLException
	 */
	protected static long updateRecord(String estructura, String tabla, Object objeto) throws SQLException {
		return makeRecord(estructura, tabla, objeto);
	}

	protected static long deleteRecord(String estructura, String tabla, Object dto) throws SQLException {
		try {
			Conexion con = Conexion.getConnection();
			PreparedStatement stmt = null;
			String statement = "";
			statement = "SELECT " + estructura + ".DEL_" + tabla + " (?)";
			Field campo = dto.getClass().getDeclaredFields()[0];
			Field user = null;

			// user = dto.getClass().getDeclaredField("access_id");

			campo.setAccessible(true);
			// user.setAccessible(true);
			// System.out.println(user.getName());
			stmt = con.getCon().prepareStatement(statement);
			stmt.setObject(1, campo.get(dto));
			// stmt.setObject(2, user.get(dto));
			long value = con.executeSQL(stmt);
			stmt.close();
			return value;

		} catch (IllegalArgumentException ex) {
			Logger.getLogger(DAO_PA.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(DAO_PA.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(DAO_PA.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

	private static String prepareSQL(int largo, String statement) {
		for (int i = 0; i < largo; i++) {
			statement += "?,";
		}
		if (largo != 0) {
			statement = statement.substring(0, statement.length() - 1) + ")";
		} else {
			statement += ")";
		}
		// System.out.println(statement);
		return statement;
	}

	public static ArrayList getTableDAO(String estructura, String tabla, int cantidad, Class dto) throws SQLException {
		Conexion con = Conexion.getConnection();
		String statement = "";
		if (cantidad == 0) {
			statement = "SELECT * FROM " + estructura + ".GET_" + tabla + "()";
		} else {
			statement = "SELECT * FROM " + estructura + ".GET_" + tabla + "(" + cantidad + ")";
		}
		
		try (Statement st=con.getStatement()){
			ResultSet rs = st.executeQuery((statement));
			IDAO valor;
			valor = (IDAO) dto.newInstance();
			ArrayList lista = valor.filler(rs);
			con.endTransact();
			return lista;
		} catch (InstantiationException | IllegalAccessException ex) {
			Logger.getLogger(DAO_PA.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}

	public static ArrayList getTableDAO(String estructura, String tabla, String filtro, Class dto) throws SQLException {
		Conexion con = Conexion.getConnection();
		String statement = "";
		statement = "SELECT * FROM " + estructura + ".GET_" + tabla + "('" + filtro + "')";
		ResultSet rs = con.executeSQL(statement);
		try {
			IDAO valor;
			valor = (IDAO) dto.newInstance();
			ArrayList lista = valor.filler(rs);
			con.endTransact();
			return lista;
		} catch (InstantiationException | IllegalAccessException ex) {
			Logger.getLogger(DAO_PA.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static HashMap getHashDAO(String estructura, String tabla, String orden, int cantidad, Object dto)
			throws SQLException {
		Conexion con = Conexion.getConnection();
		String statement = "";
		if (cantidad == 0) {
			statement = "SELECT * FROM " + estructura + ".GET_" + tabla + "()";
		} else {
			statement = "SELECT * FROM " + estructura + ".GET_" + tabla + "(" + cantidad + ")";
		}
		if (!orden.equals("")) {
			statement += " ORDER BY " + orden;
		}
		ResultSet rs = con.executeSQL(statement);
		HashMap lista = null;
		con.endTransact();
		return lista;
	}

	public static ResultSet getStoredProcedure(String esquema, String prefijo, String tabla, String... parametros)
			throws SQLException {
		Conexion con = Conexion.getConnection();
		String statement = "SELECT * FROM " + esquema + "." + prefijo + tabla + "(";
		for (int i = 0; i < parametros.length - 1; i++) {
			statement += "'" + parametros[i] + "',";
		}
		statement += "'" + parametros[parametros.length - 1] + "')";
		// System.out.println(statement);
		ResultSet rs = con.executeSQL(statement);
		return rs;
	}

	/**
	 * Llenar Sentencia SQL
	 *
	 * @param stmt
	 *            PreparedStatement que se desea llenar con los datos
	 * @param datos
	 *            Array de datos que llenar&aacute;n el PreparedStatement
	 * @return Retorna el PreparedStatement con los datos cargados
	 * @throws SQLException
	 */
	private static PreparedStatement fillSQL(PreparedStatement stmt, Object objeto) throws SQLException {
		Field[] datos = objeto.getClass().getDeclaredFields();
		for (int i = 1; i <= datos.length; i++) {
			Field field = datos[i - 1];
			field.setAccessible(true);
			Object elemento = null;
			if (!Modifier.isTransient(field.getModifiers())) {
				try {
					elemento = field.get(objeto);
				} catch (IllegalArgumentException ex) {
					Logger.getLogger(DAO_PA.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IllegalAccessException ex) {
					Logger.getLogger(DAO_PA.class.getName()).log(Level.SEVERE, null, ex);
				}
				if (elemento == null) {
					stmt.setNull(i, java.sql.Types.NULL);
				} else {
					if (elemento.getClass().equals(Date.class)) {
						stmt.setString(i, elemento.toString());
					} else if (elemento.getClass().equals(ArrayList.class)) {
						ArrayList lista = (ArrayList) elemento;

						Object[] data = new Object[lista.size()];
						for (int j = 0; j < lista.size(); j++) {
							data[j] = ((IDAO) lista.get(j)).rip();
						}
						Array arreglo = Conexion.getConnection().getCon().createArrayOf("VARCHAR", data);
						System.out.println(arreglo.toString());
						stmt.setArray(i, arreglo);
					} else {
						stmt.setObject(i, elemento);
					}
				}
			}
		}
		return stmt;
	}
}

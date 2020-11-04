package com.sd.conexion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.sd.blueblood.SQLConnectionProperties;


public abstract class Conexion implements IConexion, Runnable {

	
	
	protected String host, connectionString, database, instance, port, username, password;
	protected Connection con;
	protected static Conexion singleton;
	protected Statement stmnt;
	protected String listener = "";

	public static String SERVER_CONFIG_PATH = System.getProperty("user.home") + "/db_config.ini";

	public String getDatabase() {
		return database;
	}

	public String getHost() {
		return host;
	}

	public String getInstance() {
		return instance;
	}

	public String getPassword() {
		return password;
	}

	public String getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public static Conexion connect(String host, String username, String password, String database, String port)
			throws SQLException {
		singleton = ConexionPSQL.getOrCreate(host, username, password, database, port);
		//autoConnect();
		return singleton;
	}

	public static Conexion autoConnect() throws SQLException {
		// CoreConfig core=CoreConfig.loadConfig();
		// singleton = ConexionPSQL.getOrCreate(core.getDireccionIP(), core.getUser(),
		// core.getPass(), core.getNombreDB(),core.getPort());
		// DESARROLLO - LOCAL - SD
		/*System.out.println(Conexion.SERVER_CONFIG_PATH);
		String host="", db="", port="", user="", pass="";
		if (singleton == null) {
			File config = new File(Conexion.SERVER_CONFIG_PATH);
			if (!config.exists()) {
				try {
					if (config.createNewFile()) {
						FileWriter fw = new FileWriter(config);
						fw.write("host=\nport=\nuser=\npass=\ndatabase=\n");
						fw.flush();
						fw.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				FileReader fr;
				try {
					fr = new FileReader(config);
					BufferedReader br = new BufferedReader(fr);
					host = br.readLine().split("=")[1];
					port = br.readLine().split("=")[1];
					user = br.readLine().split("=")[1];
					pass = br.readLine().split("=")[1];
					db = br.readLine().split("=")[1];
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}*/
		//singleton = ConexionPSQL.getOrCreate(host, user, pass, db, port);

		// PRODUCCION - LOCAL - SD
		 //singleton = ConexionPSQL.getOrCreate("localhost", "soldig", "soldig", "BlueBlood-DEV","5433");

		// AMAZON
		// singleton =
		// ConexionPSQL.getOrCreate("blueblood.cmuy1ymubxvr.sa-east-1.rds.amazonaws.com",
		// "postgres", "TooRRooT", "BlueBlood","5432");
		return singleton;
	}

	public Connection getCon() {
		return con;
	}

	public static Conexion getConnection() {
		return singleton;
	}

	public static void setConnection(Conexion conector) {
		singleton = conector;
	}

	public static void Destroy() throws SQLException {
		singleton.disconnect();
		singleton = null;
		System.gc();
	}

	/*
	 * public void changeListener(String tabla,UIListener interfaz) { try {
	 * Statement stmt = con.createStatement(); if (!listener.isEmpty()) {
	 * stmt.execute("UNLISTEN " + listener); } stmt.execute("LISTEN " + tabla);
	 * listener = tabla; this.interfaz=interfaz; stmt.close(); } catch (SQLException
	 * ex) { Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
	 * } }
	 */

	@Override
	public void run() {
		/*
		 * while (true) { try { Statement stmt = con.createStatement(); ResultSet rs =
		 * stmt.executeQuery("SELECT 1"); rs.close(); stmt.close();
		 * 
		 * org.postgresql.PGNotification notifications[] = ((PGConnection)
		 * con).getNotifications(); if (notifications != null && interfaz!=null) {
		 * interfaz.updateData(); for (PGNotification notification : notifications) {
		 * interfaz.manageData(notification.getParameter()); } } Thread.sleep(500); }
		 * catch (SQLException | InterruptedException sqle) { try {
		 * Conexion.autoConnect(); } catch (SQLException ex) {
		 * System.err.println("ERROR DE SERVICIO, no se puede conectar a la DB"); }
		 * System.err.println("ERROR DEL HILO --> Reiniciando Servicio"); } }
		 */
	}
}

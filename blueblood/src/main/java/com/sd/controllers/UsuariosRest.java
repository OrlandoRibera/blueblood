package com.sd.controllers;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sd.blueblood.LDAP;
import com.sd.blueblood.SQLConnectionProperties;
import com.sd.conexion.Conexion;
import com.sd.dao.Posts;
import com.sd.dao.Usuarios;

/**
 * SPRING - USUARIOS REST
 * 
 * @author BlueFox - SD
 */

@RestController
@CrossOrigin(origins = "127.0.0.1:8081", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/usuarios")
public class UsuariosRest {

	@Autowired
	CacheManager cacheManager;

	@Autowired
	private SQLConnectionProperties proper;

	@GetMapping("/test")
	@ResponseBody
	String testing() {
		return "";// proper.getHost();
	}

	@Value("${app.upload.dir:${user.home}}/uploads/sangre/profiles/")
	public String SERVER_UPLOADS_PATH;

	@GetMapping
	ArrayList<Usuarios> getAll(@RequestParam(value = "query", required = false) String search) {
		try {
			Conexion.connect(proper.getHost(), proper.getUser(), proper.getPass(), proper.getDb(), proper.getPort());
			if (search != null) {
				return Usuarios.searchUsers(search);
			}
			return Usuarios.getAllRecords(0);
		} catch (SQLException ex) {
			Logger.getLogger(UsuariosRest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@GetMapping("/list")
	@ResponseBody
	ArrayList<Map<String, String>> getAllShort() {
		try {
			Conexion.connect(proper.getHost(), proper.getUser(), proper.getPass(), proper.getDb(), proper.getPort());
			return Usuarios.getAllShort();

		} catch (SQLException ex) {
			Logger.getLogger(UsuariosRest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@GetMapping("/{id}")
	@ResponseBody
	Usuarios getRecord(@PathVariable int id) {
		try {
			Conexion.connect(proper.getHost(), proper.getUser(), proper.getPass(), proper.getDb(), proper.getPort());
			return Usuarios.getRecord(id);
		} catch (SQLException ex) {
			Logger.getLogger(UsuariosRest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@GetMapping("/{id}/posts")
	@ResponseBody
	ArrayList<Posts> getRecordsByUser(@PathVariable int id,
			@RequestParam(value = "usuario_id", required = false) Integer usuario) {
		try {
			Conexion.connect(proper.getHost(), proper.getUser(), proper.getPass(), proper.getDb(), proper.getPort());
			if (usuario != null) {
				return Posts.getRecordsbyUser(id, usuario);
			} else {
				return Posts.getRecordsbyUser(id);
			}
		} catch (SQLException ex) {
			Logger.getLogger(PostsRest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@RequestMapping(value = "/{id}/profile", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@Cacheable(value = "profiles", key = "#id")
	public byte[] getImage(@PathVariable int id) {
		File imgFile;
		try {
			Conexion.connect(proper.getHost(), proper.getUser(), proper.getPass(), proper.getDb(), proper.getPort());
			String path = Usuarios.getProfilePic("" + id);
			System.out.println(path);
			imgFile = new File(path);
			if (!imgFile.exists()) {
				imgFile = new File(SERVER_UPLOADS_PATH + "default.png ");
			}
			FileInputStream fis = new FileInputStream(imgFile);
			byte[] image = IOUtils.toByteArray(fis);
			fis.close();
			return image;
		} catch (IOException | SQLException e) {
			// e.printStackTrace();
			System.err.println(e.getMessage());
			imgFile = new File(SERVER_UPLOADS_PATH + "default.png");
			try {
				FileInputStream fis = new FileInputStream(imgFile);
				byte[] image = IOUtils.toByteArray(fis);
				fis.close();
				return image;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.getMessage();
				return null;
			}
		}
	}

	@GetMapping("/{user_id}/jefe/{jefe_id}")
	@ResponseBody
	boolean esJefe(@PathVariable int user_id, @PathVariable int jefe_id) {
		try {
			Conexion.connect(proper.getHost(), proper.getUser(), proper.getPass(), proper.getDb(), proper.getPort());
			return Usuarios.getJefe(user_id, jefe_id);
		} catch (SQLException ex) {
			Logger.getLogger(UsuariosRest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

	@PostMapping("/login")
	@ResponseBody
	ResponseEntity<Usuarios> save(String correo, String spassword) {
		try {
			Conexion.connect(proper.getHost(), proper.getUser(), proper.getPass(), proper.getDb(), proper.getPort());
			// Usuarios user = Usuarios.login(correo, spassword);
			Usuarios user = Usuarios.login(correo + "@example.com", spassword);
			LDAP ldap = new LDAP();
			if (ldap.ldapAuthentication(correo, spassword)) {
				return ResponseEntity.ok(user);

			} else {
				Usuarios error = new Usuarios();
				error.setNombre("Error de usuario y/o contrase�a");
				return ResponseEntity.badRequest().body(error);
			}

		} catch (SQLException e) {
			Usuarios error = new Usuarios();
			error.setNombre(e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}

	}

	@PostMapping
	@ResponseBody
	ResponseEntity<Usuarios> save(@RequestBody Usuarios obj) {
		try {
			Conexion.connect(proper.getHost(), proper.getUser(), proper.getPass(), proper.getDb(), proper.getPort());
			obj.insert();
			return ResponseEntity.ok(Usuarios.getRecord(obj.getCodigo_id()));
		} catch (SQLException ex) {
			Logger.getLogger(UsuariosRest.class.getName()).log(Level.SEVERE, null, ex);
			obj.setNombre(ex.getMessage());
			return ResponseEntity.badRequest().body(obj);
		}

	}

	@PostMapping(value = "/foto")
	// @CacheEvict(value="profiles",key="#data.id")
	public ResponseEntity<String> handleFileUpload(@RequestBody Map<String, ?> data) {
		String user = "" + data.get("id");
		if (!((String) data.get("foto")).isEmpty()) {
			String[] valores = ((String) data.get("foto")).split(";");
			String extension = "";

			byte[] decodedBytes = Base64.getDecoder().decode(valores[1].substring(7));
			if (valores[0].indexOf("jpeg") > 0) {
				extension = ".jpg";
			} else {
				extension = ".png";
			}

			try {
				FileUtils.writeByteArrayToFile(new File(SERVER_UPLOADS_PATH + user + extension), decodedBytes);
				Usuarios.uploadPic(user, SERVER_UPLOADS_PATH + user + extension);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String nick = "" + data.get("nick");
		if (!nick.isEmpty()) {
			try {
				Usuarios.updateNick(user, nick);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cacheManager.getCache("profiles").evict(Integer.parseInt(user));
		return ResponseEntity.ok("Imagen de perfil cargada");
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	long delete(@PathVariable int id) {
		Usuarios obj = new Usuarios(id);
		try {
			return obj.delete();
		} catch (SQLException ex) {
			Logger.getLogger(UsuariosRest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

}

package com.sd.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sd.blueblood.EmailServiceImpl;
import com.sd.blueblood.SQLConnectionProperties;
import com.sd.conexion.Conexion;
import com.sd.dao.Detalle_Post;
import com.sd.dao.Etiquetas;
import com.sd.dao.PostReportado;
import com.sd.dao.Posts;
import com.sd.dao.Pulsos;
import com.sd.dao.Usuarios;

/**
 * SPRING - POSTS REST
 * 
 * @author BlueFox - SD
 */

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "127.0.0.1:8081", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })

public class PostsRest {
	@Value("${app.upload.dir:${user.home}}/uploads/sangre/posts/")
	public String SERVER_UPLOADS_PATH;
	
	@Autowired
	private EmailServiceImpl mailer;
	
	@Autowired
	private SQLConnectionProperties proper;
	
	@GetMapping
	ArrayList<Posts> getAll(@RequestParam(value = "usuario_id", required = false) Integer usuario,
			@RequestParam(value = "post_id", required = false) Integer post) {
		try {
			Conexion.connect(proper.getHost(),proper.getUser(),proper.getPass(),proper.getDb(),proper.getPort());
			if (usuario == null && post == null) {
				return Posts.getAllRecords(-1);
			}
			if (usuario != null && post == null) {
				// System.out.println(Posts.getAllRecords(usuario).size());
				return Posts.getAllRecords(usuario);
			} else {
				return Posts.getRecord(post, usuario);
			}
		} catch (SQLException ex) {
			Logger.getLogger(PostsRest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	/*
	 * @GetMapping("/{id}")
	 * 
	 * @ResponseBody ArrayList<Posts> getRecord(@PathVariable int id) { try { return
	 * Posts.getAllRecords(id); } catch (SQLException ex) {
	 * Logger.getLogger(PostsRest.class.getName()).log(Level.SEVERE, null, ex); }
	 * return null; }
	 */
	@RequestMapping(value = "/{id}/media", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@Cacheable(value = "images", key = "#id")
	@ResponseBody
	public byte[] getImage(@PathVariable int id) {
		try {
			Conexion.connect(proper.getHost(),proper.getUser(),proper.getPass(),proper.getDb(),proper.getPort());
			String path = Posts.getProfilePic("" + id);
			File imgFile = new File(path);
			if (!imgFile.exists()) {
				imgFile = new File(SERVER_UPLOADS_PATH + "/default.jpg ");
			}
			FileInputStream is = new FileInputStream(imgFile);
			return IOUtils.toByteArray(is);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.err.println(e.getMessage());
			// imgFile = new File(SERVER_UPLOADS_PATH + "none.jpg");
			// response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

	@GetMapping("/{id}/detail")
	@ResponseBody
	Detalle_Post getPostDetail(@PathVariable int id) throws SQLException {
		Conexion.connect(proper.getHost(),proper.getUser(),proper.getPass(),proper.getDb(),proper.getPort());
		Detalle_Post detalle = new Detalle_Post();
		detalle.getDetails(id);
		return detalle;
	}

	@GetMapping("/top/globulos")
	@ResponseBody
	Detalle_Post getTopGlobulos() throws SQLException {
		Detalle_Post detalle = new Detalle_Post();
		detalle.getTopGlobulos();
		return detalle;
	}

	@GetMapping("/top/pulsos")
	@ResponseBody
	Detalle_Post getTopPulsos() throws SQLException {
		Conexion.connect(proper.getHost(),proper.getUser(),proper.getPass(),proper.getDb(),proper.getPort());
		Detalle_Post detalle = new Detalle_Post();
		detalle.getTopPulsos();
		return detalle;
	}

	@PostMapping
	@ResponseBody
	ResponseEntity<Posts> save(@RequestBody Posts obj) throws IOException {
		try {
			Conexion.connect(proper.getHost(),proper.getUser(),proper.getPass(),proper.getDb(),proper.getPort());
			if (obj.getPath_media() != null && !obj.getPath_media().isEmpty()
					&& obj.getPath_media().contains("image")) {
				String[] valores = obj.getPath_media().split(";");
				String extension = "";
				byte[] decodedBytes = Base64.getDecoder().decode(valores[1].substring(7));
				if (valores[0].indexOf("jpeg") > 0) {
					extension = ".jpg";
				} else {
					extension = ".png";
				}
				obj.setPath_media("");
				obj.setTipo("imagen");
				obj.setCodigo_id(obj.insert());
				FileUtils.writeByteArrayToFile(new File(SERVER_UPLOADS_PATH + obj.getCodigo_id() + extension),
						decodedBytes);
				Posts.uploadPic("" + obj.getCodigo_id(), SERVER_UPLOADS_PATH + obj.getCodigo_id() + extension);
			} else {
				if (obj.getTipo_pulso() != null) {
					obj.setTipo("imagen");
				}
				obj.setPath_media("");
				obj.setCodigo_id(obj.insert());
			}
			if (obj.getTipo_pulso() != null) {
				if (obj.getTipo_pulso().equals("Tenemos sangre Tigo")) {
					Posts.uploadPic("" + obj.getCodigo_id(), SERVER_UPLOADS_PATH + "TST.jpg");
				} else if (obj.getTipo_pulso().equals("Somos 1 solo Tigo")) {
					Posts.uploadPic("" + obj.getCodigo_id(), SERVER_UPLOADS_PATH + "UST.jpg");
				} else if (obj.getTipo_pulso().equals("Hacemos que suceda de la forma correcta")) {
					Posts.uploadPic("" + obj.getCodigo_id(), SERVER_UPLOADS_PATH + "SFC.jpg");
				} else if(obj.getTipo_pulso().equals("Sangre tigo")) {
					Posts.uploadPic("" + obj.getCodigo_id(), SERVER_UPLOADS_PATH + "PSMCP.jpg");
				} else {
					Posts.uploadPic("" + obj.getCodigo_id(), SERVER_UPLOADS_PATH + "DMNC.jpg");
				}
				Pulsos pulso = new Pulsos(obj.getUsuario_id(), obj.getCodigo_id(), "", obj.getTipo_pulso());
				Map<String,String> parametros=pulso.insertPulso();
				parametros.put("post_id", ""+pulso.getPost_id());
				if(obj.getTipo_pulso().equals("Sangre tigo")) {
					mailer.sendSuperMessage(parametros);
				}else {
					mailer.sendSimpleMessage(parametros);
				}
				// AQUI SE CONTROLA LAS ETIQUETAS
				ArrayList<Etiquetas> lista=obj.addTags();
				for (Etiquetas tag : lista) {
					parametros.replace("usuario_mail",tag.getUsuario_mail());
					if(obj.getTipo_pulso().equals("Sangre tigo")) {
						mailer.sendSuperMessage(parametros);
					}else {
						mailer.sendSimpleMessage(parametros);
					}
				}
			}
			return ResponseEntity.ok(obj);

		} catch (SQLException | MessagingException ex) {
			ex.printStackTrace();
			Logger.getLogger(PostsRest.class.getName()).log(Level.SEVERE, null, ex);
			Posts error = new Posts();
			error.setMensaje(ex.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	long delete(@PathVariable long id) {
		Posts obj = new Posts(id);
		try {
			return obj.delete();
		} catch (SQLException ex) {
			Logger.getLogger(PostsRest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

	/*
	 * 
	 * SERVICIOS REST REPORTAR POSTS
	 * 
	 * 
	 */

	@GetMapping("/reportados")
	ArrayList<PostReportado> getAll(@RequestParam(value = "query", required = false) String search) {
		try {
			Conexion.connect(proper.getHost(),proper.getUser(),proper.getPass(),proper.getDb(),proper.getPort());
			return PostReportado.getAllRecords(0);
		} catch (SQLException ex) {
			Logger.getLogger(UsuariosRest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@PostMapping("/reportados")
	@ResponseBody
	ResponseEntity<PostReportado> reportPost(@RequestBody PostReportado obj) throws IOException {
		try {
			Conexion.connect(proper.getHost(),proper.getUser(),proper.getPass(),proper.getDb(),proper.getPort());
			obj.insert();
			return ResponseEntity.ok(obj);

		} catch (SQLException ex) {
			ex.printStackTrace();
			Logger.getLogger(PostsRest.class.getName()).log(Level.SEVERE, null, ex);
			PostReportado error = new PostReportado();
			error.setMotivo(ex.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	@PutMapping("/reportados")
	@ResponseBody
	ResponseEntity<PostReportado> censorPost(@RequestBody PostReportado obj) throws IOException {
		try {
			obj.update();
			return ResponseEntity.ok(obj);

		} catch (SQLException ex) {
			ex.printStackTrace();
			Logger.getLogger(PostsRest.class.getName()).log(Level.SEVERE, null, ex);
			PostReportado error = new PostReportado();
			error.setMotivo(ex.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

}

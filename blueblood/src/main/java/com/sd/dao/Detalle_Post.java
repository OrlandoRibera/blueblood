package com.sd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class Detalle_Post {

	@JsonRawValue
	private String detalle_likes;

	public Detalle_Post() {
	}

	public Detalle_Post(String detalle_likes) {
		this.detalle_likes = detalle_likes;
	}

	public String getDetalle_likes() {
		return detalle_likes;
	}

	public void setDetalle_likes(String detalle_likes) {
		this.detalle_likes = detalle_likes;
	}

	public void getDetails(int post_id) throws SQLException {
		ResultSet rs = DAO_PA.getStoredProcedure("public", "get_", "posts_details", "" + post_id);
		rs.next();
		detalle_likes = rs.getString(1);
		rs.close();
	}
	public void getTopGlobulos() throws SQLException {
		ResultSet rs = DAO_PA.getStoredProcedure("public", "get_", "top_globulos","");
		rs.next();
		detalle_likes = rs.getString(1);
		rs.close();
	}
	public void getTopPulsos() throws SQLException {
		ResultSet rs = DAO_PA.getStoredProcedure("public", "get_", "top_pulsos","");
		rs.next();
		detalle_likes = rs.getString(1);
		rs.close();
	}
}

package com.sd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SPRING - BEAN
 * @author BlueFox - SD
 */
public class Posts_tags implements IDAO{
// <editor-fold defaultstate="collapsed" desc="Atributos">
    private long post_id;
    private String tag;
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Constructores + toString">
    public Posts_tags() {
    }
    public Posts_tags(long post_id) {
        this.post_id = post_id;
    }
    public Posts_tags(long post_id, String tag) {
        this.post_id = post_id;
        this.tag = tag;
    }

    @Override
    public String toString() {
    return post_id +", "+tag ;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="CRUD">   
    @Override
    public long insert() throws SQLException {
        return DAO_PA.insertRecord("public","posts_tags", this);
    }

    @Override
    public long update() throws SQLException {
        return DAO_PA.updateRecord("public","posts_tags", this);
    }

    @Override
    public long delete() throws SQLException {
        return DAO_PA.deleteRecord("public","posts_tags", this);
    }
    
    public static ArrayList<Posts_tags> getAllRecords(int limit) throws SQLException {
            return DAO_PA.getTableDAO("public","posts_tags",limit,Posts_tags.class);
    }
    public static Posts_tags getRecord(int id) throws SQLException {
        return (Posts_tags)DAO_PA.getTableDAO("public", "posts_tags", id, Posts_tags.class).get(0);
    }
// </editor-fold>  
// <editor-fold defaultstate="collapsed" desc="Accessors">
    public long getPost_id() {
        return post_id;
    }
    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

// </editor-fold>   
    public ArrayList<Posts_tags> filler(ResultSet rs) throws SQLException {
        ArrayList<Posts_tags> vec = new ArrayList<>();
        while (rs.next()) {
            vec.add(new Posts_tags(rs.getLong(1), rs.getString(2)));
        }
        rs.close();
        System.gc();
        return vec;
    }

    @Override
	public Object[] rip() {
		Object[] data=new Object[2];
        data[0] = post_id;
        data[1] = tag;
		return data;
	}
}


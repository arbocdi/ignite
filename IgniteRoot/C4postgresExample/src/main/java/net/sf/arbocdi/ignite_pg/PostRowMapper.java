/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ignite_pg;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class PostRowMapper implements RowMapper<Post>{

    @Override
    public Post mapRow(ResultSet rs, int i) throws SQLException {
        Post post= new Post();
        post.setId(rs.getString("id"));
        post.setTitle(rs.getString("title"));
        post.setDescription(rs.getString("description"));
        post.setCreationDate(rs.getDate("creationDate").toLocalDate());
        post.setAuthor(rs.getString("author"));
        return post;
    }
    
}

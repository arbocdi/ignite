/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ignite_pg;

import java.io.Serializable;
import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lifecycle.LifecycleAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class PostgresDBStore extends CacheStoreAdapter<String, Post> implements LifecycleAware {

    private JdbcOperations template;
    
    @Override
    public Post load(String k) throws CacheLoaderException {
        Object[] args = {k};
        return template.queryForObject("SELECT * FROM posts WHERE id = ?",args , new PostRowMapper());
    }

    @Override
    public void write(Cache.Entry<? extends String, ? extends Post> entry) throws CacheWriterException {
        Post p = entry.getValue();
        template.update("INSERT INTO POSTS (id,title,description,creationDate,author) VALUES (?,?,?,?,?)", 
                p.getId(),p.getTitle(), p.getDescription(),p.getCreationDate(),p.getAuthor());
    }

    @Override
    public void delete(Object o) throws CacheWriterException {
        template.update("DELETE FROM POSTS where id = ? ", o);
    }

    @Override
    public void start() throws IgniteException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(PostgresConfiguration.class);
        template = ctx.getBean(JdbcOperations.class);
    }

    @Override
    public void stop() throws IgniteException {
    }
}

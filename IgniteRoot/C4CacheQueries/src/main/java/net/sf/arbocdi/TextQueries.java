/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi;

import java.util.List;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.TextQuery;
import org.apache.ignite.internal.processors.cache.CacheEntryImpl;

/**
 *
 * @author root
 */
public class TextQueries {

    void perform(IgniteCache<Long, Company> cache) {
        // Query for all companies which has a text "John".
        TextQuery<Integer, Company> john = new TextQuery<>(Company.class, "John");
        QueryCursor cursor =  cache.query(john);
        List<CacheEntryImpl> cmpList = cursor.getAll();
        System.out.println("===========================john======================");
        for(CacheEntryImpl entry:cmpList){
            System.out.println(entry.getKey()+"\t"+entry.getValue());
        }
        cursor.close();

    }
}

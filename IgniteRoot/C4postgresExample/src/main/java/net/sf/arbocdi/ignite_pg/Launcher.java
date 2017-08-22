/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ignite_pg;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.cache.configuration.FactoryBuilder;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.transactions.Transaction;
import static org.apache.ignite.transactions.TransactionConcurrency.PESSIMISTIC;
import static org.apache.ignite.transactions.TransactionIsolation.REPEATABLE_READ;

/**
 *
 * @author root
 */
public class Launcher {

    public static void main(String[] args) {
        
        IgniteConfiguration cfg = new IgniteConfiguration();
        
        
        CacheConfiguration configuration = new CacheConfiguration();
        configuration.setName("dynamicCache");
        configuration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        configuration.setCacheStoreFactory(FactoryBuilder.factoryOf(PostgresDBStore.class));
        configuration.setReadThrough(true);
        configuration.setWriteThrough(true);
        configuration.setWriteBehindEnabled(true);
        cfg.setCacheConfiguration(configuration);
        try (Ignite ignite = Ignition.start(cfg);) {
            
            //create cache if it doesn't exist
            int count = 10;
            try (IgniteCache<String, Post> igniteCache = ignite.getOrCreateCache("dynamicCache");
                    Transaction tx = ignite.transactions().txStart(PESSIMISTIC, REPEATABLE_READ);) {
                //let us clear

                for (int i = 1; i <= count; i++) {
                    igniteCache.put("_" + i, new Post("_" + i, "title-" + i, "description-" + i, LocalDate.now().plus(i, ChronoUnit.DAYS), "author-" + i));
                }

                tx.commit();

                for (int i = 1; i < count; i += 2) {
                    igniteCache.remove("_" + i);
                    log("Clear every odd key: " + i);
                }

                for (long i = 1; i <= count; i++) {
                    log("Local peek at [key=_" + i + ", val=" + igniteCache.localPeek("_" + i) + ']');
                }
                
                for (long i = 1; i <= count; i++) {
                    log("Got [key=_" + i + ", val=" + igniteCache.get("_" + i) + ']');
                }

                tx.commit();
                

            }
        }

    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}

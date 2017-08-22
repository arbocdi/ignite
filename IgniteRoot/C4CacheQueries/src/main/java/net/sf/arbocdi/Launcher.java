package net.sf.arbocdi;

import java.io.IOException;
import java.util.Map;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

public class Launcher {

    public static void main(String[] args) throws IOException {
        //стартуем игнайт
        Ignite ignite = Ignition.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                ignite.close();
            }
        });
        CSVLoader csvLoader = new CSVLoader();
        //загружаем данные из цсв в кеш
        Map<Long, Company> companyMap = csvLoader.load();
        //добавлю индексацию для Lucene
        CacheConfiguration<Long, Company> companyCacheCfg = new CacheConfiguration<>("company_cache");
        companyCacheCfg.setCacheMode(CacheMode.PARTITIONED);
        companyCacheCfg.setIndexedTypes(Long.class, Company.class);
        //создаю кеш
        IgniteCache<Long, Company> companyCache = ignite.getOrCreateCache(companyCacheCfg);
        companyCache.clear();
        //load data to cache
        for (Company cmp : companyMap.values()) {
            companyCache.put(cmp.getId(), cmp);
        }
        //scan queries example
        ScanQueries sq = new ScanQueries();
        sq.performSanQueries(companyCache);
        //test queries
        TextQueries txt = new TextQueries();
        txt.perform(companyCache);

    }
}

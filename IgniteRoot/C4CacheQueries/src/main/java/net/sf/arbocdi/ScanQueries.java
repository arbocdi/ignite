/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi;

import java.util.Iterator;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.internal.processors.cache.CacheEntryImpl;

/**
 *
 * @author root
 */
public class ScanQueries {

    public void performSanQueries(IgniteCache<Long, Company> companyCache) {
        // Query for all companies which the city 'NEW YORK' – State NewYork.
        //uses IgnitePredicate - это ункциональный интерейс which takes 2 params and return boolean
        QueryCursor cursor = companyCache.query(new ScanQuery<Long, Company>((k, p) -> p.getCity().equalsIgnoreCase("NEW YORK")));
        System.out.println("=============================NEW YORK");
        for (Iterator ite = cursor.iterator(); ite.hasNext();) {
            CacheEntryImpl company = (CacheEntryImpl) ite.next();
            System.out.println(company.getKey() + "\t" + ((Company) company.getValue()).getCity());
        }
        cursor.close();
        //в предикатах можно комбинировать условия
        cursor = companyCache.query(new ScanQuery<Long, Company>((k, p) -> p.getCity().equalsIgnoreCase("NEW YORK") && p.getEmail().equalsIgnoreCase("keith@groupdigital.com")));
        System.out.println("=============================NEW YORK+email");
        for (Iterator ite = cursor.iterator(); ite.hasNext();) {
            CacheEntryImpl company = (CacheEntryImpl) ite.next();
            System.out.println(company.getKey() + "\t" + ((Company) company.getValue()).getCity() + "\t" + ((Company) company.getValue()).getEmail());
        }
        cursor.close();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi;

import java.util.Arrays;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

/**
 *
 * @author root
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello Ignite");
// create a new instance of TCP Discovery SPI
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
// create a new instance of tcp discovery multicast ip finder
        TcpDiscoveryMulticastIpFinder tcMp = new TcpDiscoveryMulticastIpFinder();
        tcMp.setAddresses(Arrays.asList("127.0.0.1")); // change your IP address here
// set the multi cast ip finder for spi
        spi.setIpFinder(tcMp);
// create new ignite configuration
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setClientMode(false);
// set the discovery spi to ignite configuration
        cfg.setDiscoverySpi(spi);
// Start ignite
        Ignite ignite = Ignition.start(cfg);
// get or create cache
        IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCacheName");
// put some cache elements
        for (int i = 1; i <= 100; i++) {
            cache.put(i, Integer.toString(i));
        }
// get them from the cache and write to the console
        for (int i = 1; i <= 100; i++) {
            System.out.println("Cache get:" + cache.get(i));
        }
// close ignite instance
 Thread.sleep(1000000000);
        ignite.close();
        
       

    }
}

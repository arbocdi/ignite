package net.sf.arbocdi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.ignite.IgniteCache;

public class CSVLoader {

    public Map<Long,Company> load() throws IOException {
        Map<Long,Company> companyMap = new LinkedHashMap();
        Stream<String> lines = Files.lines(Paths.get(("USA_NY_email_addresses.csv")));
        lines.skip(1).map(s1 -> s1.split("\",\""))
                .map(s2 -> new Company(Long.valueOf(Long.valueOf(s2[0].replaceAll("\"", ""))), s2[1], s2[2], s2[3], s2[4], s2[5], s2[6], s2[7], s2[8], s2[9]))
                .forEach( r -> companyMap.put(r.getId(), r));
        return companyMap;
    }
}

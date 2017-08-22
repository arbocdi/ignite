package net.sf.arbocdi.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by isatimur on 8/9/16.
 */
@Data
public class Department implements Serializable {
    private static final AtomicInteger GENERATED_ID = new AtomicInteger();

    @QuerySqlField(index = true)
    private Integer deptno;

    @QuerySqlField
    private String dname;

    @QuerySqlField
    private String loc;

    public Department(String dname, String loc) {
        this.deptno = GENERATED_ID.incrementAndGet();
        this.dname = dname;
        this.loc = loc;
    }

   }

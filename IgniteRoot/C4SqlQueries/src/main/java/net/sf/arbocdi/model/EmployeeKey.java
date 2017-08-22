package net.sf.arbocdi.model;

import java.io.Serializable;
import lombok.Data;
import org.apache.ignite.cache.affinity.AffinityKeyMapped;

/**
 * Created by isatimur on 8/10/16.
 */
@Data
public class EmployeeKey implements Serializable {

    private final int empNo;

    @AffinityKeyMapped
    private final int deptNo;

    public EmployeeKey(int empNo, int deptNo) {
        this.empNo = empNo;
        this.deptNo = deptNo;
    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 *
 * @author root
 */
@Data
public class Employee implements Serializable {

    private static final AtomicInteger GENERATED_ID = new AtomicInteger();

    @QuerySqlField(index = true)
    private Integer empno;
    @QuerySqlField
    private String ename;
    @QuerySqlField
    private String job;
    @QuerySqlField
    private Integer mgr;
    @QuerySqlField
    private LocalDate hiredate;
    @QuerySqlField
    private Integer sal;
    @QuerySqlField(index = true)
    private Integer deptno;

    private transient EmployeeKey key;

    public Employee(String ename, Department dept, String job, Integer mgr, LocalDate hiredate, Integer sal) {
        this.empno = GENERATED_ID.incrementAndGet();
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.hiredate = hiredate;
        this.sal = sal;
        this.deptno = dept.getDeptno();
        key = new EmployeeKey(empno, deptno);
    }

    public EmployeeKey getKey() {
        if (key == null) {
            key = new EmployeeKey(empno, deptno);
        }
        return key;
    }

}

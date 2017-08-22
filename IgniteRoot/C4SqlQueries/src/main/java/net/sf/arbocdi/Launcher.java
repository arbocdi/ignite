/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.cache.Cache.Entry;
import net.sf.arbocdi.model.Department;
import net.sf.arbocdi.model.Employee;
import net.sf.arbocdi.model.EmployeeKey;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.internal.processors.cache.CacheEntryImpl;

/**
 *
 * @author root
 */
public class Launcher {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /*
      Replicated cache name to store departments.
     */
    private static final String DEPARTMENT_CACHE_NAME = "departments";

    /*
      Partitioned cache name to store employees.
     */
    private static final String EMPLOYEE_CACHE_NAME = "employees";

    public static void main(String[] args) throws InterruptedException {
        try (Ignite ignite = Ignition.start()) {
            //create caches config
            CacheConfiguration<Integer, Department> deptCacheCfg = new CacheConfiguration<>(DEPARTMENT_CACHE_NAME);

            deptCacheCfg.setCacheMode(CacheMode.REPLICATED);
            deptCacheCfg.setIndexedTypes(Integer.class, Department.class);

            CacheConfiguration<EmployeeKey, Employee> employeeCacheCfg = new CacheConfiguration<>(EMPLOYEE_CACHE_NAME);

            //специально для распределенных джоинов
            employeeCacheCfg.setCacheMode(CacheMode.PARTITIONED);
            employeeCacheCfg.setIndexedTypes(EmployeeKey.class, Employee.class);

            try (
                    IgniteCache<Integer, Department> deptCache = ignite.createCache(deptCacheCfg);
                    IgniteCache<EmployeeKey, Employee> employeeCache = ignite.createCache(employeeCacheCfg)) {
                //заполним кеш
                initialize();
                //выполним простой запрос
                SqlQuery<EmployeeKey, Employee> empSal = new SqlQuery(Employee.class, "sal>?");
                empSal.setArgs(1000);
                QueryCursor cursor = employeeCache.query(empSal);
                cursorPrinter(cursor);

                //вернем только нужные поля
                SqlFieldsQuery empSalName = new SqlFieldsQuery("SELECT e.ename,e.job FROM Employee e WHERE sal > ?");
                empSalName.setArgs("2000");
                cursor = employeeCache.query(empSalName);
                cursorPrintergeneral(cursor);

                //выполним объединение используя афинный ключ - который гарантирует что сотрудник и отдел будут на одних
                //и тех же нодах
                SqlQuery<EmployeeKey, Employee> joinQuery = new SqlQuery<>(Employee.class,
                        "from Employee , \"" + DEPARTMENT_CACHE_NAME + "\".Department   "
                        + "where Employee.deptno = Department.deptno "
                        + "and Department.dname = 'Sales'");
                cursor = employeeCache.query(joinQuery);
                cursorPrinter(cursor);

                //рассчитаем среднюю зп
                SqlFieldsQuery avgSal = new SqlFieldsQuery("SELECT SUM(e.sal)/COUNT(*) FROM Employee e");
                cursor = employeeCache.query(avgSal);
                cursorPrintergeneral(cursor);
                //вернем имена сотруднков и названия их отделов
                SqlFieldsQuery empDep = new SqlFieldsQuery(
                        "select e.ename, d.dname "
                        + "from Employee e, \"departments\".Department d "
                        + "where e.deptno = d.deptno");
                cursor = employeeCache.query(empDep);
                cursorPrintergeneral(cursor);
                Thread.sleep(1000);
            }
        }
    }

    private static void cursorPrintergeneral(QueryCursor cursor) {
        System.out.println("===============================");
        for (Object o : cursor.getAll()) {
            System.out.println(o + "\t" + o.getClass());
        }
        cursor.close();
    }

    private static void cursorPrinter(QueryCursor cursor) {
        System.out.println("===============================");
        for (Object o : cursor.getAll()) {
            Entry<EmployeeKey, Employee> entry = (Entry<EmployeeKey, Employee>) o;
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
        cursor.close();
    }

    private static void initialize() throws InterruptedException {
        IgniteCache<Integer, Department> deptCache = Ignition.ignite().cache(DEPARTMENT_CACHE_NAME);
        IgniteCache<EmployeeKey, Employee> employeeCache = Ignition.ignite().cache(EMPLOYEE_CACHE_NAME);

        // Clear caches before start.
        deptCache.clear();
        employeeCache.clear();

        // Departments
        Department dept1 = new Department("Accounting", "New York");
        Department dept2 = new Department("Research", "Dallas");
        Department dept3 = new Department("Sales", "Chicago");
        Department dept4 = new Department("Operations", "Boston");

        // Employees
        Employee emp1 = new Employee("King", dept1, "President", null, localDateOf("17-11-1981"), 5000);
        Employee emp2 = new Employee("Blake", dept3, "Manager", emp1.getEmpno(), localDateOf("01-05-1981"), 2850);
        Employee emp3 = new Employee("Clark", dept1, "Manager", emp1.getEmpno(), localDateOf("09-06-1981"), 2450);
        Employee emp4 = new Employee("Jones", dept2, "Manager", emp1.getEmpno(), localDateOf("02-04-1981"), 2975);
        Employee emp5 = new Employee("Scott", dept2, "Analyst", emp4.getEmpno(), localDateOf("13-07-1987").minusDays(85), 3000);
        Employee emp6 = new Employee("Ford", dept2, "Analyst", emp4.getEmpno(), localDateOf("03-12-1981"), 3000);
        Employee emp7 = new Employee("Smith", dept2, "Clerk", emp6.getEmpno(), localDateOf("17-12-1980"), 800);
        Employee emp8 = new Employee("Allen", dept3, "Salesman", emp2.getEmpno(), localDateOf("20-02-1981"), 1600);
        Employee emp9 = new Employee("Ward", dept3, "Salesman", emp2.getEmpno(), localDateOf("22-02-1981"), 1250);
        Employee emp10 = new Employee("Martin", dept3, "Salesman", emp2.getEmpno(), localDateOf("28-09-1981"), 1250);
        Employee emp11 = new Employee("Turner", dept3, "Salesman", emp2.getEmpno(), localDateOf("08-09-1981"), 1500);
        Employee emp12 = new Employee("Adams", dept2, "Clerk", emp5.getEmpno(), localDateOf("13-07-1987").minusDays(51), 1100);
        Employee emp13 = new Employee("James", dept3, "Clerk", emp2.getEmpno(), localDateOf("03-12-1981"), 950);
        Employee emp14 = new Employee("Miller", dept1, "Clerk", emp3.getEmpno(), localDateOf("23-01-1982"), 1300);

        deptCache.put(dept1.getDeptno(), dept1);
        deptCache.put(dept2.getDeptno(), dept2);
        deptCache.put(dept3.getDeptno(), dept3);
        deptCache.put(dept4.getDeptno(), dept4);

        // Note that in this example we use custom affinity key for Employee objects
        // to ensure that all persons are collocated with their departments.
        employeeCache.put(emp1.getKey(), emp1);
        employeeCache.put(emp2.getKey(), emp2);
        employeeCache.put(emp3.getKey(), emp3);
        employeeCache.put(emp4.getKey(), emp4);
        employeeCache.put(emp5.getKey(), emp5);
        employeeCache.put(emp6.getKey(), emp6);
        employeeCache.put(emp7.getKey(), emp7);
        employeeCache.put(emp8.getKey(), emp8);
        employeeCache.put(emp9.getKey(), emp9);
        employeeCache.put(emp10.getKey(), emp10);
        employeeCache.put(emp11.getKey(), emp11);
        employeeCache.put(emp12.getKey(), emp12);
        employeeCache.put(emp13.getKey(), emp13);
        employeeCache.put(emp14.getKey(), emp14);

        // Wait 1 second to be sure that all nodes processed put requests.
        Thread.sleep(1000);
    }

    private static LocalDate localDateOf(String parseDateText) {
        return LocalDate.parse(parseDateText, formatter);
    }
}

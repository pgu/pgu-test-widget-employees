package pgu.widget.employees.server;

import java.util.LinkedHashMap;

import pgu.widget.employees.shared.Employee;

public class DAO {

    public static LinkedHashMap<Integer, Employee> id2employees    = new LinkedHashMap<Integer, Employee>();

    static {
        for (int i = 0; i < 5; i++) {

            final Employee employee = new Employee();
            employee.setId(i);
            employee.setName("John Smith" + i);

            id2employees.put(employee.getId(), employee);
        }
    }


}

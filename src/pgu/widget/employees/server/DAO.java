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

            if (i == 0) {
                employee.setPicture("http://lh5.ggpht.com/_7ZYqYi4xigk/SaXJbI77n-I/AAAAAAAAEeA/DeV-BvKsa0c/s288/sergey_brin.jpg");

            } else if (i == 1) {
                employee.setPicture("http://ideaelevator.co/wp/wp-content/uploads/2011/01/petergriffinbh9.jpg");

            } else if (i == 2) {
                employee.setPicture("http://fc08.deviantart.net/fs71/f/2011/059/4/2/homer_simpson_by_mdiaz13-d3alpqr.jpg");

            } else if (i == 3) {
                employee.setPicture("http://www.nataliemaclean.com/blog/wp-content/uploads/2011/03/BradPitt1.jpg");
            }

            id2employees.put(employee.getId(), employee);
        }

    }


}

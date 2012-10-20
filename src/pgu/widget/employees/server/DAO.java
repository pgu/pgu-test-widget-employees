package pgu.widget.employees.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import pgu.widget.employees.shared.Employee;
import pgu.widget.employees.shared.Technos;

public class DAO {

    public static LinkedHashMap<Integer, Employee> id2employees    = new LinkedHashMap<Integer, Employee>();

    static {
        for (int i = 0; i < 5; i++) {

            final ArrayList<String> technos = new ArrayList<String>();
            String picture = null;

            if (i == 0) {
                picture = "http://lh5.ggpht.com/_7ZYqYi4xigk/SaXJbI77n-I/AAAAAAAAEeA/DeV-BvKsa0c/s288/sergey_brin.jpg";
                technos.add(Technos.GWT);
                technos.add(Technos.CLOJURE);

            } else if (i == 1) {
                picture = "http://ideaelevator.co/wp/wp-content/uploads/2011/01/petergriffinbh9.jpg";
                technos.add(Technos.JS);
                technos.add(Technos.JAVA);
                technos.add(Technos.NOSQL);

            } else if (i == 2) {
                picture = "http://fc08.deviantart.net/fs71/f/2011/059/4/2/homer_simpson_by_mdiaz13-d3alpqr.jpg";
                technos.add(Technos.GWT);
                technos.add(Technos.JAVA);
                technos.add(Technos.SQL);

            } else if (i == 3) {
                picture = "http://www.nataliemaclean.com/blog/wp-content/uploads/2011/03/BradPitt1.jpg";
                technos.add(Technos.GWT);
                technos.add(Technos.CLOJURE);
                technos.add(Technos.NOSQL);

            } else if (i == 4) {
                technos.add(Technos.GWT);
                technos.add(Technos.JAVA);
                technos.add(Technos.SQL);
            }

            final Employee employee = new Employee();
            employee.setId(i);
            employee.setName("John Smith" + i);
            employee.setPicture(picture);
            employee.setTechnos(technos);

            id2employees.put(employee.getId(), employee);
        }

    }


}

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
                picture = "img/it_crowd_1.png";
                technos.add(Technos.GWT);
                technos.add(Technos.CLOJURE);

            } else if (i == 1) {
                picture = "img/it_crowd_2.png";
                technos.add(Technos.JS);
                technos.add(Technos.JAVA);
                technos.add(Technos.NOSQL);

            } else if (i == 2) {
                picture = "img/homer.png";
                technos.add(Technos.GWT);
                technos.add(Technos.JAVA);
                technos.add(Technos.SQL);

            } else if (i == 3) {
                picture = "img/peter.png";
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

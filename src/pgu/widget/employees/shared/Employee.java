package pgu.widget.employees.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Employee implements IsSerializable {

    private Integer id;
    private String name;
    private ArrayList<String> technos;

    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public ArrayList<String> getTechnos() {
        return technos;
    }
    public void setTechnos(final ArrayList<String> technos) {
        this.technos = technos;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", technos=" + technos + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}

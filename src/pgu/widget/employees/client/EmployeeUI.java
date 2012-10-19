package pgu.widget.employees.client;

import java.util.ArrayList;

import pgu.widget.employees.shared.Employee;
import pgu.widget.employees.shared.Technos;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EmployeeUI extends Composite {

    private static EmployeeUIUiBinder uiBinder = GWT.create(EmployeeUIUiBinder.class);

    interface EmployeeUIUiBinder extends UiBinder<Widget, EmployeeUI> {
    }

    @UiField
    Button saveBtn;
    @UiField
    TextBox nameBox;
    @UiField
    CheckBox java, clojure, net, js, gwt, sql, nosql;

    private final Pgu_test_widget_employees pgu_test_widget_employees;

    public EmployeeUI(final Pgu_test_widget_employees pgu_test_widget_employees) {
        initWidget(uiBinder.createAndBindUi(this));

        this.pgu_test_widget_employees = pgu_test_widget_employees;
    }

    private boolean isNew = true;
    private Employee currentEmployee;



    public void setEmployee(final Employee employee) {

        java.setValue(false);
        clojure.setValue(false);
        net.setValue(false);
        js.setValue(false);
        gwt.setValue(false);
        sql.setValue(false);
        nosql.setValue(false);

        GWT.log("employee " + employee);
        currentEmployee = employee;

        isNew = null == employee;
        if (isNew) {
            nameBox.setText("");

        } else {
            nameBox.setText(employee.getName());

            if (employee.getTechnos() != null) {
                for (final String t : employee.getTechnos()) {
                    if (Technos.JAVA.equals(t)) {
                        java.setValue(true);

                    } else if (Technos.CLOJURE.equals(t)) {
                        clojure.setValue(true);

                    } else if (Technos.NET.equals(t)) {
                        net.setValue(true);

                    } else if (Technos.JS.equals(t)) {
                        js.setValue(true);

                    } else if (Technos.GWT.equals(t)) {
                        gwt.setValue(true);

                    } else if (Technos.SQL.equals(t)) {
                        sql.setValue(true);

                    } else if (Technos.NOSQL.equals(t)) {
                        nosql.setValue(true);

                    }
                }
            }

        }
    }

    @UiHandler("saveBtn")
    public void clickOnSave(final ClickEvent e) {
        final String name = nameBox.getText();

        if (isNew && "".equals(name.trim())) {
            return;
        }

        final ArrayList<String> technos = new ArrayList<String>();
        if (java.getValue()) {
            technos.add(Technos.JAVA);
        }
        if (clojure.getValue()) {
            technos.add(Technos.CLOJURE);
        }
        if (net.getValue()) {
            technos.add(Technos.NET);
        }
        if (js.getValue()) {
            technos.add(Technos.JS);
        }
        if (gwt.getValue()) {
            technos.add(Technos.GWT);
        }
        if (sql.getValue()) {
            technos.add(Technos.SQL);
        }
        if (nosql.getValue()) {
            technos.add(Technos.NOSQL);
        }

        if (isNew) {

            final Employee employee = new Employee();
            employee.setName(name);
            employee.setTechnos(technos);
            pgu_test_widget_employees.saveEmployee(employee);

        } else {

            currentEmployee.setName(name);
            currentEmployee.setTechnos(technos);
            pgu_test_widget_employees.saveEmployee(currentEmployee);
        }
    }

}

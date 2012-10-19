package pgu.widget.employees.client;

import java.util.ArrayList;

import pgu.widget.employees.shared.Employee;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class EmployeesUI extends Composite {

    private static EmployeesUIUiBinder uiBinder = GWT.create(EmployeesUIUiBinder.class);

    interface EmployeesUIUiBinder extends UiBinder<Widget, EmployeesUI> {
    }

    @UiField
    HTML employeesArea;
    @UiField
    Button addBtn;

    private final Pgu_test_widget_employees pgu_test_widget_employees;

    public EmployeesUI(final Pgu_test_widget_employees pgu_test_widget_employees) {
        initWidget(uiBinder.createAndBindUi(this));

        this.pgu_test_widget_employees = pgu_test_widget_employees;
    }

    @UiHandler("addBtn")
    public void clickOnAdd(final ClickEvent e) {
        pgu_test_widget_employees.goToNewEntity();
    }

    public void showEmployees(final ArrayList<Employee> employees) {
        final String nb = "" + employees.size();
        pgu_test_widget_employees.sendTitleToContainer(nb);

        final StringBuilder sb = new StringBuilder();

        sb.append(""+ //
                "<table class=\"table table-bordered table-striped\" style=\"margin-top: 20px;\">"+ //
                "<thead> "+ //
                " <tr> "+ //
                "  <th>Employees</th>"+ //
                " </tr> "+ //
                "</thead> "+ //
                "<tbody ui:field=\"tableBody\">"+ //
                "" //
                );

        for (final Employee employee: employees) {

            sb.append("" + //
                    "<tr>" + //
                    " <td>" + //
                    employee.getName() + //
                    " </td>" + //
                    " <td>" + //
                    " <a href=\"javascript:;\" class=\"btn btn-primary\" onclick=\"edit_employee(''+" + employee.getId() + ");return false;\"><i class=\"icon-pencil\"></i></a>" + //
                    " </td>" + //
                    "</tr>" + //
                    "" //
                    );
        }

        sb.append("" + //
                " </tbody>"  + //
                "</table>"  + //
                "" //
                );

        employeesArea.setHTML(sb.toString());
    }

}

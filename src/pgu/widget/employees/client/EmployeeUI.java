package pgu.widget.employees.client;

import com.github.gwtbootstrap.client.ui.Button;
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

    private final Pgu_test_widget_employees pgu_test_widget_employees;

    public EmployeeUI(final Pgu_test_widget_employees pgu_test_widget_employees) {
        initWidget(uiBinder.createAndBindUi(this));

        this.pgu_test_widget_employees = pgu_test_widget_employees;
    }

    private Integer current_id = null;
    private boolean isNew = true;

    public void setEmployeeId(final String id) {

        GWT.log("employee id " + id);

        isNew = null == id;
        if (isNew) {
            current_id = null;
            nameBox.setText("");

        } else {
            current_id = Integer.valueOf(id);
            final Employee employee = Pgu_test_widget_employees.id2employees.get(current_id);
            nameBox.setText(employee.getName());

        }
    }

    @UiHandler("saveBtn")
    public void clickOnSave(final ClickEvent e) {
        final String name = nameBox.getText();
        if (isNew && "".equals(name.trim())) {
            return;
        }

        if (isNew) {

            Integer lastId = -1;
            for (final Integer id : Pgu_test_widget_employees.id2employees.keySet()) {
                lastId = id;
            }

            lastId++;

            final Employee employee = new Employee();
            employee.setId(lastId);
            employee.setName(name);

            Pgu_test_widget_employees.id2employees.put(employee.getId(), employee);

        } else {
            final Employee employee = Pgu_test_widget_employees.id2employees.get(current_id);
            employee.setName(name);
        }

        pgu_test_widget_employees.goToEntities();
    }

}

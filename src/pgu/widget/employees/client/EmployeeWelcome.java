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

public class EmployeeWelcome extends Composite {

    private static EmployeeWelcomeUiBinder uiBinder = GWT.create(EmployeeWelcomeUiBinder.class);

    interface EmployeeWelcomeUiBinder extends UiBinder<Widget, EmployeeWelcome> {
    }

    @UiField
    Button sendBtn;
    @UiField
    TextBox nbEmployeesBox;

    public EmployeeWelcome() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("sendBtn")
    public void clickSend(final ClickEvent e) {
        pgu_test_widget_employees.sendNotificationToContainer(getNbEmployees());
    }

    public String getNbEmployees() {
        return nbEmployeesBox.getText();
    }

    private Pgu_test_widget_employees pgu_test_widget_employees;

    public void setPresenter(final Pgu_test_widget_employees pgu_test_widget_employees) {
        this.pgu_test_widget_employees = pgu_test_widget_employees;
    }

}

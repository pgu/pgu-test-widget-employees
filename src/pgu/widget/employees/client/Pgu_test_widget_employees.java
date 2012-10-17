package pgu.widget.employees.client;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class Pgu_test_widget_employees implements EntryPoint {

    private final GreetingServiceAsync             greetingService = GWT.create(GreetingService.class);

    public static LinkedHashMap<Integer, Employee> id2employees    = new LinkedHashMap<Integer, Employee>();
    static {
        for (int i = 0; i < 5; i++) {

            final Employee employee = new Employee();
            employee.setId(i);
            employee.setName("John Smith" + i);

            id2employees.put(employee.getId(), employee);
        }
    }

    final SimplePanel                              simplePanel     = new SimplePanel();
    private EmployeesUI                            employeesUI     = null;
    private EmployeeUI                             employeeUI      = null;

    private native void log(String msg) /*-{
        $wnd.console.log("employees: " + msg);
    }-*/;

    @Override
    public void onModuleLoad() {

        exportJSMethod();

        RootPanel.get().add(simplePanel);

        employeeUI = new EmployeeUI(this);
        employeesUI = new EmployeesUI(this);

        log("has container? " + hasContainer());

        if (hasContainer()) {
            sendNotificationToContainer("");
            sendHistoryTokenToContainer(TOKEN_EMPLOYEES);

        } else {
            showEmployeesView();
        }

        History.addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(final ValueChangeEvent<String> event) {
                final String token = event.getValue();
                log("history: " + token);

                if (TOKEN_EMPLOYEE.equals(token) //
                        || token.contains(TOKEN_EMPLOYEE + ":")) {


                    if (hasContainer()) {
                        sendHistoryTokenToContainer(token);

                    } else {

                        showEmployeeView(token);
                    }

                } else if (TOKEN_EMPLOYEES.equals(token) //
                        || "".equals(token)) {

                    if (hasContainer()) {
                        sendHistoryTokenToContainer(TOKEN_EMPLOYEES);

                    } else {
                        showEmployeesView();

                    }

                } else {
                    throw new UnsupportedOperationException("Unknown token " + token);
                }

            }

        });

        listenToMessage(functionToApplyOnContainerAction(this));
    }

    private void showEmployeeView(final String token) {

        String employee_id = null;
        if (TOKEN_EMPLOYEE.equals(token)) {
            employee_id = null;

        } else {

            final String[] parts = token.split(":");
            if (parts.length == 2) {
                employee_id = parts[1];
            } else {
                employee_id = null;
            }
        }

        simplePanel.setWidget(employeeUI);
        employeeUI.setEmployeeId(employee_id);
    }

    private void showEmployeesView() {
        simplePanel.setWidget(employeesUI);
        employeesUI.showEmployees();
    }

    private native boolean hasContainer() /*-{
		return $wnd.parent //
				&& $wnd !== $wnd.parent;
    }-*/;

    private native void sendHistoryTokenToContainer(final String token) /*-{

		var notification = {};
		notification.type = 'history';
		notification.id = 'employees';
		notification.token = token;

		var msg_back = JSON.stringify(notification);
		$wnd.console.log(msg_back);

		if ($wnd.parent //
				&& $wnd !== $wnd.parent) {

			$wnd.parent.postMessage(msg_back, 'http://localhost:8080');
			$wnd.parent.postMessage(msg_back, 'http://127.0.0.1:8888');
		}

    }-*/;

    private native void exportJSMethod() /*-{
       $wnd.edit_employee =
          $entry(@pgu.widget.employees.client.Pgu_test_widget_employees::editEmployee(Ljava/lang/String;));
    }-*/;

    private static final String TOKEN_EMPLOYEES = "employees";
    private static final String TOKEN_EMPLOYEE  = "employee";

    public static void editEmployee(final String id) {
        String suffix_id = "";

        if (null == id || "".equals(id.trim().toString())) {
            suffix_id = "";

        } else {
            suffix_id = id;
        }
        History.newItem(TOKEN_EMPLOYEE + ":" + suffix_id);
    }

    public void goToNewEntity() {
        editEmployee(null);
    }

    public void goToEntities() {
        History.newItem(TOKEN_EMPLOYEES);
    }

    // private native void sendToContainerWidgetIsLoaded() /*-{
    //
    // var notification = {};
    // notification.type = 'title';
    // notification.id = 'employees';
    // notification.title = 'Employees';
    //
    // var msg_back = JSON.stringify(notification);
    // $wnd.console.log(msg_back);
    // $wnd.console.log($wnd.parent);
    //
    // if ($wnd.parent //
    // && $wnd !== $wnd.parent) {
    //
    // $wnd.parent.postMessage(msg_back, 'http://localhost:8080');
    // }
    //
    // }-*/;

    public native void sendNotificationToContainer(String nb) /*-{

		var title = '';
		if (nb === null || nb.trim() === '') {
			title = 'Employees';
		} else {
			title = 'Employees (' + nb + ')';
		}

		var notification = {};
		notification.type = 'title';
		notification.id = 'employees';
		notification.title = title;

		var msg_back = JSON.stringify(notification);
		$wnd.console.log(msg_back);

		$wnd.console.log($wnd.parent);

		if ($wnd.parent //
				&& $wnd !== $wnd.parent) {

			$wnd.parent.postMessage(msg_back, 'http://localhost:8080');
			$wnd.parent.postMessage(msg_back, 'http://127.0.0.1:8888');
		}

    }-*/;

    private native void listenToMessage(JavaScriptObject fn_to_apply) /*-{
		$wnd.addEventListener('message', fn_to_apply, false);
    }-*/;

    private native JavaScriptObject functionToApplyOnContainerAction(Pgu_test_widget_employees activity) /*-{

     return function receiver(e) {

             $wnd.console.log('employees');
             $wnd.console.log(e);

             if (e.origin === 'http://localhost:8080' //
                 || e.origin === 'http://127.0.0.1:8888' //
                 ) {

                 var msg = JSON.parse(e.data);

                 if (msg.type === 'history') {

                    var token = msg.token;
                    activity.@pgu.widget.employees.client.Pgu_test_widget_employees::show(Ljava/lang/String;)(token);

                 } else {
                     $wnd.console.log('Unsupported action ' + msg.action);
                 }

             } else {
                 $wnd.console.log('Unsupported origin');
             }
         }

     }-*/;

    public void show(final String token) {

        log("show: " + token);

        if (TOKEN_EMPLOYEE.equals(token) //
                || token.contains(TOKEN_EMPLOYEE + ":")) {

            showEmployeeView(token);

        } else if (TOKEN_EMPLOYEES.equals(token) //
                || "".equals(token)) {

            showEmployeesView();

        } else {
            throw new UnsupportedOperationException("Unknown token " + token);
        }

    }

}

package pgu.widget.employees.client;

import java.util.ArrayList;
import java.util.HashMap;

import pgu.widget.employees.shared.Employee;

import com.github.gwtbootstrap.client.ui.Brand;
import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Navbar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;

public class Pgu_test_widget_employees implements EntryPoint {

    private final GreetingServiceAsync             greetingService = GWT.create(GreetingService.class);

    final SimplePanel                              simplePanel     = new SimplePanel();
    private ChartsUI                               chartsUI     = null;
    private EmployeesUI                            employeesUI     = null;
    private EmployeeUI                             employeeUI      = null;

    private native void log(String msg) /*-{
        $wnd.console.log("employees: " + msg);
    }-*/;

    @Override
    public void onModuleLoad() {

        final Runnable onLoadCallback = new Runnable() {
            @Override
            public void run() {
                onModuleLoaded();
            }

        };

        VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
    }

    private void onModuleLoaded() {
        exportJSMethod();

        final HTMLPanel appView = new HTMLPanel("");
        addMenuApp(appView);
        appView.add(simplePanel);

        RootPanel.get().add(appView);

        employeeUI = new EmployeeUI(this);
        employeesUI = new EmployeesUI(this);
        chartsUI = new ChartsUI(this);

        log("has container? " + hasContainer());

        final String href = Window.Location.getHref();
        log("href: " + href);

        if (href.contains("#")) {

            final String[] parts = href.split("#");
            if (parts.length == 2) {

                final String place = parts[1];
                show(place);

            } else {
                showEmployeesView();
            }
        } else {
            showEmployeesView();
        }

        if (hasContainer()) {
            //            sendTitleToContainer("");
            //            sendHistoryTokenToContainer(TOKEN_EMPLOYEES);

        } else {

        }

        History.addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(final ValueChangeEvent<String> event) {
                final String token = event.getValue();
                log("history: " + token);

                if (TOKEN_EMPLOYEE.equals(token) //
                        || token.startsWith(TOKEN_EMPLOYEE + ":")) {


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

                } else if ( //
                        TOKEN_CHARTS.equals(token) //
                        ) {

                    if (hasContainer()) {
                        sendHistoryTokenToContainer(TOKEN_CHARTS);

                    } else {
                        showChartsView();

                    }

                } else {
                    throw new UnsupportedOperationException("Unknown token " + token);
                }

            }

        });

        listenToMessage(functionToApplyOnContainerAction(this));
    }

    private void addMenuApp(final HTMLPanel appView) {
        if (!hasContainer()) {

            final NavLink employeesLink = new NavLink("Employees");
            final NavLink chartsLink = new NavLink("Overview");

            final Nav nav = new Nav();
            nav.add(employeesLink);
            nav.add(chartsLink);

            final Brand brand = new Brand("My Company");

            final Navbar menu = new Navbar();
            menu.addStyleName("navbar-fixed-top");
            menu.add(brand);
            menu.add(nav);

            appView.add(menu);

            employeesLink.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(final ClickEvent event) {
                    goToEntities();
                }
            });

            chartsLink.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(final ClickEvent event) {
                    goToCharts();
                }
            });
        }
    }

    private void showEmployeeView(final String place) {

        String employeeId = null;
        if (TOKEN_EMPLOYEE.equals(place)) {
            employeeId = null;

        } else {

            final String[] parts = place.split(":");
            if (parts.length == 2) {
                employeeId = parts[1];
            } else {
                employeeId = null;
            }
        }

        simplePanel.setWidget(employeeUI);

        if (null == employeeId || "".equals(employeeId.trim())) {
            employeeUI.setEmployee(null);

        } else {
            greetingService.fetchEmployee(employeeId, new AsyncCallback<Employee>() {

                @Override
                public void onFailure(final Throwable caught) {
                    throw new RuntimeException(caught);
                }

                @Override
                public void onSuccess(final Employee result) {
                    employeeUI.setEmployee(result);
                }

            });

        }

    }

    private void showChartsView() {
        simplePanel.setWidget(chartsUI);

        greetingService.fetchDistribution(new AsyncCallback<HashMap<String, Integer>>() {

            @Override
            public void onFailure(final Throwable caught) {
                throw new RuntimeException(caught);
            }

            @Override
            public void onSuccess(final HashMap<String, Integer> result) {
                chartsUI.showDistribution(result);
            }

        });
    }

    private void showEmployeesView() {
        simplePanel.setWidget(employeesUI);

        greetingService.fetchEmployees(new AsyncCallback<ArrayList<Employee>>() {

            @Override
            public void onFailure(final Throwable caught) {
                throw new RuntimeException(caught);
            }

            @Override
            public void onSuccess(final ArrayList<Employee> result) {
                employeesUI.showEmployees(result);
            }
        });

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
    private static final String TOKEN_CHARTS  = "charts";

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

    public void goToCharts() {
        History.newItem(TOKEN_CHARTS);
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

    public native void sendTitleToContainer(String nb) /*-{

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

                    var place = msg.place;
                    activity.@pgu.widget.employees.client.Pgu_test_widget_employees::show(Ljava/lang/String;)(place);

                 } else {
                     $wnd.console.log('Unsupported action ' + msg.action);
                 }

             } else {
                 $wnd.console.log('Unsupported origin');
             }
         }

     }-*/;

    public void show(final String place) {

        log("show: " + place);

        if ( //
                "".equals(place) //
                || TOKEN_EMPLOYEES.equals(place) //
                ) {

            showEmployeesView();

        } else if ( //
                TOKEN_EMPLOYEE.equals(place) //
                || place.startsWith(TOKEN_EMPLOYEE + ":") //
                ) {

            showEmployeeView(place);

        } else if ( //
                TOKEN_CHARTS.equals(place) //
                ) {

            showChartsView();

        } else {
            throw new UnsupportedOperationException("Unknown token " + place);
        }

    }

    public void saveEmployee(final Employee employee) {
        greetingService.saveEmployee(employee, new AsyncCallback<Void>() {

            @Override
            public void onFailure(final Throwable caught) {
                sendNotificationToContainer("A technical problem occurred while saving the employee " + employee.getName() + ".");
                // stays on the edition
            }

            @Override
            public void onSuccess(final Void result) {
                sendNotificationToContainer("The employee " + employee.getName() + " has been successfully saved!");
                employeeUI.clearView();
                goToEntities();
            }

        });
    }

    public native void sendNotificationToContainer(String msg) /*-{

        var notification = {};
        notification.type = 'notification';
        notification.id = 'employees';
        notification.msg = msg;

        var msg_back = JSON.stringify(notification);
        $wnd.console.log(msg_back);

        if ($wnd.parent //
                && $wnd !== $wnd.parent) {

            $wnd.parent.postMessage(msg_back, 'http://localhost:8080');
            $wnd.parent.postMessage(msg_back, 'http://127.0.0.1:8888');
        }

    }-*/;

}

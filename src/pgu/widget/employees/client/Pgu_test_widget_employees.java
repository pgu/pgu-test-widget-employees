package pgu.widget.employees.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.RootPanel;

public class Pgu_test_widget_employees implements EntryPoint {

    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    @Override
    public void onModuleLoad() {
        final EmployeeWelcome welcome = new EmployeeWelcome();
        RootPanel.get().add(welcome);

        listenToMessage(functionToApplyOnContainerAction(welcome));
    }

    private native void listenToMessage(JavaScriptObject fn_to_apply) /*-{
        $wnd.addEventListener('message', fn_to_apply, false);
    }-*/;

    private native JavaScriptObject functionToApplyOnContainerAction(EmployeeWelcome view) /*-{

        return function receiver(e) {

            $wnd.console.log('employees');
            $wnd.console.log(e);

              if (e.origin === 'http://localhost:8080') {
                  var msg = JSON.parse(e.data);

                  if (msg.action === 'update_menu') {

                    var nb = view.@pgu.widget.employees.client.EmployeeWelcome::getNbEmployees()();

                    var response = {};
                    response.id = 'employees';
                    response.count = nb;

                    var msg_back = JSON.stringify(response);
                    $wnd.console.log(msg_back);

                    e.source.parent.postMessage(msg_back, e.origin);

                  } else {
                    $wnd.console.log('Unsupported action ' + msg.action);

                  }

              } else {
                    $wnd.console.log('Unsupported origin');

              }
        }

    }-*/;

}

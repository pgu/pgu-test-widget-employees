package pgu.widget.employees.client;

import java.util.ArrayList;
import java.util.HashMap;

import pgu.widget.employees.shared.Employee;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
    void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

    void fetchEmployees(AsyncCallback<ArrayList<Employee>> asyncCallback);

    void fetchEmployee(String employee_id, AsyncCallback<Employee> asyncCallback);

    void saveEmployee(Employee employee, AsyncCallback<Void> asyncCallback);

    void fetchDistribution(AsyncCallback<HashMap<String, Integer>> asyncCallback);
}

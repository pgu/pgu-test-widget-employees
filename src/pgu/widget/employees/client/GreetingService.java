package pgu.widget.employees.client;

import java.util.ArrayList;
import java.util.HashMap;

import pgu.widget.employees.shared.Employee;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
    String greetServer(String name) throws IllegalArgumentException;

    ArrayList<Employee> fetchEmployees();

    Employee fetchEmployee(String employee_id);

    void saveEmployee(Employee employee);

    HashMap<String, Integer> fetchDistribution();

    void deleteEmployee(Integer id);
}

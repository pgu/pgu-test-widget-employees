package pgu.widget.employees.server;

import java.util.ArrayList;
import java.util.HashMap;

import pgu.widget.employees.client.GreetingService;
import pgu.widget.employees.shared.Employee;
import pgu.widget.employees.shared.FieldVerifier;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

    @Override
    public String greetServer(String input) throws IllegalArgumentException {
        // Verify that the input is valid.
        if (!FieldVerifier.isValidName(input)) {
            // If the input is not valid, throw an IllegalArgumentException back to
            // the client.
            throw new IllegalArgumentException("Name must be at least 4 characters long");
        }

        final String serverInfo = getServletContext().getServerInfo();
        String userAgent = getThreadLocalRequest().getHeader("User-Agent");

        // Escape data from the client to avoid cross-site script vulnerabilities.
        input = escapeHtml(input);
        userAgent = escapeHtml(userAgent);

        return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>"
        + userAgent;
    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     * 
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(final String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    @Override
    public ArrayList<Employee> fetchEmployees() {

        final ArrayList<Employee> es = new ArrayList<Employee>();
        for (final Employee e : DAO.id2employees.values()) {
            es.add(e);
        }

        return es;
    }

    @Override
    public Employee fetchEmployee(final String employee_id) {
        return DAO.id2employees.get(Integer.valueOf(employee_id));
    }

    @Override
    public void saveEmployee(final Employee employee) {
        final Integer id = employee.getId();

        if (id== null) {

            Integer max = 0;
            for (final Integer eId : DAO.id2employees.keySet()) {
                max = Math.max(max, eId);
            }
            max++;

            employee.setId(max);
            DAO.id2employees.put(max, employee);

        } else {
            DAO.id2employees.put(id, employee);
        }
    }

    @Override
    public HashMap<String, Integer> fetchDistribution() {

        final HashMap<String, Integer> distribution = new HashMap<String, Integer>();

        for (final Employee e : DAO.id2employees.values()) {
            if (e.getTechnos() == null) {
                continue;
            }

            for (final String t : e.getTechnos()) {

                if (distribution.containsKey(t)) {
                    distribution.put(t, distribution.get(t) + 1);
                } else {
                    distribution.put(t, 1);
                }
            }
        }

        return distribution;
    }
}

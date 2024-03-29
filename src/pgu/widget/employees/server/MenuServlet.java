package pgu.widget.employees.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final int empNb = DAO.id2employees.size();

        String empTitle = "Employees";
        if (empNb > 0) {
            empTitle += " (" + empNb + ")";
        }

        resp.getWriter().print(getMenu(empTitle));
    }

    public static String getMenu(final String empTitle) {
        final String menuJson = "" + //
                "{'entries':                                " + //
                "           [                               " + //
                "            {                              " + //
                "              'code':'0'                   " + //
                "             ,'title':'%s'                 " + //
                "            }                              " + //
                "            ,{                             " + //
                "              'code':'1'                   " + //
                "             ,'title':'Overview'           " + //
                "             ,'place':'charts'             " + //
                "            }                              " + //
                "           ]                               " + //
                "}                                          " + //
                ""; //

        return String.format( //
                menuJson //
                .replaceAll("'", "\"") //
                .replaceAll("\\s", "") //
                //
                , empTitle)
                ;
    }
    //
    //    public static void main(final String[] args) {
    //        System.out.println(MenuServlet.getMenu());
    //    }

}

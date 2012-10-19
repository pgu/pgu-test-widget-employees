package pgu.widget.employees.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;

public class ChartsUI extends Composite {

    private static ChartsUIUiBinder uiBinder = GWT.create(ChartsUIUiBinder.class);

    interface ChartsUIUiBinder extends UiBinder<Widget, ChartsUI> {
    }

    @UiField
    HTMLPanel container;

    Pgu_test_widget_employees presenter;

    public ChartsUI(final Pgu_test_widget_employees presenter) {
        initWidget(uiBinder.createAndBindUi(this));

        this.presenter = presenter;
    }

    private PieOptions createOptions() {
        final PieOptions options = PieOptions.create();
        options.setWidth(800);
        options.setHeight(480);
        options.set3D(true);
        options.setTitle("Employees by technologies");
        return options;
    }

    private SelectHandler createSelectHandler(final PieChart chart) {
        return new SelectHandler() {
            @Override
            public void onSelect(final SelectEvent event) {
                String message = "";

                final JsArray<Selection> selections = chart.getSelections();

                for (int i = 0; i < selections.length(); i++) {
                    message += i == 0 ? "" : "\n";

                    final Selection selection = selections.get(i);

                    if (selection.isCell()) {
                        final int row = selection.getRow();
                        final int column = selection.getColumn();

                        message += "cell " + row + ":" + column + " selected";

                    } else if (selection.isRow()) {

                        final int row = selection.getRow();
                        message += "row " + row + " selected";

                    } else {
                        // unreachable
                        message += "Pie chart selections should be either row selections or cell selections.";
                        message += "  Other visualizations support column selections as well.";
                    }
                }

                Window.alert(message);
            }
        };
    }

    private final Comparator<Integer> comp = new Comparator<Integer>() {

        @Override
        public int compare(final Integer o1, final Integer o2) {
            return - o1.compareTo(o2);
        }
    };

    private AbstractDataTable createTable(final HashMap<String, Integer> techno2nbEmployees) {

        final TreeMap<Integer, ArrayList<String>> nb2techno = new TreeMap<Integer, ArrayList<String>>(comp);
        for (final Entry<String, Integer> e : techno2nbEmployees.entrySet()) {

            final Integer nb = e.getValue();
            final String t = e.getKey();

            if (nb2techno.containsKey(nb)) {
                nb2techno.get(nb).add(t);
                Collections.sort(nb2techno.get(nb));

            } else {
                final ArrayList<String> ts = new ArrayList<String>();
                ts.add(t);
                nb2techno.put(nb, ts);
            }

        }



        final DataTable data = DataTable.create();

        data.addColumn(ColumnType.STRING, "Techno");
        data.addColumn(ColumnType.NUMBER, "Employees per techno");

        data.addRows(techno2nbEmployees.size());

        int row = 0;
        for (final Entry<Integer, ArrayList<String>> e : nb2techno.entrySet()) {
            final Integer nb = e.getKey();

            for (final String t : e.getValue()) {
                data.setValue(row, 0, t);
                data.setValue(row, 1, nb);

                row++;
            }
        }

        return data;
    }

    public void showDistribution(final HashMap<String, Integer> techno2nbEmployees) {

        container.clear();

        final PieChart pie = new PieChart(createTable(techno2nbEmployees), createOptions());

        pie.addSelectHandler(createSelectHandler(pie));
        container.add(pie);

    }

}

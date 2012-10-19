package pgu.widget.employees.client;

import java.util.HashMap;
import java.util.Map.Entry;

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
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
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

        final Runnable onLoadCallback = new Runnable() {
            @Override
            public void run() {
                // load api...
            }
        };

        VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
    }

    private PieOptions createOptions() {
        final PieOptions options = PieOptions.create();
        options.setWidth(400);
        options.setHeight(240);
        options.set3D(true);
        options.setTitle("Nb of employees by technologies");
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

    private AbstractDataTable createTable(final HashMap<String, Integer> techno2nbEmployees) {

        final DataTable data = DataTable.create();

        data.addColumn(ColumnType.STRING, "Techno");
        data.addColumn(ColumnType.NUMBER, "Employees per techno");

        data.addRows(techno2nbEmployees.size());

        int row = 0;
        for (final Entry<String, Integer> e : techno2nbEmployees.entrySet()) {

            data.setValue(row, 0, e.getKey());
            data.setValue(row, 1, e.getValue());

            row++;
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

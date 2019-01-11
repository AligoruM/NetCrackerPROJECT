package catalogApp.client.view;

import catalogApp.client.presenter.TabsPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TabPanel;


public class TabsView extends Composite implements TabsPresenter.Display {

    @UiTemplate("TabsView.ui.xml")
    interface MainScreenUiBinder extends UiBinder<Panel, TabsView> {
    }

    @UiField
    TabPanel tabPanel;

    private static MainScreenUiBinder mainScreenUiBinder = GWT.create(MainScreenUiBinder.class);


    public TabsView() {
        initWidget(mainScreenUiBinder.createAndBindUi(this));
    }

    @Override
    public TabPanel getTabPanel() {
        return tabPanel;
    }
}

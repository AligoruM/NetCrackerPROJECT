package catalogApp.client.view.mainPage;

import catalogApp.client.presenter.TabsPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TabPanel;


public class TabPanelView extends Composite implements TabsPresenter.Display {

    @UiTemplate("TabPanelView.ui.xml")
    interface TabPanelViewUiBinder extends UiBinder<Panel, TabPanelView> {
    }

    @UiField
    TabPanel tabPanel;

    private static TabPanelViewUiBinder tabPanelViewUiBinder = GWT.create(TabPanelViewUiBinder.class);


    public TabPanelView() {
        initWidget(tabPanelViewUiBinder.createAndBindUi(this));
    }

    @Override
    public TabPanel getTabPanel() {
        return tabPanel;
    }
}

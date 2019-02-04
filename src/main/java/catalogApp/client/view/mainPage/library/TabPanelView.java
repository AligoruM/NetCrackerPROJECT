package catalogApp.client.view.mainPage.library;

import catalogApp.client.presenter.TabPanelPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TabPanel;


public class TabPanelView extends Composite implements TabPanelPresenter.Display {

    interface TabPanelViewUiBinder extends UiBinder<Panel, TabPanelView> {
    }

    @UiField
    TabPanel tabPanel;
    @UiField
    Button addButton;
    @UiField
    Button addToLib;
    @UiField
    Button editItem;

    private static TabPanelViewUiBinder tabPanelViewUiBinder = GWT.create(TabPanelViewUiBinder.class);


    public TabPanelView() {
        initWidget(tabPanelViewUiBinder.createAndBindUi(this));
    }

    @Override
    public Button getAddButton() {
        return addButton;
    }

    @Override
    public Button getAddToLibButton() {
        return addToLib;
    }

    @Override
    public Button getEditButton() {
        return editItem;
    }

    @Override
    public TabPanel getTabPanel() {
        return tabPanel;
    }
}

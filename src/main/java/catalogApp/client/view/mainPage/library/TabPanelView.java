package catalogApp.client.view.mainPage.library;

import catalogApp.client.CatalogController;
import catalogApp.client.presenter.TabPanelPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
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
    @UiField
    Button archiveButton;
    @UiField
    Button restoreButton;

    private static TabPanelViewUiBinder tabPanelViewUiBinder = GWT.create(TabPanelViewUiBinder.class);


    public TabPanelView() {
        initWidget(tabPanelViewUiBinder.createAndBindUi(this));
        if(!CatalogController.isAdmin()){
            hideButtons();
        }
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
    public Button getArchiveButton() {
        return archiveButton;
    }

    @Override
    public Button getRestoreButton() {
        return restoreButton;
    }

    @Override
    public TabPanel getTabPanel() {
        return tabPanel;
    }

    private void hideButtons(){
        addButton.getElement().getStyle().setVisibility(Style.Visibility.HIDDEN);
        editItem.getElement().getStyle().setVisibility(Style.Visibility.HIDDEN);
        archiveButton.getElement().getStyle().setVisibility(Style.Visibility.HIDDEN);
        restoreButton.getElement().getStyle().setVisibility(Style.Visibility.HIDDEN);
        addButton.getElement().getStyle().setWidth(0, Style.Unit.PX);
        editItem.getElement().getStyle().setWidth(0, Style.Unit.PX);
        archiveButton.getElement().getStyle().setWidth(0, Style.Unit.PX);
        restoreButton.getElement().getStyle().setWidth(0, Style.Unit.PX);
    }
}

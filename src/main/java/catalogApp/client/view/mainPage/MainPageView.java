package catalogApp.client.view.mainPage;

import catalogApp.client.CatalogController;
import catalogApp.client.presenter.MainPagePresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class MainPageView extends Composite implements MainPagePresenter.Display {
    interface MainPageViewUiBinder extends UiBinder<HTMLPanel, MainPageView> {

    }
    @UiField
    ToggleButton profileButton;
    @UiField
    ToggleButton libButton;
    @UiField
    ToggleButton userButton;
    @UiField
    ToggleButton settingButton;
    @UiField
    SimplePanel mainPanel;
    @UiField
    HTMLPanel profileBarPanel;

    private static MainPageViewUiBinder ourUiBinder = GWT.create(MainPageViewUiBinder.class);

    public MainPageView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        if(!CatalogController.isAdmin()) {
            userButton.getElement().getStyle().setVisibility(Style.Visibility.HIDDEN);
        }
    }

    @Override
    public SimplePanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public HTMLPanel getProfilePanel() {
        return profileBarPanel;
    }

    @Override
    public ToggleButton getProfileButton() {
        return profileButton;
    }

    @Override
    public ToggleButton getLibraryButton() {
        return libButton;
    }

    @Override
    public ToggleButton getUsersButton() {
        return userButton;
    }

    @Override
    public ToggleButton getSettingsButton() {
        return settingButton;
    }
}
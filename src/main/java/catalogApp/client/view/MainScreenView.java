package catalogApp.client.view;

import catalogApp.client.presenter.MainScreenPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;

public class MainScreenView extends Composite implements MainScreenPresenter.Display {
    @UiTemplate("MainScreenView.ui.xml")
    interface MainScreenUiBinder extends UiBinder<Panel, MainScreenView> {
    }

    private static MainScreenUiBinder mainScreenUiBinder = GWT.create(MainScreenUiBinder.class);


    public MainScreenView() {
        initWidget(mainScreenUiBinder.createAndBindUi(this));
        initContent();
    }

    private void initContent() {


    }


    @Override
    public HasClickHandlers getAddButton() {
        return null;
    }

    @Override
    public HasClickHandlers getCancelButton() {
        return null;
    }

    @Override
    public Label getWarningLabel() {
        return null;
    }

    @Override
    public TextArea getTimeTA() {
        return null;
    }

    @Override
    public void hideOwnerField() {

    }
}

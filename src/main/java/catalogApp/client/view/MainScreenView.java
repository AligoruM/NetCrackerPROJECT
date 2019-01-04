package catalogApp.client.view;

import catalogApp.client.presenter.MainScreenPresenter;
import catalogApp.client.services.TestService;
import catalogApp.shared.model.Book;
import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class MainScreenView extends Composite implements MainScreenPresenter.Display {
    @UiTemplate("MainScreenView.ui.xml")
    interface MainScreenUiBinder extends UiBinder<Panel, MainScreenView> {
    }

    private static MainScreenUiBinder mainScreenUiBinder = GWT.create(MainScreenUiBinder.class);
    private TestService testService = GWT.create(TestService.class);

    @UiField
    Label nameLbl;
    @UiField
    TextArea nameTA;
    @UiField
    Button testButton;


    public MainScreenView() {
        initWidget(mainScreenUiBinder.createAndBindUi(this));

        initContent();
    }

    private void initContent() {
        testButton.addClickHandler(event -> {
            testService.getAllBooks(new MethodCallback<List<Book>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    Window.alert("doesnt work!");
                }

                @Override
                public void onSuccess(Method method, List<Book> list) {
                    Window.alert(list.get(0).toString());
                }
            });
        });

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

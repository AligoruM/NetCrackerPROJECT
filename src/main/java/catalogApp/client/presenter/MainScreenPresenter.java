package catalogApp.client.presenter;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class MainScreenPresenter  {

    private Display display;

    public interface Display {

        HasClickHandlers getAddButton();

        HasClickHandlers getCancelButton();

        Label getWarningLabel();

        TextArea getTimeTA();

        void hideOwnerField();

        Widget asWidget();
    }

    public MainScreenPresenter(Display view) {
        this.display = view;
    }

    public void go(final HasWidgets container) {
        container.clear();
        container.add(display.asWidget());
    }
}

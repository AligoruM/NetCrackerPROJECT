package catalogApp.client.presenter;

import catalogApp.client.event.ClosedDialogEvent;
import catalogApp.client.services.AuthWebService;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProfilePopupPresenter implements Presenter {
    public interface Display{
        Button getCloseButton();
        Widget asWidget();
        void hideDialog();
        void showDialog();
    }


    private Display display;
    private AuthWebService authWebService;
    private HandlerManager eventBus;

    public ProfilePopupPresenter(Display display, AuthWebService authWebService, HandlerManager eventBus) {
        this.display = display;
        this.authWebService = authWebService;
        this.eventBus = eventBus;
        bind();
    }

    @Override
    public void go(DockPanel container) {
        RootPanel.get().add(display.asWidget());
        display.showDialog();
    }
    private void bind(){
        display.getCloseButton().addClickHandler(event -> {
           eventBus.fireEvent(new ClosedDialogEvent());
           display.hideDialog();
        });
    }
}

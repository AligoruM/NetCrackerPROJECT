package catalogApp.client.presenter;

import catalogApp.client.CatalogController;
import catalogApp.client.event.ShowLibraryEvent;
import catalogApp.client.event.ShowProfileEvent;
import catalogApp.client.event.ShowUsersEvent;
import catalogApp.client.view.mainPage.ProfileBarView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class MainPagePresenter implements Presenter {

    public interface Display {
        SimplePanel getMainPanel();
        HTMLPanel getProfilePanel();
        ToggleButton getProfileButton();
        ToggleButton getLibraryButton();
        ToggleButton getUsersButton();
        ToggleButton getSettingsButton();
    }

    private HandlerManager eventBus;
    Display display;

    private ProfileBarPresenter profileBarPresenter;

    public MainPagePresenter(Display display, HandlerManager eventBus) {
        this.eventBus = eventBus;

        this.display = display;

        profileBarPresenter = new ProfileBarPresenter(new ProfileBarView(), eventBus);

        profileBarPresenter.setData(CatalogController.getUser());
        profileBarPresenter.go(display.getProfilePanel());
        bind();
    }

    private void bind(){
        display.getProfileButton().addValueChangeHandler(event -> {
            if(event.getValue()){
                display.getUsersButton().setDown(false);
                display.getLibraryButton().setDown(false);
                display.getSettingsButton().setDown(false);
                eventBus.fireEvent(new ShowProfileEvent());
            }
        });
        display.getUsersButton().addValueChangeHandler(event -> {
           if (event.getValue()){
               display.getProfileButton().setDown(false);
               display.getLibraryButton().setDown(false);
               display.getSettingsButton().setDown(false);
               eventBus.fireEvent(new ShowUsersEvent());
           }
        });
        display.getLibraryButton().addValueChangeHandler(event -> {
            if (event.getValue()){
                display.getProfileButton().setDown(false);
                display.getSettingsButton().setDown(false);
                display.getUsersButton().setDown(false);
                eventBus.fireEvent(new ShowLibraryEvent());
            }
        });

        display.getSettingsButton().addValueChangeHandler(event -> {
            if (event.getValue()){
                display.getProfileButton().setDown(false);
                display.getUsersButton().setDown(false);
                display.getLibraryButton().setDown(false);
                //TODO обработка кнопки
            }
        });
    }

    @Override
    public void go(Panel container) {
    }

    public SimplePanel getPanel(){
        return display.getMainPanel();
    }

    public void updateData(){
        profileBarPresenter.setData(CatalogController.getUser());
    }
}

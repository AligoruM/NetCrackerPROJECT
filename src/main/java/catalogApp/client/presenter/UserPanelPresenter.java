package catalogApp.client.presenter;

import catalogApp.client.services.UserWebService;
import catalogApp.client.view.components.tables.AbstractCatalogCellTable;
import catalogApp.client.view.dialogs.AddUserDialogView;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserPanelPresenter implements Presenter {
    public interface Display {
        Button getAddButton();

        Button getEnableButton();

        Button getBanButton();

        AbstractCatalogCellTable<SimpleUser> getTable();

        void setList(List<SimpleUser> dataProvider);

        Widget asWidget();
    }

    private static ListDataProvider<SimpleUser> dataProvider = new ListDataProvider<>();

    private Display display;
    private AddUserDialogPresenter addUserDialogPresenter;
    private UserWebService userWebService;

    public UserPanelPresenter(Display display, UserWebService userWebService) {
        this.display = display;
        this.userWebService = userWebService;
        addUserDialogPresenter = new AddUserDialogPresenter(new AddUserDialogView(), userWebService);
        display.setList(dataProvider.getList());
        dataProvider.addDataDisplay(display.getTable());
        this.userWebService.getAllUsers(new MethodCallback<List<SimpleUser>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("getAllUsers doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, List<SimpleUser> response) {
                dataProvider.getList().addAll(response);
            }
        });
        bind();
    }

    @Override
    public void go(Panel container) {
        container.clear();
        container.add(display.asWidget());
    }

    private void bind() {
        display.getAddButton().addClickHandler(event -> addUserDialogPresenter.go(null));
        display.getBanButton().addClickHandler(event -> {
            List<Integer> idList = new ArrayList<>();
            Set<SimpleUser> users = ((MultiSelectionModel<SimpleUser>)display.getTable().getSelectionModel()).getSelectedSet();
            if (checkForNoAdmins(users)) {
                users.forEach(e->idList.add(e.getId()));
                userWebService.banUsers(idList, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        Window.alert("Ban doesnt work");
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        dataProvider.getList().forEach(x -> {
                            if (idList.contains(x.getId())) {
                                x.setArchived(true);
                            }
                        });
                        dataProvider.refresh();
                    }
                });
            }
        });

        display.getEnableButton().addClickHandler(event -> {
            List<Integer> idList = new ArrayList<>();
            ((MultiSelectionModel<SimpleUser>)display.getTable().getSelectionModel()).getSelectedSet().forEach(e -> idList.add(e.getId()));
            userWebService.enableUsers(idList, new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    Window.alert("Enable doesnt work");
                }

                @Override
                public void onSuccess(Method method, Void response) {
                    dataProvider.getList().forEach(x -> {
                        if (idList.contains(x.getId())) {
                            x.setArchived(false);
                        }
                    });
                    dataProvider.refresh();
                }
            });
        });
    }

    public static ListDataProvider<SimpleUser> getDataProvider() {
        return dataProvider;
    }

    private boolean checkForNoAdmins(Set<SimpleUser> simpleUsers) {
        for (SimpleUser user : simpleUsers) {
            if (user.getRoles().contains("ADMIN")) {
                Window.alert("You cannot ban admins!");
                return false;
            }
        }
        return true;
    }
}

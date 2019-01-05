package catalogApp.client;

import catalogApp.client.presenter.MainScreenPresenter;
import catalogApp.client.presenter.Presenter;
import catalogApp.client.view.MainScreenView;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasWidgets;

public class CatalogController implements Presenter, ValueChangeHandler<String> {

    public void go(HasWidgets container) {
        Presenter pr = new MainScreenPresenter(new MainScreenView());
        pr.go(container);
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
    }
}

package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class AddAuthorDialogPresenter implements Presenter {

    public interface Display{
        void showDialog();
        void hideDialog();
        Button getSubmitButton();
        Button getCancelButton();
        String getAuthorName();
        Widget asWidget();
    }

    private Display display;
    private BookWebService bookWebService;
    private List<String> authors;

    public AddAuthorDialogPresenter(Display display, BookWebService bookWebService) {
        this.display = display;
        this.bookWebService = bookWebService;
        bind();
    }

    @Override
    public void go(Panel container) {
        display.showDialog();
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    private void bind(){
        display.getCancelButton().addClickHandler(event -> display.hideDialog());

        display.getSubmitButton().addClickHandler(event -> {
            String author = display.getAuthorName();
            if(checkForValid(author))
                bookWebService.addAuthor(author, new MethodCallback<Boolean>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        Window.alert("addAuthor doesnt work");
                    }

                    @Override
                    public void onSuccess(Method method, Boolean response) {
                        if(response){
                            Window.alert("Added");
                            authors.add(0, author);
                            display.hideDialog();
                        }else {
                            Window.alert("Author already exists");
                        }
                    }
                });
        });
    }

    private boolean checkForValid(String name){
        if(name.trim().isEmpty()){
            Window.alert("Cannot be empty");
            return false;
        }
        for (String e : authors) {
            if(name.equalsIgnoreCase(e)) {
                Window.alert("Author already exist");
                return false;
            }
        }
        return true;
    }
}

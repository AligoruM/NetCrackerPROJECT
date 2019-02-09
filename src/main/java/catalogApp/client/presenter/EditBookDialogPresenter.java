package catalogApp.client.presenter;

import catalogApp.client.presenter.helper.EditorInitializeHelper;
import catalogApp.client.services.BookWebService;
import catalogApp.client.view.components.AdditionalInfo;
import catalogApp.client.view.components.FileUploader;
import catalogApp.shared.model.Author;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class EditBookDialogPresenter implements Presenter {

    public interface Display {
        AdditionalInfo getAdditionalInfo();

        Button getCancelButton();

        Button getSubmitButton();

        void showDialog();

        void hideDialog();

        Widget asWidget();

        void showData(Book object);

        String getNewName();

        String getNewAuthor();

    }

    private ListDataProvider<Book> dataProvider;

    private Book book;

    private Display display;

    private BookWebService bookWebService;

    private String oldName;

    private String oldComment;

    private String oldAuthor;

    private boolean isLoaded;


    public EditBookDialogPresenter(Display display, BookWebService bookWebService, ListDataProvider<Book> dataProvider, Book book) {
        this.book = book;
        this.dataProvider = dataProvider;
        this.display = display;
        this.bookWebService = bookWebService;
        oldName = book.getName();
        oldAuthor = book.getAuthor().getName();
        oldComment = book.getComment() != null ? book.getComment() : "";
        bind();
    }

    @Override
    public void go(Panel container) {
        display.showData(book);
        display.showDialog();
    }

    private void bind() {

        FileUploader fileUploader = display.getAdditionalInfo().getFileUploader();

        EditorInitializeHelper.initFileUploader(display.getAdditionalInfo().getUploadButton(), fileUploader);

        fileUploader.addSubmitCompleteHandler(event -> {
            isLoaded = true;
            Window.alert("loaded");
        });


        display.getSubmitButton().addClickHandler(event -> {
            boolean isChanged = false;
            boolean mainFieldsIsChanged = false;
            String newName = display.getNewName();
            String newAuthorName = display.getNewAuthor();
            String newComment = display.getAdditionalInfo().getComment();
            String imagePath = display.getAdditionalInfo().getFileUploader().getFileUpload().getFilename();
            imagePath = imagePath.substring(imagePath.lastIndexOf('\\') + 1);
            Book newBook = new Book();

            newBook.setId(book.getId());

            if (!oldName.equalsIgnoreCase(newName) && !newName.isEmpty()) {
                newBook.setName(newName);
                mainFieldsIsChanged = true;
            } else {
                newBook.setName(book.getName());
            }

            if (!oldAuthor.equalsIgnoreCase(newAuthorName) && !newAuthorName.isEmpty()) {
                Author newAuthor = new Author();
                newAuthor.setName(newAuthorName);
                newBook.setAuthor(newAuthor);
                mainFieldsIsChanged = true;
            } else {
                newBook.setAuthor(book.getAuthor());
            }

            if (!oldComment.equals(newComment)) {
                newBook.setComment(newComment);
                isChanged = true;
            }

            if (!imagePath.isEmpty() && isLoaded) {
                newBook.setImagePath(imagePath);
                isChanged = true;
            }


            if (isChanged || mainFieldsIsChanged) {
                if(!mainFieldsIsChanged){
                    newBook.setAuthor(null);
                    newBook.setName(null);
                }
                bookWebService.updateBook(newBook, new MethodCallback<Book>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("updateBook doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Book response) {
                        if (response != null) {
                            book.setName(response.getName());
                            book.setComment(response.getComment());
                            if (response.getImagePath() != null) {
                                book.setImagePath(response.getImagePath());
                            }
                            if (response.getAuthor() != null) {
                                book.setAuthor(response.getAuthor());
                            }
                            dataProvider.refresh();
                            display.hideDialog();
                        } else {
                            Window.alert("Book like that already exists");
                        }
                    }
                });
            }
        });

        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }
}

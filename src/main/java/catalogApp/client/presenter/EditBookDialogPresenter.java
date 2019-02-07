package catalogApp.client.presenter;

import catalogApp.client.presenter.helper.EditorInitializeHelper;
import catalogApp.client.services.BookWebService;
import catalogApp.client.view.components.AdditionalInfo;
import catalogApp.client.view.components.FileUploader;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
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

    }

    private ListDataProvider<Book> dataProvider;

    private Book book;

    private Display display;

    private BookWebService bookWebService;

    private String oldName;

    private String oldComment;

    private boolean isLoaded;


    public EditBookDialogPresenter(Display display, BookWebService bookWebService, ListDataProvider<Book> dataProvider, Book book) {
        this.book = book;
        this.dataProvider = dataProvider;
        this.display = display;
        this.bookWebService = bookWebService;
        oldName = book.getName();
        oldComment = book.getComment()!=null ? book.getComment() : "";
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

        fileUploader.addSubmitCompleteHandler(event -> isLoaded=true);


        display.getSubmitButton().addClickHandler(event -> {
            boolean isChanged=false;
            String newName = display.getNewName();
            String newComment = display.getAdditionalInfo().getComment();
            String imagePath = display.getAdditionalInfo().getFileUploader().getFileUpload().getFilename();
            imagePath = imagePath.substring(imagePath.lastIndexOf("\\") + 1);
            Book newBook = new Book();

            newBook.setId(book.getId());

            if(!oldName.equals(newName) && !newName.isEmpty()) {
                newBook.setName(newName);
                isChanged=true;
            }
            if(!oldComment.equals(newComment)){
                newBook.setComment(newComment);
                isChanged = true;
            }

            if(!imagePath.isEmpty() && isLoaded){
                newBook.setImagePath(imagePath);
                isChanged=true;
            }


            if(isChanged || isLoaded){
                bookWebService.updateBook(newBook, new MethodCallback<Book>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("updateBook doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Book response) {
                        book.setName(newName);
                        book.setComment(newComment);
                        book.setImagePath(response.getImagePath());
                        dataProvider.refresh();
                        display.hideDialog();
                    }
                });
            }
        });

        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }
}

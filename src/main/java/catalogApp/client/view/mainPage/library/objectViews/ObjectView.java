package catalogApp.client.view.mainPage.library.objectViews;

import catalogApp.shared.model.BaseObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;

import static catalogApp.client.view.constants.LibraryConstants.*;
import static catalogApp.client.view.constants.LibraryConstants.COMMENT_HEIGHT;

public class ObjectView extends Composite {
    @UiTemplate("ObjectView.ui.xml")
    interface ObjectViewUiBinder extends UiBinder<HTMLPanel, ObjectView> {
    }

    @UiField
    Label titleLabel;
    @UiField
    FlexTable table;
    @UiField
    Image objectImage;

    Label name = new Label();
    private DecoratorPanel comment = new DecoratorPanel();
    private HTML commentContainer = new HTML();

    private static ObjectViewUiBinder ourUiBinder = GWT.create(ObjectViewUiBinder.class);

    public ObjectView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    void initTable(String nameLabel) {
        table.setCellSpacing(5);
        setImageSize(IMG_HEIGHT, null);

        table.setWidget(0, 0, new Label(nameLabel));
        table.setWidget(0, 1, name);

        table.setWidget(1, 0, new Label(COMMENT_LABEL));
        comment.setWidget(commentContainer);
        table.setWidget(2, 0, comment);
        table.getFlexCellFormatter().setColSpan(2, 0, 2);

        commentContainer.setWidth(COMMENT_WIDTH);
        commentContainer.setHeight(COMMENT_HEIGHT);
    }


    private void setImageSize(String height, String width) {
        if (height != null)
            objectImage.setHeight(height);
        if (width != null)
            objectImage.setWidth(width);
    }

    void setData(BaseObject object) {
        name.setText(object.getName());
        if (object.getImagePath() != null) {
            if (!objectImage.getUrl().contains(object.getImagePath())) {
                objectImage.setUrl(object.getImagePath());
            }
        } else if(!objectImage.getUrl().contains(DEFAULT_OBJECT_IMAGE)) {
            objectImage.setUrl(DEFAULT_OBJECT_IMAGE);
        }

        if (object.getComment() != null) {
            commentContainer.setHTML(object.getComment());
        } else {
            commentContainer.setHTML(EMPTY_COMMENT);
        }
    }

}

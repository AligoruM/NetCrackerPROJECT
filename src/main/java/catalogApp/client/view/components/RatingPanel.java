package catalogApp.client.view.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;


public class RatingPanel extends Composite {

    private NumberFormat formatter = NumberFormat.getFormat("#.##");

    interface RatingPanelUiBinder extends UiBinder<HTMLPanel, RatingPanel> {
    }

    private static RatingPanelUiBinder ourUiBinder = GWT.create(RatingPanelUiBinder.class);
    @UiField
    Button oneButton;
    @UiField
    Button twoButton;
    @UiField
    Button threeButton;
    @UiField
    Button fourButton;
    @UiField
    Button fiveButton;
    @UiField
    HorizontalPanel mainPanel;
    @UiField
    Label ratingLabel;

    public RatingPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    public void setActive(boolean enabled){
        oneButton.setEnabled(enabled);
        twoButton.setEnabled(enabled);
        threeButton.setEnabled(enabled);
        fourButton.setEnabled(enabled);
        fiveButton.setEnabled(enabled);
    }

    public void setRating(double rating){
        ratingLabel.setText(formatter.format(rating));
    }

    public Button getOneButton() {
        return oneButton;
    }

    public Button getTwoButton() {
        return twoButton;
    }

    public Button getThreeButton() {
        return threeButton;
    }

    public Button getFourButton() {
        return fourButton;
    }

    public Button getFiveButton() {
        return fiveButton;
    }
}
package catalogApp.client.view.components.tables.utils;

import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.IntegerBox;

public class DurationFormatter {
    public static String formatDuration(int duration) {
        if (duration > 0) {
            int intPart = duration / 60;
            int remainder = duration % 60;
            return intPart + ":" + (remainder<10 ? "0"+remainder : remainder);
        } else return "???";
    }

    public static KeyPressHandler durationBoxKeyPressHandler(){
        return event -> {
            if(!Character.isDigit(event.getCharCode()))
                ((IntegerBox)event.getSource()).cancelKey();
        };

    }
}

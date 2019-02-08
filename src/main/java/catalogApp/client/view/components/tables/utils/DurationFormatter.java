package catalogApp.client.view.components.tables.utils;

public class DurationFormatter {
    public static String formatDuration(int duration) {
        if (duration > 0) {
            int intPart = duration / 60;
            int remainder = duration % 60;
            return intPart + ":" + (remainder<10 ? "0"+remainder : remainder);
        } else return "???";
    }
}

package catalogApp.shared.model;

public interface Ratable {
    double getRating() ;

    void setRating(double rating);

    boolean isMarked();

    void setMarked(boolean marked);

    int getId();
}

package catalogApp.shared.model;

public interface Ratable {
    float getRating() ;

    void setRating(float rating);

    boolean isMarked();

    void setMarked(boolean marked);

    int getId();
}

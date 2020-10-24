package seedu.address.model.view;

public class CurrentView {
    private View view;

    public CurrentView (View view) {
        this.view = view;
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }
}

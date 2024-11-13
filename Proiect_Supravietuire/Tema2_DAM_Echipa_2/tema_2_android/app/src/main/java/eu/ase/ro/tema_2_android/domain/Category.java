package eu.ase.ro.tema_2_android.domain;

public enum Category {
    FIRST_AID("First Aid"),
    DISASTERS("Disasters");

    private final String displayedLabel;

    Category(String displayedLabel) {
        this.displayedLabel = displayedLabel;
    }

    public String getDisplayName() {
        return displayedLabel;
    }

    @Override
    public String toString() {
        return displayedLabel;
    }
}

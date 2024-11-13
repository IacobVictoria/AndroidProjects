package eu.ase.ro.tema_2_android.domain;

import java.io.Serializable;
import java.util.List;

public class SurvivalGuide implements Serializable {
    private Category category;
    private List<String> precautionMeasures;
    private int urgencyLevel;
    private String description;
    private String title;
    private int imageResource;

    public SurvivalGuide(Category category, List<String> precautionMeasures, int urgencyLevel, String description, String title, int imageResource) {
        this.category = category;
        this.precautionMeasures = precautionMeasures;
        this.urgencyLevel = urgencyLevel;
        this.description = description;
        this.title = title;
        this.imageResource = imageResource;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrecautionMeasures(List<String> precautionMeasures) {
        this.precautionMeasures = precautionMeasures;
    }

    public void setUrgencyLevel(int urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public Category getCategory() {
        return category;
    }

    public List<String> getPrecautionMeasures() {
        return precautionMeasures;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }


    @Override
    public String toString() {
        return "SurvivalGuide{" +
                "category=" + category +
                ", precautionMeasures=" + precautionMeasures +
                ", urgencyLevel=" + urgencyLevel +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", imageResource=" + imageResource +
                '}';
    }

}
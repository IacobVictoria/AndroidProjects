package eu.ase.ro.tema_2_android.domain;

import java.io.Serializable;
import java.util.List;

public class ExperiencePost implements Serializable {
    private String name;
    private String disasterType;
    private String description;
    private List<String> damages;
    private LossLevel lossLevel;
    private EmotionalDamage emotionalDamage;
    private int levelOfPreparedness;
    private int image;

    public ExperiencePost(String name, String disasterType, String description, List<String> damages, LossLevel lossLevel, EmotionalDamage emotionalDamage, int levelOfPreparedness, int image) {
        this.name = name;
        this.disasterType = disasterType;
        this.description = description;
        this.damages = damages;
        this.lossLevel = lossLevel;
        this.emotionalDamage = emotionalDamage;
        this.levelOfPreparedness = levelOfPreparedness;
        this.image = image;
    }

    public EmotionalDamage getEmotionalDamage() {
        return emotionalDamage;
    }

    public void setEmotionalDamage(EmotionalDamage emotionalDamage) {
        this.emotionalDamage = emotionalDamage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDamages() {
        return damages;
    }

    public void setDamages(List<String> damages) {
        this.damages = damages;
    }

    public LossLevel setLossLevel() {
        return lossLevel;
    }

    public void setLossLevel(LossLevel lossLevel) {
        this.lossLevel = lossLevel;
    }

    public int getLevelOfPreparedness() {
        return levelOfPreparedness;
    }

    public void setLevelOfPreparedness(int levelOfPreparedness) {
        this.levelOfPreparedness = levelOfPreparedness;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ExperiencePost{" +
                "name='" + name + '\'' +
                ", disasterType='" + disasterType + '\'' +
                ", description='" + description + '\'' +
                ", damages=" + damages +
                ", lossLevel=" + lossLevel +
                ", levelOfPreparedness=" + levelOfPreparedness +
                ", image=" + image +
                '}';
    }
}

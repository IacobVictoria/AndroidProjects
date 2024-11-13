package eu.ase.ro.testcomplet1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.temporal.ValueRange;
import java.util.List;

public class Subscription implements Parcelable {
    private Category category;
    private String type;
    private List<String> extra;

    public Subscription(Category category, String type, List<String> extra) {
        this.category = category;
        this.type = type;
        this.extra = extra;
    }

    protected Subscription(Parcel in) {
        category = Category.valueOf(in.readString());
        type = in.readString();
        extra = in.createStringArrayList();
    }

    public static final Creator<Subscription> CREATOR = new Creator<Subscription>() {
        @Override
        public Subscription createFromParcel(Parcel in) {
            return new Subscription(in);
        }

        @Override
        public Subscription[] newArray(int size) {
            return new Subscription[size];
        }
    };

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getExtra() {
        return extra;
    }

    public void setExtra(List<String> extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "category=" + category +
                ", type='" + type + '\'' +
                ", extra=" + extra +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(String.valueOf(category));
        dest.writeString(type);
        dest.writeStringList(extra);
    }
}

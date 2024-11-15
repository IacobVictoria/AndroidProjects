package eu.ase.ro.vacationplanningapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

public class Vacation implements Parcelable {
    Date date; // input
    String location; // spinner
    List<String> activities; // checkbox
    Type type; //radio button

    public Vacation(Date date, String location, List<String> activities, Type type) {
        this.date = date;
        this.location = location;
        this.activities = activities;
        this.type = type;
    }

    protected Vacation(Parcel in) {
        date = DateConverter.toDate(in.readString());
        location = in.readString();
        activities = in.createStringArrayList();
        type = Type.valueOf(in.readString());
    }

    public static final Creator<Vacation> CREATOR = new Creator<Vacation>() {
        @Override
        public Vacation createFromParcel(Parcel in) {
            return new Vacation(in);
        }

        @Override
        public Vacation[] newArray(int size) {
            return new Vacation[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vacation{" +
                "date=" + date +
                ", location='" + location + '\'' +
                ", activities=" + activities +
                ", type=" + type +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(DateConverter.toString(date));
        dest.writeString(location);
        dest.writeStringList(activities);
        dest.writeString(type.toString());
    }
}

package eu.ase.ro.quiztema3.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class QuizReview implements Parcelable {
    private String firstName;
    private String lastName;
    private String feedbackContent;
    private float rating;
    private Date reviewDate;

    public QuizReview(String firstName, String lastName, String feedbackContent, float rating, Date reviewDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.feedbackContent = feedbackContent;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "QuizReview{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", feedbackContent='" + feedbackContent + '\'' +
                ", rating=" + rating +
                ", reviewDate=" + reviewDate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(feedbackContent);
        dest.writeFloat(rating);
        dest.writeString(reviewDate.toString());
    }

    protected QuizReview(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        feedbackContent = in.readString();
        rating = in.readFloat();
        reviewDate = DateConverter.toDate(in.readString());

    }

    public static final Creator<QuizReview> CREATOR = new Creator<QuizReview>() {
        @Override
        public QuizReview createFromParcel(Parcel in) {
            return new QuizReview(in);
        }

        @Override
        public QuizReview[] newArray(int size) {
            return new QuizReview[size];
        }
    };


}

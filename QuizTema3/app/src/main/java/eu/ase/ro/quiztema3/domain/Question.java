package eu.ase.ro.quiztema3.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Question implements Parcelable {
    private String question;
    private String type;
    private List<String> options;
    private String correctAnswer;
    private List<String> correctAnswerList;
    private int minValue; // pentru slider
    private int maxValue; // pentru slider
    private int score;

    public Question(){}

    //   tip radio
    public Question(String question, String type, List<String> options, String correctAnswer, int score) {
        this.question = question;
        this.type = type;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.correctAnswerList = null;
        this.minValue = -1;
        this.maxValue = -1;
        this.score = score;
    }

    //  tip checkbox
    public Question(String question, String type, List<String> options, List<String> correctAnswerList, int score) {
        this.question = question;
        this.type = type;
        this.options = options;
        this.correctAnswer = null;
        this.correctAnswerList = correctAnswerList;
        this.minValue = -1;
        this.maxValue = -1;
        this.score = score;
    }

    //  tip slider
    public Question(String question, String type, int minValue, int maxValue, int correctAnswer,int score) {
        this.question = question;
        this.type = type;
        this.options = null;
        this.correctAnswer = String.valueOf(correctAnswer);
        this.correctAnswerList = null;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.score = score;
    }

    // tip datepicker
    public Question(String question, String type, String correctAnswer,int score) {
        this.question = question;
        this.type = type;
        this.options = null;
        this.correctAnswer = correctAnswer;
        this.correctAnswerList = null;
        this.minValue = -1;
        this.maxValue = -1;
        this.score = score;
    }
    public Question(String question, String type, boolean correctAnswer,int score) {
        this.question = question;
        this.type = type;
        this.options = null;
        this.correctAnswer = String.valueOf(correctAnswer);
        this.correctAnswerList = null;
        this.minValue = -1;
        this.maxValue = -1;
        this.score = score;
    }

    //  tip ratingbar
    public Question(String question, String type, int correctAnswer, int score) {
        this.question = question;
        this.type = type;
        this.options = null;
        this.correctAnswer = String.valueOf(correctAnswer);
        this.correctAnswerList = null;
        this.minValue = -1;
        this.maxValue = -1;
        this.score = score;
    }
    protected Question(Parcel in) {
        question = in.readString();
        type = in.readString();
        options = in.createStringArrayList();
        correctAnswer = in.readString();
        correctAnswerList = in.createStringArrayList();
        minValue = in.readInt();
        maxValue = in.readInt();
        score = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getCorrectAnswerList() {
        return correctAnswerList;
    }

    public void setCorrectAnswerList(List<String> correctAnswerList) {
        this.correctAnswerList = correctAnswerList;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question: ").append(question).append("\n");
        sb.append("Type: ").append(type).append("\n");

        switch (type) {
            case "radio":
                sb.append("Options: ").append(options).append("\n");
                sb.append("Correct Answer: ").append(correctAnswer).append("\n");
                break;

            case "checkbox":
                sb.append("Options: ").append(options).append("\n");
                sb.append("Correct Answers: ").append(correctAnswerList).append("\n");
                break;

            case "slider":
                sb.append("Min Value: ").append(minValue).append("\n");
                sb.append("Max Value: ").append(maxValue).append("\n");
                sb.append("Correct Answer: ").append(correctAnswer).append("\n");
                break;

            case "datepicker":
                sb.append("Correct Answer Date: ").append(correctAnswer).append("\n");
                break;

            default:
                sb.append("Unknown question type.\n");
                break;
        }
        return sb.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(type);
        dest.writeStringList(options);
        dest.writeString(correctAnswer);
        dest.writeStringList(correctAnswerList);
        dest.writeInt(minValue);
        dest.writeInt(maxValue);
        dest.writeInt(score);
    }
}

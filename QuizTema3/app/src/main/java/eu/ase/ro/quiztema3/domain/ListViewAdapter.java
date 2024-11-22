package eu.ase.ro.quiztema3.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import eu.ase.ro.quiztema3.R;

public class ListViewAdapter extends ArrayAdapter<QuizReview> {
    Context context;
    LayoutInflater layoutInflater;
    List<QuizReview> list;
    int resource;


    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<QuizReview> objects, LayoutInflater layoutInflater) {
        super(context, resource, objects);
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.list = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        QuizReview quizReview = list.get(position);
        ImageView iconName = view.findViewById(R.id.iacob_maria_victoria_review_icon_name);
        TextView textName = view.findViewById(R.id.iacob_maria_victoria_review_text_name);
        ImageView iconFeedback = view.findViewById(R.id.iacob_maria_victoria_review_icon_feedback);
        TextView textFeedback = view.findViewById(R.id.iacob_maria_victoria_review_text_feedback);
        ImageView iconDate = view.findViewById(R.id.iacob_maria_victoria_review_icon_date);
        TextView textDate = view.findViewById(R.id.iacob_maria_victoria_review_text_date);
        RatingBar ratingBar = view.findViewById(R.id.iacob_maria_victoria_review_rating_bar);

        textName.setText(String.format("%s %s", quizReview.getFirstName(), quizReview.getLastName()));
        textFeedback.setText(quizReview.getFeedbackContent());
        textDate.setText(DateConverter.toString(
                quizReview.getReviewDate()));
        ratingBar.setRating(quizReview.getRating());

        return view;
    }
}

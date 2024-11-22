package eu.ase.ro.quiztema3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import eu.ase.ro.quiztema3.domain.QuizReview;

public class AddReviewQuizActivity extends AppCompatActivity {

    private TextView reviewText;
    private TextInputEditText lastNameEditText, firstNameEditText;
    private EditText feedbackEditText;
    private RatingBar ratingBar;
    private Button submitButton;
    public static final String REVIEW_KEY = "review_key";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review_quiz);
        intent = getIntent();
        initComponents();
    }

    private void initComponents() {
        reviewText = findViewById(R.id.iacob_maria_victoria_review_text);
        lastNameEditText = findViewById(R.id.iacob_maria_victoria_tiet_last_name);
        firstNameEditText = findViewById(R.id.iacob_maria_victoria_tiet_first_name);
        feedbackEditText = findViewById(R.id.iacob_maria_victoria_feedback);
        ratingBar = findViewById(R.id.iacob_maria_victoria_rating_bar);
        submitButton = findViewById(R.id.iacob_maria_victoria_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    String firstName = firstNameEditText.getText().toString();
                    String lastName = lastNameEditText.getText().toString();
                    String contentFeedback = feedbackEditText.getText().toString();
                    Date dateFeedback = new Date();
                    float rating = ratingBar.getRating();
                    QuizReview quizReview = new QuizReview(firstName, lastName, contentFeedback, rating, dateFeedback);
                    intent.putExtra(REVIEW_KEY, quizReview);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private boolean isValid() {
        if (feedbackEditText.getText() == null || feedbackEditText.getText().toString().length() < 3) {
            Toast.makeText(AddReviewQuizActivity.this, R.string.feedback_not_valid,Toast.LENGTH_LONG).show();
            return false;
        }
        if (lastNameEditText.getText() == null || lastNameEditText.getText().toString().length() < 3) {
            Toast.makeText(AddReviewQuizActivity.this, R.string.last_name_not_valid,Toast.LENGTH_LONG).show();
            return false;
        }
        if (firstNameEditText.getText() == null || firstNameEditText.getText().toString().length() < 3) {
            Toast.makeText(AddReviewQuizActivity.this, R.string.first_name_not_valid,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
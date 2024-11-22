package eu.ase.ro.quiztema3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import eu.ase.ro.quiztema3.fragments.HomeFragment;

public class QuizResultActivity extends AppCompatActivity {

    TextView finalScoreTextView, correctAnswersTextView;
    Button  homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        finalScoreTextView = findViewById(R.id.iacob_maria_victoria_final_score);
        correctAnswersTextView = findViewById(R.id.iacob_maria_victoria_correct_answers);
        homeButton = findViewById(R.id.iacob_maria_victoria_go_home_button);

        int totalScore = getIntent().getIntExtra("totalScore", 0);
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);
        String finalScoreText = getString(R.string.final_score_text, totalScore);
        String finalAnswersText = getString(R.string.final_answers_quiz, correctAnswers, totalQuestions);
        finalScoreTextView.setText(finalScoreText);
        correctAnswersTextView.setText(finalAnswersText);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
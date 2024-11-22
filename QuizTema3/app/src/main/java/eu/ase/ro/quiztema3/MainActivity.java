package eu.ase.ro.quiztema3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.quiztema3.databinding.ActivityMainBinding;
import eu.ase.ro.quiztema3.domain.DateConverter;
import eu.ase.ro.quiztema3.domain.Question;
import eu.ase.ro.quiztema3.domain.QuizReview;
import eu.ase.ro.quiztema3.fragments.AboutQuizFragment;
import eu.ase.ro.quiztema3.fragments.HomeFragment;
import eu.ase.ro.quiztema3.fragments.QuizReviewsFragment;
import eu.ase.ro.quiztema3.network.HttpService;
//https://drive.google.com/file/d/19PBEcOdDzZwTSQIiKxLV1GGF5rTTmzXc/view?usp=drive_link
public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://run.mocky.io/v3/1e0dec76-9a82-455c-b24c-ff88d5ed6266";
    List<Question> questionList;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Fragment currentFragment;
    ExecutorService executorService = Executors.newCachedThreadPool();
    Handler handler = new Handler(Looper.getMainLooper());
    private static List<QuizReview> quizReviewList;
    FloatingActionButton floatingActionButton;
    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItemsList();
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getReviewsCallback());
        floatingActionButton = findViewById(R.id.iacob_maria_victoria_main_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddReviewQuizActivity.class);
                launcher.launch(intent);
            }
        });
        drawerLayout = findViewById(R.id.iacob_maria_victoria_drawer_layout);
        setupNavigationConfiguration();
        navigationView = findViewById(R.id.iacob_maria_victoria_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.iacob_maria_victoria_nav_home) {

                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            HttpService httpService = new HttpService(URL);

                            String result = null;
                            try {
                                result = httpService.call();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            handler.post(getRunOnUiThreadOperation(result));
                        }
                    });

                } else if (item.getItemId() == R.id.iacob_maria_victoria_nav_about) {
                    currentFragment = new AboutQuizFragment();
                    openFragment();
                } else if (item.getItemId() == R.id.iacob_maria_victoria_nav_review) {
                    currentFragment = QuizReviewsFragment.getInstance(quizReviewList);
                    openFragment();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        if (savedInstanceState == null) {
            currentFragment = new AboutQuizFragment();
            openFragment();
            navigationView.setCheckedItem(R.id.iacob_maria_victoria_nav_about);
        }
    }

    private ActivityResultCallback<ActivityResult> getReviewsCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                QuizReview quizReview = result.getData().getParcelableExtra(AddReviewQuizActivity.REVIEW_KEY);
                quizReviewList.add(quizReview);
                if (currentFragment instanceof QuizReviewsFragment) {
                    ((QuizReviewsFragment) currentFragment).notifyAdapter();
                }
            }
        };
    }

    private Runnable getRunOnUiThreadOperation(String result) {
        return () -> {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray questionsArray = jsonObject.getJSONArray("questions");

                questionList = new ArrayList<>();

                for (int i = 0; i < questionsArray.length(); i++) {
                    JSONObject questionObj = questionsArray.getJSONObject(i);

                    String questionText = questionObj.getString("question");
                    String type = questionObj.getString("type");
                    int score = questionObj.getInt("score");

                    Question question;
                    if ("radio".equals(type)) {
                        List<String> options = new ArrayList<>();
                        JSONArray optionsArray = questionObj.getJSONArray("options");
                        for (int j = 0; j < optionsArray.length(); j++) {
                            options.add(optionsArray.getString(j));
                        }

                        String correctAnswer = questionObj.getString("correct_answer");
                        question = new Question(questionText, type, options, correctAnswer, score);
                    } else if ("checkbox".equals(type)) {
                        List<String> options = new ArrayList<>();
                        JSONArray optionsArray = questionObj.getJSONArray("options");
                        for (int j = 0; j < optionsArray.length(); j++) {
                            options.add(optionsArray.getString(j));
                        }

                        List<String> correctAnswers = new ArrayList<>();
                        JSONArray correctAnswerArray = questionObj.getJSONArray("correct_answer");
                        for (int j = 0; j < correctAnswerArray.length(); j++) {
                            correctAnswers.add(correctAnswerArray.getString(j));
                        }
                        question = new Question(questionText, type, options, correctAnswers, score);
                    } else if ("slider".equals(type)) {
                        int minValue = questionObj.getInt("min_value");
                        int maxValue = questionObj.getInt("max_value");
                        int correctAnswer = questionObj.getInt("correct_answer");
                        question = new Question(questionText, type, minValue, maxValue, correctAnswer, score);
                    } else if ("datepicker".equals(type)) {
                        String correctAnswer = questionObj.getString("correct_answer");
                        question = new Question(questionText, type, correctAnswer, score);
                    } else if ("switch".equals(type)) {
                        boolean correctAnswer = questionObj.getBoolean("correct_answer");
                        question = new Question(questionText, type, correctAnswer, score);

                    } else if ("ratingbar".equals(type)) {
                        int correctAnswer = questionObj.getInt("correct_answer");
                        question = new Question(questionText, type, correctAnswer, score);

                    } else if ("spinner".equals(type)) {
                        List<String> options = new ArrayList<>();
                        JSONArray optionsArray = questionObj.getJSONArray("options");
                        for (int j = 0; j < optionsArray.length(); j++) {
                            options.add(optionsArray.getString(j));
                        }
                        String correctAnswer = questionObj.getString("correct_answer");
                        question = new Question(questionText, type, options, correctAnswer, score);

                    } else {
                        continue;
                    }

                    questionList.add(question);
                }
                currentFragment = HomeFragment.getInstance(questionList);
                openFragment();
            } catch (JSONException e) {
                Log.e( "Nu se poate parsa JSON-ului",e.getMessage());
            }
        };
    }

    private void addItemsList() {
        quizReviewList = new ArrayList<>();
        String[] firstNames = getResources().getStringArray(R.array.first_names);
        String[] lastNames = getResources().getStringArray(R.array.last_names);
        String[] feedbackContent = getResources().getStringArray(R.array.feedback_content);
        String[] ratings = getResources().getStringArray(R.array.ratings);
        String[] reviewDates = getResources().getStringArray(R.array.review_dates);

        for (int i = 0; i < 3; i++) {
            QuizReview quizReview = new QuizReview(firstNames[i],
                    lastNames[i], feedbackContent[i], Float.parseFloat(ratings[i]), DateConverter.toDate(reviewDates[i]));
            quizReviewList.add(quizReview);
        }
    }

    private void openFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.iacob_maria_victoria_frame_layout, currentFragment).commit();
    }

    private void setupNavigationConfiguration() {
        Toolbar toolbar = findViewById(R.id.iacob_maria_victoria_toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
    }

}
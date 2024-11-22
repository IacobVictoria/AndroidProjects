package eu.ase.ro.quiztema3.domain;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.ase.ro.quiztema3.QuizResultActivity;
import eu.ase.ro.quiztema3.R;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.QuestionViewHolder> {
    private static List<Question> questions;
    private final FragmentManager fragmentManager;
    private Map<Integer, Object> userAnswers = new HashMap<>();

    public ViewPagerAdapter(List<Question> questions,FragmentManager fragmentManager) {
        this.questions = questions;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 0) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.start_quiz, parent, false);
        } else if (viewType == 2) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.end_quiz, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_card, parent, false);
        }
        return new QuestionViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        if (position == questions.size() + 1) {
            holder.showEndCard();
        } else if (position == 0) {
            holder.showStartCard();
        } else {
            Question question = questions.get(position-1);
            holder.createQuestionCard(question);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == questions.size() + 1) {
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return questions.size() + 2;
    }

    public  class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionText;
        RadioGroup radioGroup;
        SeekBar seekBar;
        LinearLayout checkBoxLayout;
        DatePicker datePicker;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchControl;
        RatingBar ratingBar;
        Spinner spinner;
        Button endButton;
        TextView endQuizText;
        TextView startQuizText;
        TextView rulesText;
        ImageView imageStartQuiz;
        TextView swipeText;

        public QuestionViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if (viewType == 1) {
                questionText = itemView.findViewById(R.id.iacob_maria_victoria_tv_question);
                radioGroup = itemView.findViewById(R.id.iacob_maria_victoria_radio_group);
                seekBar = itemView.findViewById(R.id.iacob_maria_victoria_seek_bar);
                checkBoxLayout = itemView.findViewById(R.id.iacob_maria_victoria_checkbox_layout);
                datePicker = itemView.findViewById(R.id.iacob_maria_victoria_date_picker);
                switchControl = itemView.findViewById(R.id.iacob_maria_victoria_switch);
                ratingBar = itemView.findViewById(R.id.iacob_maria_victoria_rating_bar);
                spinner = itemView.findViewById(R.id.iacob_maria_victoria_spinner);
            } else  if(viewType == 2){
                endQuizText = itemView.findViewById(R.id.iacob_maria_victoria_tv_end_quiz);
                endButton = itemView.findViewById(R.id.iacob_maria_victoria_button_end_quiz);
            }
            else{
                startQuizText = itemView.findViewById(R.id.iacob_maria_victoria_tv_start_quiz);
                rulesText = itemView.findViewById(R.id.iacob_maria_victoria_tv_rules_quiz);
                imageStartQuiz = itemView.findViewById(R.id.iacob_maria_victoria_image_start_quiz);
                swipeText = itemView.findViewById(R.id.iacob_maria_victoria_swipe_start);
            }
        }

        public void createQuestionCard(Question question) {
            questionText.setText(question.getQuestion());
            radioGroup.setVisibility(View.GONE);
            seekBar.setVisibility(View.GONE);
            checkBoxLayout.setVisibility(View.GONE);
            datePicker.setVisibility(View.GONE);
            switchControl.setVisibility(View.GONE);
            ratingBar.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            checkBoxLayout.removeAllViews();

            switch (question.getType()) {
                case "radio":
                    radioGroup.setVisibility(View.VISIBLE);
                    for (String option : question.getOptions()) {
                        RadioButton radioButton = new RadioButton(itemView.getContext());
                        radioButton.setText(option);
                        radioGroup.addView(radioButton);
                    }
                    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                        if (checkedId != -1) {
                            RadioButton selectedRadioButton = itemView.findViewById(checkedId);
                            String selectedAnswer = selectedRadioButton.getText().toString();
                            userAnswers.put(getAdapterPosition() - 1, selectedAnswer);
                        } else {
                            userAnswers.put(getAdapterPosition() - 1, "No answer");
                        }
                    });
                    break;

                case "checkbox":
                    checkBoxLayout.setVisibility(View.VISIBLE);
                    List<String> selectedAnswers = new ArrayList<>();
                    for (String option : question.getOptions()) {
                        CheckBox newCheckBox = new CheckBox(itemView.getContext());
                        newCheckBox.setText(option);
                        newCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                            if (isChecked) {
                                selectedAnswers.add(newCheckBox.getText().toString());
                            } else {
                                selectedAnswers.remove(newCheckBox.getText().toString());
                            }
                            if (selectedAnswers.isEmpty()) {
                                userAnswers.put(getAdapterPosition() - 1, "No answer");
                            } else {
                                userAnswers.put(getAdapterPosition() - 1, selectedAnswers);
                            }
                        });
                        checkBoxLayout.addView(newCheckBox);
                    }

                    break;

                case "slider":
                    seekBar.setVisibility(View.VISIBLE);
                    seekBar.setMax(question.getMaxValue());
                    seekBar.setMin(question.getMinValue());
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            userAnswers.put(getAdapterPosition() - 1, progress == 0 ? null : progress);
                            String progressText = String.valueOf(progress);
                            Toast.makeText(itemView.getContext(), "Slider value: " + progressText, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {}

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {}
                    });
                    break;

                case "datepicker":
                    datePicker.setVisibility(View.VISIBLE);
                    datePicker.init(
                            2024, 0, 1, (view, year, monthOfYear, dayOfMonth) -> {
                                String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                userAnswers.put(getAdapterPosition() - 1, selectedDate);
                            });
                    break;

                case "switch":
                    switchControl.setVisibility(View.VISIBLE);
                    switchControl.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        userAnswers.put(getAdapterPosition() - 1, isChecked);
                    });
                    break;

                case "ratingbar":
                    ratingBar.setVisibility(View.VISIBLE);
                    ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                        userAnswers.put(getAdapterPosition() - 1, rating);
                    });
                    break;

                case "spinner":
                    spinner.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_item, question.getOptions());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            String selectedAnswer = (String) parentView.getItemAtPosition(position);
                            userAnswers.put(getAdapterPosition() - 1, selectedAnswer);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            userAnswers.put(getAdapterPosition() - 1, "No answer");
                        }
                    });
                    break;
                default:
                    break;
            }
        }

        public void showEndCard() {
            endQuizText.setVisibility(View.VISIBLE);
            endButton.setVisibility(View.VISIBLE);
            endButton.setOnClickListener(v -> {
                int totalScore = 0;
                int correctAnswers = 0;

                for (int i = 0; i < questions.size(); i++) {
                    Question question = questions.get(i);
                    Object userAnswer = userAnswers.get(i);
                    if (userAnswer == null || userAnswer.equals("No answer")) {
                        continue;
                    }

                    if (question.getType().equals("checkbox")) {
                        List<String> correctAnswersList = question.getCorrectAnswerList();
                        if (userAnswer instanceof List) {
                            List<String> userAnswerList = (List<String>) userAnswer;
                            if (userAnswerList.containsAll(correctAnswersList) && correctAnswersList.containsAll(userAnswerList)) {
                                totalScore += question.getScore();
                                correctAnswers++;
                            }
                        }
                    } else if (question.getType().equals("slider")) {

                        Integer correctAnswer = Integer.parseInt(question.getCorrectAnswer());
                        if (userAnswer.equals(correctAnswer)) {
                            totalScore += question.getScore();
                            correctAnswers++;
                        }
                    }   else if (question.getType().equals("switch")) {
                        boolean correctAnswer = Boolean.parseBoolean(question.getCorrectAnswer());
                        if (userAnswer instanceof Boolean && (boolean) userAnswer == correctAnswer) {
                            totalScore += question.getScore();
                            correctAnswers++;
                        }
                    }
                    else if (question.getType().equals("ratingbar")) {
                        float correctAnswer = Float.parseFloat(question.getCorrectAnswer());
                        if (userAnswer instanceof Float && (float) userAnswer == correctAnswer) {
                            totalScore += question.getScore();
                            correctAnswers++;
                        }
                    } else { //radio, checkbox
                        if (userAnswer.equals(question.getCorrectAnswer())) {
                            totalScore += question.getScore();
                            correctAnswers++;
                        }
                    }
                }

                Intent intent = new Intent(itemView.getContext(), QuizResultActivity.class);
                intent.putExtra("totalScore", totalScore);
                intent.putExtra("correctAnswers", correctAnswers);
                intent.putExtra("totalQuestions", questions.size());

                itemView.getContext().startActivity(intent);
            });
        }
        public void showStartCard() {
            imageStartQuiz.setVisibility(View.VISIBLE);
            startQuizText.setVisibility(View.VISIBLE);
            rulesText.setVisibility(View.VISIBLE);
            swipeText.setVisibility(View.VISIBLE);
        }
    }
}

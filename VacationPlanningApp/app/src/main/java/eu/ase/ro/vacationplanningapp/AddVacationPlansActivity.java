package eu.ase.ro.vacationplanningapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import eu.ase.ro.vacationplanningapp.domain.DateConverter;
import eu.ase.ro.vacationplanningapp.domain.Type;
import eu.ase.ro.vacationplanningapp.domain.Vacation;

public class AddVacationPlansActivity extends AppCompatActivity {

    TextInputEditText tietDate;
    TextInputEditText tietBudget;
    Spinner spinner;
    RadioGroup radioGroup;
    CheckBox ck1;
    CheckBox ck2;
    CheckBox ck3;
    Button btn;
    Intent intent;
    public static final String KEY_VACATION="key-vac";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vacation_plans);
        intent = getIntent();
        initComponents();
    }

    private void initComponents() {
        tietDate = findViewById(R.id.tiet_date);
        tietBudget = findViewById(R.id.tiet_budget);
        spinner = findViewById(R.id.spinner_location);
        radioGroup = findViewById(R.id.rg1);
        ck1 = findViewById(R.id.act1);
        ck2 = findViewById(R.id.act2);
        ck3 = findViewById(R.id.act3);
        btn = findViewById(R.id.btn_save);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.spiner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btn.setOnClickListener((event) -> {
            if (isValid()) {
                Date date = DateConverter.toDate(tietDate.getText().toString());
                String location=spinner.getSelectedItem().toString();
                Type type;
                if(radioGroup.getCheckedRadioButtonId()== R.id.rb1){
                     type = Type.MUNTE;
                }
                else{
                    type = Type.PLAJA;
                }
                List<String> activitati = new ArrayList<>();
                if(ck1.isChecked()){
                    activitati.add(ck1.getText().toString());
                }
                if(ck2.isChecked()){
                    activitati.add(ck2.getText().toString());
                }
                if(ck3.isChecked()){
                    activitati.add(ck3.getText().toString());
                }
                Vacation vacation = new Vacation(date,location,activitati,type);
                intent.putExtra(KEY_VACATION,vacation);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    private boolean isValid() {
        if (tietDate.getText() == null || tietDate.getText().toString().isEmpty() || DateConverter.toDate(tietDate.getText().toString()) == null) {
            Toast.makeText(this,"hie",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tietBudget.getText() == null) {
            return false;
        }
        try {
            double budget = Double.parseDouble(tietBudget.getText().toString());
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
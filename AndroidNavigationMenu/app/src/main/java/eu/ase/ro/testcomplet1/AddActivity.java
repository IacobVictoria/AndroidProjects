package eu.ase.ro.testcomplet1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    Button saveBtn;
    Spinner spinner;
    RadioGroup radioGroup;
    DatePicker datePicker;
    CheckBox ck_contract;
    CheckBox ck_acces;
    CheckBox ck_suport;
    Intent intent;
    public static final String LIST_KEY="LIST_KEY";
    public static final String DATE_KEY="DATE_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initComponents();
        intent = getIntent();

    }

    private void initComponents() {
        spinner = findViewById(R.id.add_spinner);
        radioGroup = findViewById(R.id.add_rg);
        if (valid()) {
            ck_contract = findViewById(R.id.ck_contract);
            ck_suport = findViewById(R.id.ck_suport);
            ck_acces = findViewById(R.id.ck_access);
            datePicker = findViewById(R.id.date_add);
            saveBtn = findViewById(R.id.btn_save);
           radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(RadioGroup group, int checkedId) {
                   if (radioGroup.getCheckedRadioButtonId() == R.id.rb_sapt) {
                       updateAdapter(R.array.categorie_lunar);
                   }
                   if (radioGroup.getCheckedRadioButtonId() == R.id.rb_lunar) {
                       updateAdapter(R.array.categorie_saptamanal);
                   }
               }
           });
            saveBtn.setOnClickListener((event) -> {
                String type = spinner.getSelectedItem().toString();
                Category category = Category.LUNAR;
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_anual) {
                    category = Category.ANUAL;
                }
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_sapt) {
                    category = Category.SAPTAMANAL;
                }
                List<String> accessories = new ArrayList<>();
                if (ck_acces.isChecked()) {
                    accessories.add(ck_acces.getText().toString());
                }
                if (ck_suport.isChecked()) {
                    accessories.add(ck_suport.getText().toString());
                }
                if (ck_contract.isChecked()) {
                    accessories.add(ck_contract.getText().toString());
                }
                String data = datePicker.getYear()+" "+datePicker.getMonth();
                Subscription sub = new Subscription(category, type, accessories);
                intent.putExtra(LIST_KEY, sub);
                intent.putExtra(DATE_KEY,data);
                setResult(RESULT_OK, intent);
                finish();
            });
        }
    }

    private void updateAdapter(int categorieLunar) {
       ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(),categorieLunar,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private boolean valid() {
        return true;
    }
}
package eu.ase.ro.tema_2_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.tema_2_android.domain.Category;
import eu.ase.ro.tema_2_android.domain.SurvivalGuide;
import eu.ase.ro.tema_2_android.services.SurvivalGuideService;

public class AddSurvivalGuideActivity extends AppCompatActivity {

    public static final String GUIDE_KEY = "guide_key";
    private Spinner categorieSpinner;
    private TextInputEditText titluText;
    private TextInputEditText descriereText;
    private RangeSlider nivelUrgentaSlider;
    private LinearLayout checkboxLayout;
    private Button btnSave;
    private Button btnCancel;
    private CheckBox checkboxLanterna;
    private CheckBox checkboxTrusa;
    private CheckBox checkboxApa;
    private CheckBox checkboxProvizii;
    private CheckBox checkboxRadio;
    private CheckBox checkboxAltele;
    private Intent intent;

    private SurvivalGuideService survivalGuideService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_survival_guide);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        survivalGuideService = new SurvivalGuideService();

        initializareComponente();
        intent = getIntent();

    }

    private void initializareComponente() {
        categorieSpinner = findViewById(R.id.guia_diana_categorie);
        titluText = findViewById(R.id.guia_diana_titlu);
        descriereText = findViewById(R.id.guia_diana_descriere);
        nivelUrgentaSlider = findViewById(R.id.guia_diana_slider);
        btnSave = findViewById(R.id.guia_diana_btnSave);
        checkboxLayout = findViewById(R.id.guia_diana_precautii);
        btnCancel = findViewById(R.id.guia_diana_btnCancel);

        checkboxLanterna = findViewById(R.id.guia_diana_lanterna);
        checkboxTrusa = findViewById(R.id.guia_diana_trusa);
        checkboxApa = findViewById(R.id.guia_diana_apa);
        checkboxProvizii = findViewById(R.id.guia_diana_provizii);
        checkboxRadio = findViewById(R.id.guia_diana_radio);
        checkboxAltele = findViewById(R.id.guia_diana_altele);

        nivelUrgentaSlider.setLabelFormatter(value -> String.valueOf((int) value));

        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorieSpinner.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            if (validare()) {
                SurvivalGuide dateFormular = buildDateFormularFromView();
                Log.i("Formular", "Formular" + dateFormular);

                intent.putExtra(GUIDE_KEY, dateFormular);
                setResult(RESULT_OK, intent);
                finish();
            }

        });

        btnCancel.setOnClickListener(v->{
            finish();
        });
    }

    private SurvivalGuide buildDateFormularFromView() {
        Category categorie = (Category) categorieSpinner.getSelectedItem();
        String titlu = titluText.getText().toString();
        String descriere = descriereText.getText().toString();
        int nivelUrgenta = Math.round(nivelUrgentaSlider.getValues().get(0));

        List<String> masuriPrecautie = new ArrayList<>();
        if (checkboxLanterna.isChecked()) {
            masuriPrecautie.add("Lanterna");
        }
        if (checkboxTrusa.isChecked()) {
            masuriPrecautie.add("Trusa de prim ajutor");
        }
        if (checkboxApa.isChecked()) {
            masuriPrecautie.add("Apa");
        }
        if (checkboxProvizii.isChecked()) {
            masuriPrecautie.add("Provizii (mâncare)");
        }
        if (checkboxRadio.isChecked()) {
            masuriPrecautie.add("Radio");
        }
        if (checkboxAltele.isChecked()) {
            masuriPrecautie.add("Altele");
        }

        return new SurvivalGuide(categorie, masuriPrecautie, nivelUrgenta, descriere, titlu, survivalGuideService.getImageForTitle(titlu));
    }

    private boolean validare() {
        if (titluText.getText() == null || titluText.getText().toString().trim().length() < 3) {
            Toast.makeText(getApplicationContext(), "Titlul este prea scurt", Toast.LENGTH_LONG).show();
            return false;
        }

        if (descriereText.getText() == null || descriereText.getText().toString().trim().length() < 3) {
            Toast.makeText(getApplicationContext(), "Descrierea este prea scurtă", Toast.LENGTH_LONG).show();
            return false;
        }

        if (categorieSpinner.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "Selectați o categorie", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!checkboxLanterna.isChecked() && !checkboxTrusa.isChecked() &&
                !checkboxApa.isChecked() && !checkboxProvizii.isChecked() &&
                !checkboxRadio.isChecked() && !checkboxAltele.isChecked()) {
            Toast.makeText(getApplicationContext(), "Selectați cel puțin o măsură de precauție", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


}
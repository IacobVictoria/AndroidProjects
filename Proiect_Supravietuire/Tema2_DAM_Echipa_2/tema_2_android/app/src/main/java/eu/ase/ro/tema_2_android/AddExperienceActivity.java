package eu.ase.ro.tema_2_android;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import eu.ase.ro.tema_2_android.domain.EmotionalDamage;
import eu.ase.ro.tema_2_android.domain.ExperiencePost;
import eu.ase.ro.tema_2_android.domain.LossLevel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddExperienceActivity extends AppCompatActivity {

    public static final String EXPERIENCE_KEY = "experience_key";
    private TextInputEditText tietName;
    private Spinner spnDisaster;
    private TextInputEditText tietDescription;
    private CheckBox cbHome;
    private CheckBox cbCar;
    private CheckBox cbAnother;
    private RadioGroup rgLoss;
    private RadioGroup rgEffect;
    private Spinner spnPrepared;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_experience);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponents();
        intent = getIntent();
    }
    private void initComponents() {
        tietName = findViewById(R.id.hrehorciuc_mara_add_experience_tiet_name);
        spnDisaster = findViewById(R.id.hrehorciuc_mara_add_experience_spn_disaster_type);
        tietDescription = findViewById(R.id.hrehorciuc_mara_add_experience_tiet_description);
        cbHome = findViewById(R.id.hrehorciuc_mara_add_experience_cb_home);
        cbCar = findViewById(R.id.hrehorciuc_mara_add_experience_cb_car);
        cbAnother = findViewById(R.id.hrehorciuc_mara_add_experience_cb_another);
        rgLoss = findViewById(R.id.hrehorciuc_mara_add_experience_rg_loss_level);
        rgEffect = findViewById(R.id.hrehorciuc_mara_add_experience_rg_effect);
        spnPrepared = findViewById(R.id.hrehorciuc_mara_add_experience_spn_prepared);
        btnSave = findViewById(R.id.hrehorciuc_mara_add_experience_btn_save);
        btnSave.setOnClickListener(v -> {
            if(isValid()) {
                ExperiencePost experience = buildExperienceFromView();

                intent.putExtra(EXPERIENCE_KEY, experience);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private ExperiencePost buildExperienceFromView() {
        String name=tietName.getText().toString();
        String disasterType=(String)spnDisaster.getSelectedItem();
        String description=tietDescription.getText().toString();
        LossLevel lossLevel = rgLoss.getCheckedRadioButtonId() == R.id.hrehorciuc_mara_add_experience_rb_minor_loss ? LossLevel.MICI
                : rgLoss.getCheckedRadioButtonId() == R.id.hrehorciuc_mara_add_experience_rb_mediocre_loss ? LossLevel.MEDII
                : LossLevel.MARI;
        EmotionalDamage emotionalDamage = rgEffect.getCheckedRadioButtonId() == R.id.hrehorciuc_mara_add_experience_rb_gentle ? EmotionalDamage.USOR
                : rgEffect.getCheckedRadioButtonId() == R.id.hrehorciuc_mara_add_experience_rb_mediocre ? EmotionalDamage.MODERAT
                :EmotionalDamage.SEVER;
        List<String> damages = new ArrayList<>();
        if(cbHome.isChecked()) {
            damages.add("casa");
        }
        if(cbCar.isChecked()) {
            damages.add("masina");
        }
        if(cbAnother.isChecked()) {
            damages.add("altele");
        }
        int levelOfPreparedness = Integer.parseInt(spnPrepared.getSelectedItem().toString());
        int image;
        if(disasterType.equals("Cutremur")) {
            image = R.drawable.earthquake;
        }
        else if(disasterType.equals("Incendiu")) {
            image = R.drawable.fire;
        }
        else {
            image = R.drawable.storm;
        }
        return new ExperiencePost(name, disasterType, description, damages, lossLevel, emotionalDamage, levelOfPreparedness, image);
    }

    private boolean isValid() {
        if (tietName.getText() == null || tietName.getText().toString().trim().length() < 3) {
            Toast.makeText(getApplicationContext(), R.string.hrehorciuc_mara_add_experience_invalid_name, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tietDescription.getText()==null || tietDescription.getText().toString().trim().length()<10) {
            Toast.makeText(getApplicationContext(), R.string.hrehorciuc_mara_add_experience_invalid_description, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
package eu.ase.ro.tema_2_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnTips;
    private Button btnExperiences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponents();
    }

    private void initComponents(){
        Button btnTips = (Button)findViewById(R.id.bululete_bianca_main_btn_tips);
        Button btnExperiences = (Button)findViewById(R.id.bululete_bianca_main_btn_experiences);
        btnTips.setOnClickListener(getSurvivalGuideActivityEvent());
        btnExperiences.setOnClickListener(getExperienceActivityEvent());
    }

    private View.OnClickListener getSurvivalGuideActivityEvent(){
        return view -> {
            Intent intent = new Intent(getApplicationContext(),
                    SurvivalGuideActivity.class);
            startActivity(intent);
        };
    }

    private View.OnClickListener getExperienceActivityEvent(){
        return view -> {
            Intent intent = new Intent(getApplicationContext(),
                    ExperienceActivity.class);
            startActivity(intent);
        };
    }
}
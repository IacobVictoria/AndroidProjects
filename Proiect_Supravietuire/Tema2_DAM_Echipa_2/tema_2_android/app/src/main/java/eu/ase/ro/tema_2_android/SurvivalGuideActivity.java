package eu.ase.ro.tema_2_android;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.ase.ro.tema_2_android.domain.Category;
import eu.ase.ro.tema_2_android.domain.ExpandableListPagerAdapter;
import eu.ase.ro.tema_2_android.domain.SurvivalGuide;
import eu.ase.ro.tema_2_android.services.SurvivalGuideService;

public class SurvivalGuideActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private List<SurvivalGuide> survivalGuides;
    private FloatingActionButton floatingBtn;
    private Spinner contactSpinner;
    private ImageButton backImageButton;
    private TextView textViewEmergency;

    private ActivityResultLauncher<Intent> launcher;
    ExpandableListPagerAdapter adapter;
    private SurvivalGuideService survivalGuideService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survival_guide);

        survivalGuideService = new SurvivalGuideService();
        survivalGuides = new ArrayList<>();

        initComponents();

        launcher = registerForActivityResult(new ActivityResultContracts
                .StartActivityForResult(), getAddSurvivalGuideCallback());

        initializeSurvivalGuides();

        floatingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddSurvivalGuideActivity.class);
            launcher.launch(intent);
        });

        backImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        adapter = new ExpandableListPagerAdapter(getApplicationContext(), survivalGuides);
        viewPager.setAdapter(adapter);
    }

    private void initComponents() {
        contactSpinner = findViewById(R.id.iacob_maria_victoria_spn_emergencyContact);
        viewPager = findViewById(R.id.iacob_maria_victoria_viewPager);
        floatingBtn = findViewById(R.id.iacob_maria_victoria_btn_addGuide);
        backImageButton = findViewById(R.id.iacob_maria_victoria_backBtn);
        textViewEmergency = findViewById(R.id.iacob_maria_victoria_tv_emergencyContact);
    }

    private ActivityResultCallback<ActivityResult> getAddSurvivalGuideCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                SurvivalGuide survivalGuide = (SurvivalGuide) result.getData()
                        .getSerializableExtra(AddSurvivalGuideActivity.GUIDE_KEY);
                Toast.makeText(getApplicationContext(), "added: " + survivalGuide.toString(),
                        Toast.LENGTH_LONG).show();
                survivalGuides.add(survivalGuide);
                adapter.notifyDataSetChanged();
            }
        };
    }

    private void initializeSurvivalGuides() {

        Resources res = getResources();
        String[] titles = res.getStringArray(R.array.viewPager_titles);
        String[] descriptions = res.getStringArray(R.array.viewPager_descriptions);
        String[] urgencyLevels = res.getStringArray(R.array.viewPager_urgency_levels);

        List<List<String>> precautions = new ArrayList<>();
        precautions.add(Arrays.asList(res.getStringArray(R.array.viewPager_precautions_first_aid)));
        precautions.add(Arrays.asList(res.getStringArray(R.array.viewPager_precautions_disaster)));
        precautions.add(Arrays.asList(res.getStringArray(R.array.viewPager_precautions_fire)));
        precautions.add(Arrays.asList(res.getStringArray(R.array.viewPager_precautions_flood)));
        precautions.add(Arrays.asList(res.getStringArray(R.array.viewPager_precautions_choking)));

        survivalGuides = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            int urgency = Integer.parseInt(urgencyLevels[i]);
            Category category = survivalGuideService.getCategory(titles[i]);
            int imageResource = survivalGuideService.getImageForTitle(titles[i]);
            survivalGuides.add(new SurvivalGuide(category, precautions.get(i), urgency, descriptions[i], titles[i], imageResource));
        }
    }

}
package eu.ase.ro.tema_2_android;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.tema_2_android.domain.EmotionalDamage;
import eu.ase.ro.tema_2_android.domain.ExperiencePost;
import eu.ase.ro.tema_2_android.domain.LossLevel;
import eu.ase.ro.tema_2_android.domain.RecyclerViewAdapter;

public class ExperienceActivity extends AppCompatActivity {
    private RecyclerView rvPosts;
    private SearchView svFilter;
    private Button btnAdd;
    private List<ExperiencePost> experiencePosts = new ArrayList<>();
    private int[] disasterImages = {R.drawable.earthquake, R.drawable.storm, R.drawable.fire};
    private ImageButton btnBack;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_experience);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.balint_franceska_experience_post_ibtn_back);
        btnBack.setOnClickListener(c -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
        svFilter = findViewById(R.id.balint_franceska_experience_post_sv_filter);
        ImageView searchIcon = (ImageView) svFilter.findViewById(androidx.appcompat.R.id.search_mag_icon);
        searchIcon.setColorFilter(ContextCompat.getColor(this, R.color.titleColor), PorterDuff.Mode.SRC_IN);

        btnAdd = findViewById(R.id.balint_franceska_experience_post_btn_add);
        btnAdd.setOnClickListener(c -> {
            Intent intent = new Intent(getApplicationContext(), AddExperienceActivity.class);
            launcher.launch(intent);
        });

        rvPosts = findViewById(R.id.balint_franceska_experience_post_rv_posts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        experiencePosts.add(new ExperiencePost(getString(R.string.balint_franceska_name_1), getString(R.string.balint_franceska_storm), getString(R.string.balint_franceska_storm_description), List.of(getString(R.string.balint_franceska_home_damage), getString(R.string.balint_franceska_car_damage)), LossLevel.MARI, EmotionalDamage.MODERAT, 1, disasterImages[1]));
        experiencePosts.add(new ExperiencePost(getString(R.string.balint_franceska_name_2), getString(R.string.balint_franceska_earthquake), getString(R.string.balint_franceska_earthquake_description), List.of(getString(R.string.balint_franceska_home_damage), getString(R.string.balint_franceska_another_damage)), LossLevel.MEDII, EmotionalDamage.USOR, 2, disasterImages[0]));
        experiencePosts.add(new ExperiencePost(getString(R.string.balint_franceska_name_3), getString(R.string.balint_franceska_fire), getString(R.string.balint_franceska_fire_description), List.of(getString(R.string.balint_franceska_car_damage)), LossLevel.MICI, EmotionalDamage.MODERAT,5, disasterImages[2]));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(experiencePosts);
        rvPosts.setAdapter(adapter);

        svFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), getAddStudentCallback());
    }

    private ActivityResultCallback<ActivityResult> getAddStudentCallback() {
        return result -> {
            if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                ExperiencePost experience = (ExperiencePost) result.getData().getSerializableExtra(AddExperienceActivity.EXPERIENCE_KEY);
                RecyclerViewAdapter adapter = (RecyclerViewAdapter) rvPosts.getAdapter();
                if (adapter != null) {
                    adapter.addExperiencePost(experience);
                }
            }
        };
    }
}
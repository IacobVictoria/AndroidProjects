package eu.ase.ro.vacationplanningapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuInflater;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.vacationplanningapp.databinding.ActivityMainBinding;
import eu.ase.ro.vacationplanningapp.domain.Vacation;
import eu.ase.ro.vacationplanningapp.fragments.HomeFragment;
import eu.ase.ro.vacationplanningapp.fragments.VactionPlannerFragment;
import eu.ase.ro.vacationplanningapp.network.HttpManager;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Fragment fragment;
    FloatingActionButton floatingActionButton;
    ExecutorService executorService = Executors.newCachedThreadPool();
    Handler handler = new Handler(Looper.getMainLooper());
    List<Vacation> vacations = new ArrayList<>();
    ActivityResultLauncher<Intent> launcher;
    private static final String URL = "https://run.mocky.io/v3/bc12ed7d-21df-465e-8ced-351a69ec136b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupConfig();
        navigationView = findViewById(R.id.nav_view);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddVacation());
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddVacationPlansActivity.class);
                launcher.launch(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    fragment = new HomeFragment();
                    Toast.makeText(MainActivity.this, "hei", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.nav_vac_list) {
                    fragment = VactionPlannerFragment.getInstance(vacations);
                }
                openFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        if (savedInstanceState == null) {
            fragment = new HomeFragment();
            openFragment();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private ActivityResultCallback<ActivityResult> getAddVacation() {
        return result -> {
            if (result.getData() != null && result.getResultCode() == RESULT_OK) {
                Vacation vacation = result.getData().getParcelableExtra(AddVacationPlansActivity.KEY_VACATION);
                vacations.add(vacation);
if(fragment instanceof VactionPlannerFragment){
    ((VactionPlannerFragment) fragment).notifyAdapter();
}
            }
        };
    }

    private void openFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, fragment).commit();
    }

    private void setupConfig() {
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_import) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    HttpManager httpManager = new HttpManager(URL);
                    try {
                        vacations = httpManager.call();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    handler.post(getInUiComponent(vacations));
                }
            });
        }
        return true;
    }

    private Runnable getInUiComponent(List<Vacation> vacations) {
        return () -> {
            fragment = VactionPlannerFragment.getInstance(vacations);
            openFragment();
        };
    }
}
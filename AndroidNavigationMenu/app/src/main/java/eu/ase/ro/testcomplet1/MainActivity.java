package eu.ase.ro.testcomplet1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.DatePicker;
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
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.testcomplet1.databinding.ActivityMainBinding;
import eu.ase.ro.testcomplet1.fragments.AboutFragment;
import eu.ase.ro.testcomplet1.fragments.HomeFragment;
import eu.ase.ro.testcomplet1.fragments.ImportFragment;
import eu.ase.ro.testcomplet1.network.HttpManager;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment currentFragment;
    private ActivityResultLauncher<Intent> launcher;
    FloatingActionButton fabAdd;
    List<Subscription> subscriptionList=new ArrayList<>();
    List<String> activities = new ArrayList<>();
    String data;
    private static String url="https://run.mocky.io/v3/313193af-c2bf-426b-a9c4-51dbc51cd63e";
    ExecutorService executorService =  Executors.newCachedThreadPool();
    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configSetup();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getSelectedItemNav());
        fabAdd = findViewById(R.id.fab);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddCallback());
        fabAdd.setOnClickListener((event) -> {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            launcher.launch(intent);
        });
        if (savedInstanceState == null) {
            currentFragment = new HomeFragment();
            openFragment();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }
    private ActivityResultCallback<ActivityResult> getAddCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Subscription sub = result.getData().getParcelableExtra(AddActivity.LIST_KEY);
                 data = result.getData().getStringExtra(AddActivity.DATE_KEY);
                subscriptionList.add(sub);
                if(currentFragment instanceof AboutFragment){
                    ((AboutFragment) currentFragment).notifyAdapter();
                }
            }
        };
    }

    private NavigationView.OnNavigationItemSelectedListener getSelectedItemNav() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    currentFragment = new HomeFragment();
                    Toast.makeText(getApplicationContext(), "hei", Toast.LENGTH_LONG).show();
                }

                if (item.getItemId() == R.id.nav_about) {
                    currentFragment = AboutFragment.getInstance(subscriptionList);
                    Toast.makeText(getApplicationContext(), "hei", Toast.LENGTH_LONG).show();
                }

                openFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private void openFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, currentFragment).commit();
    }

    private void configSetup() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_show){
            currentFragment = HomeFragment.getInstance(data);
            openFragment();
        }
        if(item.getItemId() == R.id.action_import){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    HttpManager httpManager = new HttpManager(url);
                    List<String> result = httpManager.call();
                    handler.post(getReturnresylt(result));

                }
            });
            currentFragment = ImportFragment.getInstance(activities);
            openFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private Runnable getReturnresylt(List<String> result) {
        return ()-> {
            activities = result;
        };

    }
}
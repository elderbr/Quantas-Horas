package br.com.elderbr.android.quantashoras;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Context myContext;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        myContext = this;


        if (savedInstanceState == null) {
            toolbar.setTitle("    Quantas Horas");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, new QuantaHoraFragment()).commit();
        }

    }


    private void instanciando() {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_quantas_horas:
                toolbar.setTitle("    Quantas Horas");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, new QuantaHoraFragment()).commit();
                break;
            case R.id.nav_chegada:
                toolbar.setTitle("    Chegada");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, new ChegadaFragment()).commit();
                break;
            case R.id.nav_setting:
                toolbar.setTitle("    Configurações");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, new ConfiguracaoFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
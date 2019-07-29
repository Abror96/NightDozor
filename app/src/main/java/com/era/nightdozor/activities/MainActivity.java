package com.era.nightdozor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.era.nightdozor.R;
import com.era.nightdozor.activities.mainViewPager.adapter.MainViewPagerAdapter;
import com.era.nightdozor.databinding.ActivityMainBinding;
import com.era.nightdozor.mvp.contracts.AuthContract;
import com.era.nightdozor.mvp.interactors.AuthInteractorImpl;
import com.era.nightdozor.mvp.presenters.AuthPresenterImpl;
import com.era.nightdozor.utils.PrefConfig;

import static com.era.nightdozor.utils.Constants.initProgressDialog;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_more_vert_black_24dp));

        prefConfig = new PrefConfig(this);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(2);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        setupTabIcons();

    }

    private void setupTabIcons() {
        binding.tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        binding.tabLayout.getTabAt(1).setIcon(R.drawable.ic_pc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                startActivity(new Intent(MainActivity.this, AuthActivity.class));
                finish();
                prefConfig.setLoginStatus(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

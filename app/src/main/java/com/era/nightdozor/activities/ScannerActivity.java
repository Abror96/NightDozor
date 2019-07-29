package com.era.nightdozor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.era.nightdozor.R;
import com.era.nightdozor.databinding.ActivityScanerBinding;
import com.era.nightdozor.mvp.contracts.ScannerContract;
import com.era.nightdozor.mvp.interactors.ScannerInteractorImpl;
import com.era.nightdozor.mvp.presenters.ScannerPresenterImpl;
import com.era.nightdozor.utils.PrefConfig;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.era.nightdozor.utils.Constants.initProgressDialog;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, ScannerContract.View {

    private ActivityScanerBinding binding;
    private ZXingScannerView mScannerView;
    private PrefConfig prefConfig;
    private ProgressDialog progressDialog;
    private ScannerContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scaner);
        setSupportActionBar(binding.toolbar);

        prefConfig = new PrefConfig(this);
        progressDialog = initProgressDialog(this, "Идёт загрузка");
        presenter = new ScannerPresenterImpl(this, new ScannerInteractorImpl());

        mScannerView = binding.scannerView;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            binding.toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_more_vert_black_24dp));

        }
        checkPermissions();

    }

    private void checkPermissions() {

        if (
            ActivityCompat.checkSelfPermission(getApplicationContext(), CAMERA) != PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{CAMERA}, 8974);
        } else {
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermissions();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v("LOGGERR", rawResult.getText());
        presenter.onSendQrCalled(rawResult.getText(), prefConfig.getToken());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 8974: {
                String[] unGrantedPermissions = findUnGrantedPermissions(permissions);
                if (unGrantedPermissions.length == 0) {
                    mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
                    mScannerView.startCamera();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Can't access messages.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private String[] findUnGrantedPermissions(String[] permissions) {
        List<String> unGrantedPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (!isPermissionGranted(permission)) {
                unGrantedPermissionList.add(permission);
            }
        }
        return unGrantedPermissionList.toArray(new String[0]);
    }

    private boolean isPermissionGranted(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
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
                startActivity(new Intent(ScannerActivity.this, AuthActivity.class));
                finish();
                prefConfig.setLoginStatus(false);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSendQrSuccess() {
        binding.scannerView.setVisibility(View.GONE);
        binding.success.setVisibility(View.VISIBLE);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        finish();
                    }
                },
                3000);
    }

    @Override
    public void showSnackbar(String message) {
        mScannerView.resumeCameraPreview(this);
        Snackbar.make(binding.mainView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }
}

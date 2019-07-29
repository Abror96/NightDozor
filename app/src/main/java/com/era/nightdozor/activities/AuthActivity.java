package com.era.nightdozor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.era.nightdozor.R;
import com.era.nightdozor.databinding.ActivityAuthBinding;
import com.era.nightdozor.mvp.contracts.AuthContract;
import com.era.nightdozor.mvp.interactors.AuthInteractorImpl;
import com.era.nightdozor.mvp.presenters.AuthPresenterImpl;
import com.era.nightdozor.utils.PrefConfig;
import com.google.android.material.snackbar.Snackbar;

import static com.era.nightdozor.utils.Constants.initProgressDialog;

public class AuthActivity extends AppCompatActivity implements AuthContract.View {

    private ProgressDialog progressDialog;
    private PrefConfig prefConfig;
    private AuthContract.Presenter presenter;
    private ActivityAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);

        progressDialog = initProgressDialog(this, "Идёт загрузка");
        prefConfig = new PrefConfig(this);
        presenter = new AuthPresenterImpl(this, new AuthInteractorImpl());

        if (prefConfig.getLoginStatus()) {
            Log.d("LOGGERR", "authSuccess: " + prefConfig.getToken());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        binding.auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = binding.etLogin.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                if (!login.isEmpty() && !password.isEmpty()) {
                    presenter.onAuthCalled(login, password);
                } else {
                    Snackbar.make(binding.mainView, "Заполните все поля", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void authSuccess(String token) {
        Log.d("LOGGERR", "authSuccess: " + token);
        prefConfig.setToken(token);
        prefConfig.setLoginStatus(true);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSnackbar(String message) {
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

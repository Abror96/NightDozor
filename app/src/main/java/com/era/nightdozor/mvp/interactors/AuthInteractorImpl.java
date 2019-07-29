package com.era.nightdozor.mvp.interactors;

import android.util.Log;

import com.era.nightdozor.mvp.contracts.AuthContract;
import com.era.nightdozor.retrofit.ApiClient;
import com.era.nightdozor.retrofit.ApiInterface;
import com.era.nightdozor.retrofit.models.responses.CommonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthInteractorImpl implements AuthContract.Interactor {

    private ApiInterface apiService =
            ApiClient.getInstance().create(ApiInterface.class);
    private String TAG = "LOGGERR auth";

    @Override
    public void onAuth(OnFinishedListener onFinishedListener, String login, String password) {
        Call<CommonResponse> sendEmailCall = apiService.auth(
                login,
                password
        );

        sendEmailCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    CommonResponse commonResponse = response.body();
                    if (commonResponse.getStatus().equals("OK")) {
                        onFinishedListener.onFinished(commonResponse.getObject());
                        Log.d(TAG, "auth: " + response.body().getStatus());
                    } else if (commonResponse.getStatus().toLowerCase().equals("error")) {
                        onFinishedListener.onFailure(commonResponse.getError());
                    }
                } else onFinishedListener.onFailure("Произошла ошибка сервера "+ statusCode +". Попытайтесь снова");
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure("Произошла ошибка сервера. Попытайтесь снова");
            }
        });
    }
}

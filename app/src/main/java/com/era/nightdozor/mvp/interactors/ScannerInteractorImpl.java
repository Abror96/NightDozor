package com.era.nightdozor.mvp.interactors;


import android.util.Log;

import com.era.nightdozor.mvp.contracts.ScannerContract;
import com.era.nightdozor.retrofit.ApiClient;
import com.era.nightdozor.retrofit.ApiInterface;
import com.era.nightdozor.retrofit.models.responses.CommonResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerInteractorImpl implements ScannerContract.Interactor {

    private ApiInterface apiService =
            ApiClient.getInstance().create(ApiInterface.class);
    private String TAG = "LOGGERR scanner";

    @Override
    public void sendQr(OnFinishedListener onFinishedListener, String qrCode, String token) {
        Call<CommonResponse> sendQr = apiService.sendQr("Basic " + token, qrCode);

        sendQr.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    CommonResponse commonResponse = response.body();
                    if (commonResponse.getStatus().equals("OK")) {
                        onFinishedListener.onFinished();
                        Log.d(TAG, "auth: " + response.body().getStatus());
                    } else if (commonResponse.getStatus().toLowerCase().equals("error")) {
                        onFinishedListener.onFailure(commonResponse.getError());
                    }
                } else {
                    onFinishedListener.onFailure("Произошла ошибка сервера " + statusCode + ". Попытайтесь снова");
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure("Произошла ошибка сервера. Попытайтесь снова");
            }
        });
    }
}

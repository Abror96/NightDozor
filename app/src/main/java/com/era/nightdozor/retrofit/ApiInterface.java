package com.era.nightdozor.retrofit;

import com.era.nightdozor.retrofit.models.responses.CommonResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    // auth
    @POST("user/auth")
    Call<CommonResponse> auth(@Query("login") String login,
                              @Query("password") String password);

    // send qr code
    @POST("event/reg")
    Call<CommonResponse> sendQr(@Header("Authorization") String token,
                                @Query("qrcode") String qrCode);

}

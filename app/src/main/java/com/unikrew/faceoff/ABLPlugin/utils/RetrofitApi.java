package com.unikrew.faceoff.ABLPlugin.utils;

import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraResponse;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.model.VerifyOtpBioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.VerifyOtpBioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.model.phase2.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitApi {

    /* Phase 1 API's */
    @POST("/RdaConsumer/api/consumer/public/bio-metric-verification")
    Call<BioMetricVerificationResponse> CNICpost(@Body BioMetricVerificationPostParams cd);

    @POST("/RdaConsumer/api/consumer/verify-otp-bio-metric-verification")
    Call<VerifyOtpBioMetricVerificationResponse> OtpPost(@Body VerifyOtpBioMetricVerificationPostParams pp, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/bio-metric-verification-nadra")
    Call<BioMetricVerificationNadraResponse> UpdateBioMetricStatus(@Body BioMetricVerificationNadraPostParams pp, @Header("Authorization") String accessToken);


    /* Phase 2 API's */

    @POST("/RdaConsumer/api/consumer/public/view-apps-generate-otp")
    Call<> viewAppsGenerateOtp(@Body ViewAppsGenerateOtpPostParams postParams);
}
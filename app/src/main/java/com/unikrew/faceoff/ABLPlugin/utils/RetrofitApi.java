package com.unikrew.faceoff.ABLPlugin.utils;

import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraResponse;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.model.VerifyOtpBioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.VerifyOtpBioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.BranchesModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.GetBranchPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.change_mobile_number.ChangeMobileNumberPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.change_mobile_number.ChangeMobileNumberResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application.DeleteDraftedApplicationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application.DeleteDraftedApplicationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerfiyOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;

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

    //Getting mobile networks
//    @POST("/RdaConsumer/api/common/public/lookup/code")
//    Call<MobileNetworkResponse> getMobileNetworks(@Body MobileNetworkPostParams mobileNetworkPostParams);

    @POST("/RdaConsumer/api/consumer/public/view-apps-generate-otp")
    Call<ViewAppsGenerateOtpResponse> viewAppsGenerateOtp(@Body ViewAppsGenerateOtpPostParams postParams);

    @POST("/RdaConsumer/api/consumer/public/get-drafted-apps-verify-otp")
    Call<GetDraftedAppsVerifyOtpResponse> getDraftedAppsVerifyOtp(@Body GetDraftedAppsVerfiyOtpPostParams postParams);

    @POST("/RdaConsumer/api/common/public/lookup/city/branch")
    Call<BranchesModel> getBranches(@Body GetBranchPostModel postParams);

    @POST("/RdaConsumer/api/consumer/consumer-account-detail")
    Call<GetConsumerAccountDetailsResponse> getConsumerAccDetails(@Body GetConsumerAccountDetailsPostParams postParams,@Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/public/update-account")
    Call<DeleteDraftedApplicationResponse> deleteDraftedApplication(@Body DeleteDraftedApplicationPostParams postParams,@Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/public/change-mobile-no")
    Call<ChangeMobileNumberResponse> changeMobileNumber(@Body ChangeMobileNumberPostParams postParams);
}
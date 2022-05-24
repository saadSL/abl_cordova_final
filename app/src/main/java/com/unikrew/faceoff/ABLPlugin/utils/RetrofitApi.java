package com.unikrew.faceoff.ABLPlugin.utils;

import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationNadraResponse;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.BioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.model.VerifyOtpBioMetricVerificationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.VerifyOtpBioMetricVerificationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.atm_cards.AtmCardsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.atm_cards.AtmCardsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.BranchesModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.banking_mode.GetBranchPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.change_mobile_number.ChangeMobileNumberPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.change_mobile_number.ChangeMobileNumberResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application.DeleteDraftedApplicationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.delete_drafted_application.DeleteDraftedApplicationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details.FatcaDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.fatca_details.FatcaDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info.FreelancerTaxPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.freelancer_tax_info.FreelancerTaxResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_consumer_account_details.GetConsumerAccountDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerfiyOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.get_drafted_apps_verify_otp.GetDraftedAppsVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.nature_of_account.SaveNatureOfAccountPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.nature_of_account.SaveNatureOfAccountResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.occupation.OccupationPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.occupation.OccupationResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_one.PersonalDetailsOnePostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_three.PersonalDetailsThreePostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.personal_dets_two.PersonalDetailsTwoPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.PostUserAddressModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.personal_dets.user_address.UserAddressResponseModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.profession.ProfessionPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.profession.ProfessionResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmployeeDetailsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.register_employee_details.RegisterEmploymentDetailsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details.RemitterDetailsPostModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.remitter_details.RemitterDetailsResponseModel;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_attachment.SaveAttachmentPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_attachment.SaveAttachmentResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.save_kyc.SaveKycResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypePostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.AccountTypeResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_account_type.MobileNetworkResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtp;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.setup_transaction.SetupTransactionResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.tin_unavailability_reasons.TinUnavailabilityReasonsResponse;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.view_apps_generate_otp.ViewAppsGenerateOtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.current_account.countries.CountriesResponse;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxPostParams;
import com.unikrew.faceoff.ABLPlugin.model.current_account.current_account_tax_info.CurrentAccountTaxResponse;
import com.unikrew.faceoff.ABLPlugin.model.current_account.setup_transactions.CurrentAccountSetupTransactionPostParams;
import com.unikrew.faceoff.ABLPlugin.model.current_account.setup_transactions.CurrentAccountSetupTransactionResponse;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship.RelationshipPostParams;
import com.unikrew.faceoff.ABLPlugin.model.joint_account_model.relationship.RelationshipResponse;

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


    /* Asaan Account API's */

    @POST("/RdaConsumer/api/consumer/public/view-apps-generate-otp")
    Call<ViewAppsGenerateOtpResponse> viewAppsGenerateOtp(@Body ViewAppsGenerateOtpPostParams postParams);

    @POST("/RdaConsumer/api/consumer/public/get-drafted-apps-verify-otp")
    Call<GetDraftedAppsVerifyOtpResponse> getDraftedAppsVerifyOtp(@Body GetDraftedAppsVerfiyOtpPostParams postParams);

    @POST("/RdaConsumer/api/common/public/lookup/city/branch")
    Call<BranchesModel> getBranches(@Body GetBranchPostModel postParams);

    @POST("/RdaConsumer/api/consumer/consumer-account-detail")
    Call<GetConsumerAccountDetailsResponse> getConsumerAccDetails(@Body GetConsumerAccountDetailsPostParams postParams, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/public/update-account")
    Call<DeleteDraftedApplicationResponse> deleteDraftedApplication(@Body DeleteDraftedApplicationPostParams postParams, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/public/change-mobile-no")
    Call<ChangeMobileNumberResponse> changeMobileNumber(@Body ChangeMobileNumberPostParams postParams);

    @POST("/RdaConsumer/api/common/public/lookup/code")
    Call<MobileNetworkResponse> getPurposeOfAccount(@Body MobileNetworkPostParams mobileNetworkPostParams);

    @POST("/RdaConsumer/api/consumer/register-verify-otp")
    Call<RegisterVerifyOtpResponse> registerVerifyOtp(@Body RegisterVerifyOtp registerVerifyOtp);

    @POST("/RdaConsumer/api/consumer/register-consumer-account-info")
    Call<AccountTypeResponse> postAccountType(@Body AccountTypePostParams accountTypeParams, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/register-consumer-account-info")
    Call<RemitterDetailsResponseModel> postRemitterDetails(@Body RemitterDetailsPostModel remitterDetailsPostModel, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/register-consumer-address")
    Call<UserAddressResponseModel> postUserAddress(@Body PostUserAddressModel postUserAddressModel, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/common/public/lookup/code")
    Call<OccupationResponse> getOccupation(@Body OccupationPostParams postParams);

    @POST("/RdaConsumer/api/common/public/lookup/code")
    Call<ProfessionResponse> getProfession(@Body ProfessionPostParams postParams);

    @POST("/RdaConsumer/api/consumer/register-consumer-basic-info")
    Call<RegisterEmploymentDetailsResponse> postPersonalDetailsOne(@Body PersonalDetailsOnePostModel postParams, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/register-consumer-basic-info")
    Call<RegisterEmploymentDetailsResponse> postPersonalDetailsTwo(@Body PersonalDetailsTwoPostModel postParams, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/register-consumer-basic-info")
    Call<RegisterEmploymentDetailsResponse> postPersonalDetailsThree(@Body PersonalDetailsThreePostModel postParams, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/register-consumer-account-info")
    Call<SetupTransactionResponse> setupTransactionDetails(@Body SetupTransactionPostParams postParams, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/save-kyc")
    Call<SaveKycResponse> saveMonthlySalary(@Body SaveKycPostParams saveKycPostParams, @Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/attachment/save-attachment")
    Call<SaveAttachmentResponse> saveAttachment(@Body SaveAttachmentPostParams postParams,@Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/register-consumer-account-info")
    Call<SaveNatureOfAccountResponse> saveNatureOfAccount(@Body SaveNatureOfAccountPostParams postParams,@Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/common/public/lookup/code")
    Call<AtmCardsResponse> getAtmCards(@Body AtmCardsPostParams postParams);

    @POST("/RdaConsumer/api/common/public/lookup/code")
    Call<TinUnavailabilityReasonsResponse> getTinUnavailabilityReasons(@Body TinUnavailabilityReasonsPostParams tinUnavailabilityReasonsPostParams);

    @POST("/RdaConsumer/api/consumer/register-consumer-basic-info")
    Call<FreelancerTaxResponse> submitFreelancerTaxDetails(@Body FreelancerTaxPostParams freelancerTaxPostParams,@Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/register-consumer-fatca-info")
    Call<FatcaDetailsResponse> submitFatcaDetails(@Body FatcaDetailsPostParams fatcaDetailsPostParams,@Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/common/public/lookup/code")
    Call<RelationshipResponse> getRelationships(@Body RelationshipPostParams postParams);

    @POST("/RdaConsumer/api/common/public/lookup/country")
    Call<CountriesResponse> getCountries();

    @POST("/RdaConsumer/api/consumer/register-consumer-basic-info")
    Call<CurrentAccountTaxResponse> postCurrentAccountTaxPersonalDetails(@Body CurrentAccountTaxPostParams currentAccountTaxPostParams,@Header("Authorization") String accessToken);

    @POST("/RdaConsumer/api/consumer/register-consumer-account-info")
    Call<CurrentAccountSetupTransactionResponse> postCurrentAccountTaxPersonalDetails(@Body CurrentAccountSetupTransactionPostParams postParams, @Header("Authorization") String accessToken);
}

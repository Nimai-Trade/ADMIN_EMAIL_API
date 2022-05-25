package com.nimai.admin.service;

import com.nimai.admin.payload.SorceDetailsReponseBean;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.nimai.admin.payload.BankRatingRequest;
import javax.validation.Valid;
import com.nimai.admin.payload.PreferredBankRequest;
import com.nimai.admin.payload.PreferredBankListResponse;
import com.nimai.admin.payload.KycFiledBean;
import com.nimai.admin.payload.TransactionRequestBody;
import com.nimai.admin.payload.SPlanBean;
import com.nimai.admin.payload.CouponBean;
import com.nimai.admin.payload.BankDetailsResponse;
import com.nimai.admin.payload.VasUpdateRequestBody;
import org.springframework.http.ResponseEntity;
import com.nimai.admin.payload.KycBDetailResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.PlanOfPaymentDetailsResponse;
import com.nimai.admin.payload.KycSDetailResponse;
import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.payload.QuotationDetailsResponse;
import java.util.List;

public interface BankService
{
    List<QuotationDetailsResponse> getQuotesUserId(final String userId);
    
   List<KycSDetailResponse> getKycDetailsUserId(final NimaiMCustomer userId);
    
    //List<KycBDetailResponse> getKycDetailsUserId(NimaiMCustomer paramNimaiMCustomer);
    
    List<PlanOfPaymentDetailsResponse> getPlanOPayDetails(final String userId);
    
    PagedResponse<?> getSearchBankDetail(final SearchRequest request);
    
    ResponseEntity<?> kycStatusUpdate(final KycBDetailResponse data);
    
    PagedResponse<?> getBankQuoteList(final SearchRequest request);
    
    ResponseEntity<?> makerKycStatusUpdate(final KycBDetailResponse data);
    
    PagedResponse<?> getMakerApprovedKyc(final SearchRequest request);
    
    ResponseEntity<?> wireTranferStatusUpdate(final VasUpdateRequestBody request);
    
    PagedResponse<?> getWireTransferList(final SearchRequest request);
    
    ResponseEntity<BankDetailsResponse> getBankDetailUserId(final String userid);
    
    KycBDetailResponse getMakerApprovedKycByKycId(final SearchRequest request);
    
    ResponseEntity<?> checkDuplicateCouponCode(final CouponBean request);
    
    ResponseEntity<?> checkDuplicateSPLan(final SPlanBean request);
    
    PagedResponse<?> getVasWireTransferList(final SearchRequest request);
    
    ResponseEntity<?> transactionStatusUpdate(final TransactionRequestBody request);
    
    ResponseEntity<?> makerTransactionStatusUpdate(final TransactionRequestBody request);
    
    ResponseEntity<?> kycFiledSave(final KycFiledBean data);
    
    ResponseEntity<?> kycViewFieldData(final KycFiledBean data);
    
    List<PreferredBankListResponse> getBankList();
    
    ResponseEntity<?> createOrUpdatePreferredBank(@Valid final PreferredBankRequest request);
    
    List<PreferredBankListResponse> viewPreferredBanks(@Valid final PreferredBankRequest request);
    
    ResponseEntity<?> createOrUpdateBankRating(@Valid final BankRatingRequest request);
    
    ResponseEntity<?> viewBankRatingDetails(@Valid final BankRatingRequest request);
    
    ResponseEntity<?> createOrUpdateUploadedPreferredBankList(final String userId, final MultipartFile file) throws IOException;
    
    List<?> viewUploadedPreferredBanks(@Valid final PreferredBankRequest request);
    
    List<?> viewMasterRating(final String string);
    
    List<?> viewAgency();
    
    List<?> getSourceDetailsList();
    
    ResponseEntity<?> saveSourceDetails(final SorceDetailsReponseBean data);
}
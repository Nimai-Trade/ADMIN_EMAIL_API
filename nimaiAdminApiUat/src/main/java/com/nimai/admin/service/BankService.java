package com.nimai.admin.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.model.NimaiFOwner;
import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.payload.BankDetailsResponse;
import com.nimai.admin.payload.CouponBean;
import com.nimai.admin.payload.KycBDetailResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.PlanOfPaymentDetailsResponse;
import com.nimai.admin.payload.QuotationDetailsResponse;
import com.nimai.admin.payload.SPlanBean;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.VasUpdateRequestBody;

/*bashir*/
public interface BankService {

	//ResponseEntity<BankDetailsResponse> getBankDetailUserId(String userId);

	List<QuotationDetailsResponse> getQuotesUserId(String userId);

	List<KycBDetailResponse> getKycDetailsUserId(NimaiMCustomer userId);

	List<PlanOfPaymentDetailsResponse> getPlanOPayDetails(String userId);

	PagedResponse<?> getSearchBankDetail(SearchRequest request);

	ResponseEntity<?> kycStatusUpdate(KycBDetailResponse data);

	PagedResponse<?> getBankQuoteList(SearchRequest request);

	ResponseEntity<?> makerKycStatusUpdate(KycBDetailResponse data);

	PagedResponse<?> getMakerApprovedKyc(SearchRequest request);

	ResponseEntity<?> wireTranferStatusUpdate(VasUpdateRequestBody request);

	PagedResponse<?> getWireTransferList(SearchRequest request);

	//ResponseEntity<BankDetailsResponse> getBankDetailUserId(String userid, NimaiFOwner nfo1);

	//ResponseEntity<BankDetailsResponse> getBankDetailUserId(String userid, String ownerId);

	ResponseEntity<BankDetailsResponse> getBankDetailUserId(String userid);

	KycBDetailResponse getMakerApprovedKycByKycId(SearchRequest request);

	ResponseEntity<?> checkDuplicateCouponCode(CouponBean request);

	ResponseEntity<?> checkDuplicateSPLan(SPlanBean request);

	PagedResponse<?> getVasWireTransferList(SearchRequest request);

}

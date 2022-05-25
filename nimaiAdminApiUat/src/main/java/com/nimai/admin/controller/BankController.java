package com.nimai.admin.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.nimai.admin.payload.SorceDetailsReponseBean;
import com.nimai.admin.payload.KycFiledBean;
import org.json.JSONException;
import java.io.IOException;
import com.nimai.admin.payload.SPlanBean;
import com.nimai.admin.payload.CouponBean;
import com.nimai.admin.payload.TransactionRequestBody;
import com.nimai.admin.payload.VasUpdateRequestBody;
import com.nimai.admin.payload.KycBDetailResponse;
import com.nimai.admin.payload.PlanOfPaymentDetailsResponse;
import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.payload.KycSDetailResponse;
import com.nimai.admin.payload.QuotationDetailsResponse;
import com.nimai.admin.payload.MasterRatingPayload;
import com.nimai.admin.payload.BankRatingRequest;
import javax.validation.Valid;
import com.nimai.admin.payload.PreferredBankRequest;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import com.nimai.admin.payload.BankDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.nimai.admin.payload.PagedResponse;
import org.springframework.web.bind.annotation.RequestBody;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.service.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import com.nimai.admin.service.BankService;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping({ "/api/bank" })
public class BankController
{
    private static Logger logger;
    @Autowired
    BankService bankService;
    
    @Autowired
    CurrencyConverterService currencyService;
    
    @PostMapping({ "/searchList" })
    public PagedResponse<?> getSearchCustomer(@RequestBody final SearchRequest request) {
        return (PagedResponse<?>)this.bankService.getSearchBankDetail(request);
    }
    
    @GetMapping({ "/details/{userid}" })
    public ResponseEntity<BankDetailsResponse> getBankDetailByUserId(@PathVariable("userid") final String userid) {
        System.out.println("user :: " + userid);
        return (ResponseEntity<BankDetailsResponse>)this.bankService.getBankDetailUserId(userid);
    }
    
    @GetMapping({ "/bankList" })
    public List<?> getBankListForPreferredBank() {
        System.out.println(":: Getting bank list :: ");
        return (List<?>)this.bankService.getBankList();
    }
    
    @GetMapping({ "/sourceList" })
    public List<?> getSourceDetailsList() {
        System.out.println(":: Source details list :: ");
        return (List<?>)this.bankService.getSourceDetailsList();
    }
    
    @PostMapping({ "/savePreferredBank" })
    public ResponseEntity<?> createOrUpdatePreferredBank(@Valid @RequestBody final PreferredBankRequest request) {
        return (ResponseEntity<?>)this.bankService.createOrUpdatePreferredBank(request);
    }
    
    @PostMapping({ "/saveBankRating" })
    public ResponseEntity<?> createOrUpdateBankRating(@Valid @RequestBody final BankRatingRequest request) {
        return (ResponseEntity<?>)this.bankService.createOrUpdateBankRating(request);
    }
    
    @PostMapping({ "/viewPreferredBank" })
    public List<?> viewPreferredBank(@Valid @RequestBody final PreferredBankRequest request) {
        return (List<?>)this.bankService.viewPreferredBanks(request);
    }
    
    @PostMapping({ "/getAgency" })
    public List<?> viewAgency() {
        return (List<?>)this.bankService.viewAgency();
    }
    
    @PostMapping({ "/getMasterRating" })
    public List<?> viewMasterRating(@Valid @RequestBody final MasterRatingPayload request) {
        return (List<?>)this.bankService.viewMasterRating(request.getAgency());
    }
    
    @PostMapping({ "/viewBankRating" })
    public ResponseEntity<?> viewBankRating(@Valid @RequestBody final BankRatingRequest request) {
        return (ResponseEntity<?>)this.bankService.viewBankRatingDetails(request);
    }
    
    @GetMapping({ "/quotes/{userId}" })
    public List<QuotationDetailsResponse> getQuotesDetailsByUserId(@PathVariable("userId") final String userId) {
        return (List<QuotationDetailsResponse>)this.bankService.getQuotesUserId(userId);
    }
    
    @GetMapping({ "/kyc/{userId}" })
    public List<KycSDetailResponse> grtKycDetailByUserId(@PathVariable("userId") final String userId) {
        final NimaiMCustomer c = new NimaiMCustomer();
        c.setUserid(userId);
        return (List<KycSDetailResponse>)this.bankService.getKycDetailsUserId(c);
    }
    
    @GetMapping({ "/planOfPayment/{userId}" })
    public List<PlanOfPaymentDetailsResponse> getPlanDetails(@PathVariable("userId") final String userId) {
        return (List<PlanOfPaymentDetailsResponse>)this.bankService.getPlanOPayDetails(userId);
    }
    
    @PostMapping({ "/kycStatusUpdate" })
    public ResponseEntity<?> kycStatusUpdate(@RequestBody final KycBDetailResponse data) {
        return (ResponseEntity<?>)this.bankService.kycStatusUpdate(data);
    }
    
    @PostMapping({ "/quoteList" })
    public PagedResponse<?> getBankQuoteList(@RequestBody final SearchRequest request) {
        return (PagedResponse<?>)this.bankService.getBankQuoteList(request);
    }
    
    @PostMapping({ "/makerKycStatusUpdate" })
    public ResponseEntity<?> makerKycStatusUpdate(@RequestBody final KycBDetailResponse data) {
        return (ResponseEntity<?>)this.bankService.makerKycStatusUpdate(data);
    }
    
    @PostMapping({ "/makerApprovedKyc" })
    public PagedResponse<?> getMakerApprovedKyc(@RequestBody final SearchRequest request) {
        System.out.println("Request: " + request);
        return (PagedResponse<?>)this.bankService.getMakerApprovedKyc(request);
    }
    
    @PostMapping({ "/viewMakerApprovedKycByKycId" })
    public KycBDetailResponse getMakerApprovedKycbyKycId(@RequestBody final SearchRequest request) {
        System.out.println("Request: " + request);
        final KycBDetailResponse response = this.bankService.getMakerApprovedKycByKycId(request);
        return response;
    }
    
    @PostMapping({ "/wireTranferStatusUpdate" })
    public ResponseEntity<?> wireTranferStatusUpdate(@RequestBody final VasUpdateRequestBody request) {
        return (ResponseEntity<?>)this.bankService.wireTranferStatusUpdate(request);
    }
    
    @PostMapping({ "/transactionStatusUpdate" })
    public ResponseEntity<?> transactionStatusUpdate(@RequestBody final TransactionRequestBody request) {
        return (ResponseEntity<?>)this.bankService.transactionStatusUpdate(request);
    }
    
    @PostMapping({ "/makerTransactionStatusUpdate" })
    public ResponseEntity<?> makerTransactionStatusUpdate(@RequestBody final TransactionRequestBody request) {
        return (ResponseEntity<?>)this.bankService.makerTransactionStatusUpdate(request);
    }
    
    @PostMapping({ "/wireTransferList" })
    public PagedResponse<?> getWireTransferList(@RequestBody final SearchRequest request) {
        return (PagedResponse<?>)this.bankService.getWireTransferList(request);
    }
    
    @PostMapping({ "/vasWireTransferList" })
    public PagedResponse<?> getVasWireTransferList(@RequestBody final SearchRequest request) {
        return (PagedResponse<?>)this.bankService.getVasWireTransferList(request);
    }
    
    @PostMapping({ "/checkDuplicateCouponCode" })
    public ResponseEntity<?> checkDuplicateCouponCode(@RequestBody final CouponBean request) {
        return (ResponseEntity<?>)this.bankService.checkDuplicateCouponCode(request);
    }
    
    @PostMapping({ "/checkDuplicateSPLan" })
    public ResponseEntity<?> checkDuplicateSPLan(@RequestBody final SPlanBean request) {
        return (ResponseEntity<?>)this.bankService.checkDuplicateSPLan(request);
    }
    
    @PostMapping({ "/currencyConverter" })
    public ResponseEntity<?> currencyConverter(@RequestBody final SPlanBean request) throws IOException, JSONException {
        final CurrencyConverterService currencyService = this.currencyService;
        CurrencyConverterService.sendHttpGetRequest("INR", "USD");
        return null;
    }
    
    @PostMapping({ "/saveFieldData" })
    public ResponseEntity<?> saveKycFiedData(@RequestBody final KycFiledBean data) {
        return (ResponseEntity<?>)this.bankService.kycFiledSave(data);
    }
    
    @PostMapping({ "/saveSourceDetails" })
    public ResponseEntity<?> saveSourceDetails(@RequestBody final SorceDetailsReponseBean data) {
        return (ResponseEntity<?>)this.bankService.saveSourceDetails(data);
    }
    
    @PostMapping({ "/viewFieldData" })
    public ResponseEntity<?> viewKycFiedData(@RequestBody final KycFiledBean data) {
        return (ResponseEntity<?>)this.bankService.kycViewFieldData(data);
    }
    
    @PostMapping({ "/saveUploadedPreferredBank/{userId}" })
    public ResponseEntity<?> createOrUpdateUploadedPreferredBank(@PathVariable("userId") final String userId, @RequestParam("file") final MultipartFile file) throws IOException {
        System.out.println("=== Uploading Preferred Bank");
        return (ResponseEntity<?>)this.bankService.createOrUpdateUploadedPreferredBankList(userId, file);
    }
    
    @PostMapping({ "/viewUploadedPreferredBank" })
    public List<?> viewUploadedPreferredBank(@Valid @RequestBody final PreferredBankRequest request) {
        return (List<?>)this.bankService.viewUploadedPreferredBanks(request);
    }
    
    static {
        BankController.logger = LoggerFactory.getLogger((Class)BankController.class);
    }
}
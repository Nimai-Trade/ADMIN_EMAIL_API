package com.nimai.lc.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nimai.lc.bean.BankDetailsBean;
import com.nimai.lc.bean.QuotationBean;
import com.nimai.lc.bean.QuotationMasterBean;
import com.nimai.lc.bean.TransactionQuotationBean;
import com.nimai.lc.entity.Quotation;
import com.nimai.lc.entity.QuotationMaster;

public interface QuotationService 
{
	public Integer saveQuotationdetails(QuotationBean quotationbean);
	public void confirmQuotationdetails(QuotationBean quotationbean);
	public HashMap<String, Integer> calculateQuote(Integer quotationId,String transId,String tableType);
	public void quotePlace(String transId);
	public int getQuotationdetailsToCount(String transId);
	public List<Quotation> getAllDraftQuotationDetails(String userId);
	public List<QuotationMaster> getQuotationDetailByUserIdAndStatus(String userId,String status);
	public List<QuotationMaster> getAllQuotationDetails(String userId);
	public void updateDraftQuotationDet(QuotationBean quotationbean);
	public void moveQuoteToHistory(Integer quotationId,String transId, String userId);
	public void updateQuotationMasterDetails(QuotationMasterBean quotationbean) throws ParseException;
	public Quotation getSpecificDraftQuotationDetail(Integer quotationId);
	public List<QuotationMasterBean> getQuotationDetailByUserIdAndTransactionId(String userId, String transactionId);
	public List<QuotationMaster> getQuotationDetailByQuotationId(Integer quotationId);
	public void updateQuotationForReject(Integer quotationId,String userId, String statusReason);
	public List<QuotationMaster> getQuotationDetailByBankUserId(String bankUserId);
	public List<String> getSavingsByUserId(String bankUserId);
	public List<String> getTotalSavings(String bankUserId);
	public List<String> getTotalSavingsUserId(String ccy,String bankUserId);
	public void updateQuotationForAccept(Integer quotationId,String transId);
	//public List<TransactionQuotationBean> getTransactionQuotationDetailByBankUserIdAndStatus(String bankUserId, String quotationPlaced, String transactionStatus) throws ParseException;
	public List<TransactionQuotationBean> getTransactionQuotationDetailByBankUserIdAndStatus(String bankUserId,String quotationStatus) throws ParseException;
	public List<TransactionQuotationBean> getTransactionQuotationDetailByQId(int qId) throws NumberFormatException,ParseException;	
	public List<Quotation> getAllDraftQuotationDetailsByBankUserId(String bankUserId);
	public HashMap<String, Integer> calculateQuote(Integer quotationId, String transactionId);
	public String findBankUserIdByQuotationId(Integer quotationId);
	public HashMap<String, String> getBankDetailsByBankUserId(String bankUserId);
	public List<BankDetailsBean> getBankDet(String bankUserId);
	public List<TransactionQuotationBean> getAllDraftTransQuotationDetailsByBankUserId(String bankUserId) throws NumberFormatException, ParseException;
	public Quotation findDraftQuotation(String transId, String userId, String bankUserId);
	public int findQuotationId(String transId, String userId, String bankUserId);
	public QuotationMaster getDetailsOfAcceptedTrans(String transId, String userId);
	public Integer getRejectedQuotationByTransactionId(String transactionId);
	public void updateQuotationStatusForReopenToRePlaced(Integer qid, String transactionId) throws ParseException;
	public List<QuotationMaster> getQuotationDetailByUserIdAndTransactionIdStatus(String userId, String transactionId,String status);
	public QuotationMaster getQuotationOfAcceptedQuote(String transactionId);
	public void sendMailToBank(QuotationBean quotationBean, String string);
	public Quotation getDraftQuotationDetails(Integer quotationId);
	public void deleteDraftQuotation(Integer quotationId);
	public String getTransactionId(Integer quotationId);
	public String getUserId(Integer quotationId);
	public Double getQuoteValueByQid(Integer quotationId);
	public void updateQuotationStatusForFreezeToPlaced(String transactionId, String bankUserId);
	public Integer getQuotationIdByTransIdUserId(String transactionId, String userId, String status);
	public String getBankUserIdByQId(Integer quotationId);
	public boolean withdrawQuoteByQid(QuotationBean quotationbean);
	public String calculateSavingPercent(String transId, Integer quotationId);
	public boolean checkDataForSaving(String lcCountry, String lcCurrency);
	public List<QuotationMaster> checkQuotationPlacedOrNot(String transactionId, String bankUserId);
	QuotationMaster getDetailsOfAcceptedTrans(String transId);
	public QuotationMaster getQuotationDetailByAcceptedQuotationId(Integer quotationId);
	
}

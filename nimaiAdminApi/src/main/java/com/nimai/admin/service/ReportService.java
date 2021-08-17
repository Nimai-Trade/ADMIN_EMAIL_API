package com.nimai.admin.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;

import com.nimai.admin.payload.SearchRequest;

public interface ReportService {

	ByteArrayInputStream getAllCustomerTransactionDetails(SearchRequest request, String filename);

	ByteArrayInputStream getAllBankTransactionDetails(SearchRequest request, String fileName);

	ByteArrayInputStream getTransactioReports(SearchRequest request, String filename);

	ByteArrayInputStream getNewUserReport(SearchRequest request, String filename);

	ByteArrayInputStream getUSubsRenewal(SearchRequest request, String filename);

	ByteArrayInputStream getDiscountReport(SearchRequest request, String filename);

	ByteArrayInputStream getProdReqReport(SearchRequest request, String filename);

	ByteArrayInputStream getReffReport(SearchRequest request, String filename) throws ParseException;

	ByteArrayInputStream getCountryWiseReport(SearchRequest request, String filename);

	ByteArrayInputStream getPaymentSubscriptionReport(SearchRequest request, String filename) throws ParseException;

	ByteArrayInputStream getCustRmPerfReport(SearchRequest request, String filename);

	ByteArrayInputStream getBankRmPerfReport(SearchRequest request, String filename);

	ByteArrayInputStream getBankRmPerfUwReport(SearchRequest request, String filename);

	ByteArrayInputStream getAllCustomerTransactionDetailsByUserId(SearchRequest request, String filename) throws ParseException;

}

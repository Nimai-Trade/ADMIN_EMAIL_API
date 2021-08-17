package com.nimai.admin.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.service.ReportService;
import com.nimai.admin.util.GenericResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/reports")
public class ReportsController {

	@Autowired
	ReportService reportService;
	
	@Autowired
	GenericResponse response;

	@PostMapping("/getReports")
	public ResponseEntity<?> getReport(@RequestBody SearchRequest request) throws ParseException {
		String filename = "";
		InputStreamResource file = null;
		try {
			if (request.getDateFrom() != null) {
				request.setDateFrom(request.getDateFrom().substring(0, 10));
				request.setDateTo(request.getDateTo().substring(0, 10));
			}
			System.out.println("From Date >> " + request.getDateFrom() + " To Date >> " + request.getDateTo());
			if (request.getRequirementType() != null) {

				switch (request.getRequirementType()) {
				case "Customer Trxn Report":
					filename = "Customer_Transaction_Reports_" + request.getUserId() + ".xlsx";
					file = new InputStreamResource(reportService.getAllCustomerTransactionDetailsByUserId(request, filename));//completed testing
					filename = "Customer_Transaction_Reports.xlsx";
			//	file = new InputStreamResource(reportService.getAllCustomerTransactionDetails(request, filename));//completed testing
				break;
				case "Bank Transaction Report":
					filename = "Bank_Transaction_Reports.xlsx";
					file = new InputStreamResource(reportService.getAllBankTransactionDetails(request, filename));//completed testing
					break;
				case "Payment & Subscription Report":// iob
					filename = "payment_and_subscription_report.xlsx";
					file = new InputStreamResource(reportService.getPaymentSubscriptionReport(request, filename));//completed testing
					break;
				case "Trxn Expiry Report":
					filename = "Transaction_Reports.xlsx";
					file = new InputStreamResource(reportService.getTransactioReports(request, filename));//completed testing
					break;
				case "New User Status Report":// iob
					filename = "new_User_Reports.xlsx";
					file = new InputStreamResource(reportService.getNewUserReport(request, filename));//completed testing
					break;
				case "User Subscription Renewal":
					filename = "user_subscription_renewal_report.xlsx";
					file = new InputStreamResource(reportService.getUSubsRenewal(request, filename));//completed testing
					break;
				case "Discount Coupon Report":// iob
					filename = "discount_coupon_report.xlsx";
					file = new InputStreamResource(reportService.getDiscountReport(request, filename));//completed testing
					break;
				case "Product Requirement Report":
					filename = "product_requirement_report.xlsx";
					file = new InputStreamResource(reportService.getProdReqReport(request, filename));//completed testing
					break;
				case "Referrer Report":
					filename = "referer_report.xlsx";
					file = new InputStreamResource(reportService.getReffReport(request, filename));
					break;
				case "Customer RM Performance Report":// iob
					filename = "customer_rm_performance_report.xlsx";
					file = new InputStreamResource(reportService.getCustRmPerfReport(request, filename));
					break;
				case "Bank RM Performance Report(cust)":
					filename = "bank_rm_performance_report.xlsx";
					file = new InputStreamResource(reportService.getBankRmPerfReport(request, filename));
					break;
				case "Bank RM Performance Report(uw)":
					filename = "bank_rm_performance_report_(uw).xlxs";
					file = new InputStreamResource(reportService.getBankRmPerfUwReport(request, filename));
					break;
				case "Country wise Report":
					filename = "country_wise_report.xlsx";
					file = new InputStreamResource(reportService.getCountryWiseReport(request, filename));
					break;

				default:
					break;

				}
				if(file.exists()) {
					return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
							.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
					
				}else {
					response.setFlag(0);
					response.setMessage("Data is not present for this date range please select proper dates");
					return new ResponseEntity<>(response,
							HttpStatus.OK);
				}
				

			} else {
				return new ResponseEntity<>("Please Select Report Type ", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setFlag(0);
			response.setMessage("Data is not present for this date range please select proper dates");
			return new ResponseEntity<>(response,
					HttpStatus.OK);

		}
	}

}

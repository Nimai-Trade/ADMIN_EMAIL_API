package com.nimai.admin.controller;

import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.service.ReportService;
import com.nimai.admin.util.GenericResponse;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin({"*"})
@RestController
@RequestMapping({"/api/reports"})
public class ReportsController {
  @Autowired
  ReportService reportService;
  
  @Autowired
  GenericResponse response;
  
  @PostMapping({"/getReports"})
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
            file = new InputStreamResource(this.reportService.getAllCustomerTransactionDetailsByUserId(request, filename));
            filename = "Customer_Transaction_Reports.xlsx";
            break;
          case "Quoted Transaction Report":
            filename = "Quoted_Transaction_Reports.xlsx";
            file = new InputStreamResource(this.reportService.getAllBankTransactionDetails(request, filename));
            break;
          case "Payment & Subscription Report":
            filename = "Payment_And_Subscription_Report.xlsx";
            file = new InputStreamResource(this.reportService.getPaymentSubscriptionReport(request, filename));
            break;
          case "Trxn Expiry Report":
            filename = "Transaction_Reports.xlsx";
            file = new InputStreamResource(this.reportService.getTransactioReports(request, filename));
            break;
          case "New User Status Report":
            filename = "new_User_Reports.xlsx";
            file = new InputStreamResource(this.reportService.getNewUserReport(request, filename));
            break;
          case "User Subscription Renewal":
            filename = "user_subscription_renewal_report.xlsx";
            file = new InputStreamResource(this.reportService.getUSubsRenewal(request, filename));
            break;
          case "Discount Coupon Report":
            filename = "discount_coupon_report.xlsx";
            file = new InputStreamResource(this.reportService.getDiscountReport(request, filename));
            break;
          case "Product Requirement Report":
            filename = "product_requirement_report.xlsx";
            file = new InputStreamResource(this.reportService.getProdReqReport(request, filename));
            break;
          case "Referrer Report":
            filename = "referer_report.xlsx";
            file = new InputStreamResource(this.reportService.getReffReport(request, filename));
            break;
          case "Customer RM Performance Report":
            filename = "customer_rm_performance_report.xlsx";
            file = new InputStreamResource(this.reportService.getCustRmPerfReport(request, filename));
            break;
          case "Bank RM Performance Report(cust)":
            filename = "bank_rm_performance_report.xlsx";
            file = new InputStreamResource(this.reportService.getBankRmPerfReport(request, filename));
            break;
          case "Bank RM Performance Report(uw)":
            filename = "bank_rm_performance_report_(uw).xlxs";
            file = new InputStreamResource(this.reportService.getBankRmPerfUwReport(request, filename));
            break;
          case "Country wise Report":
            filename = "country_wise_report.xlsx";
            file = new InputStreamResource(this.reportService.getCountryWiseReport(request, filename));
            break;
        } 
        if (file.exists())
          return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().header("Content-Disposition", new String[] { "attachment; filename=" + filename })).contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file); 
        this.response.setFlag(0);
        this.response.setMessage("Data is not present for this date range please select proper dates");
        return new ResponseEntity(this.response, HttpStatus.OK);
      } 
      return new ResponseEntity("Please Select Report Type ", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      e.printStackTrace();
      this.response.setFlag(0);
      this.response.setMessage("Data is not present for this date range please select proper dates");
      return new ResponseEntity(this.response, HttpStatus.OK);
    } 
  }
}

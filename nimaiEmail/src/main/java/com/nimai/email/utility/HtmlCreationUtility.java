   package com.nimai.email.utility;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;
import com.nimai.email.bean.InvoiceBean;
import com.nimai.email.bean.TupleBean;
import com.nimai.email.entity.AdminDailyCountDetailsBean;
import com.nimai.email.entity.AdminRmWiseCount;
import com.nimai.email.entity.BankMonthlyReport;
import com.nimai.email.entity.CustomerBankMonthlyReort;
import com.nimai.email.entity.EodBankDailyReport;
import com.nimai.email.entity.EodCustomerDailyReort;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiEmailScheduler;
import com.nimai.email.entity.NimaiMEmployee;
import com.nimai.email.entity.NimaiSubscriptionDetails;
import com.nimai.email.service.DailySchedulerServiceImpl;

public class HtmlCreationUtility {

	private static Logger logger = LoggerFactory.getLogger(DailySchedulerServiceImpl.class);

	public static String generateAdminHtmlTemplateReport(List<AdminRmWiseCount> rmWiseCount,
			AdminDailyCountDetailsBean adminDailyCount, NimaiMEmployee empDetails) {

		String employeeName = checkEmpName(empDetails.getEmpName());

		StringBuilder buf = new StringBuilder();
		try {
			buf.append("<br>Dear" + empDetails.getEmpName() + ", <br><br>\r\n" + "<br>\r\n"
					+ "<br><br>Addeded Yesterday<br><br>\r\n" + "<table  style=width:75%>\r\n" + "          <thead>\r\n"
					+ "              <tr>\r\n"
					+ "                  <th style=background-color:#98AFC7>Total Corporate</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Total underwriter bank</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Total Bank as customer</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Number of Transactions</th>\r\n"
					+ "  <th style=background-color:#98AFC7>Total Amount</th>\r\n" + "              </tr>\r\n"
					+ "          </thead>\r\n" + "          <tbody>\r\n" + "              <tr>\r\n"
					+ "                  <td style=background-color:#ADD8E6>"
					+ adminDailyCount.getYesterdayTotalCorporate() + "</td>\r\n"
					+ "                  <td style=background-color:#ADD8E6>" + adminDailyCount.getYesterdayTotalBAU()
					+ "</td>\r\n" + "                  <td style=background-color:#ADD8E6>"
					+ adminDailyCount.getYesterdayTotalBAC() + "</td>\r\n"
					+ "                  <td style=background-color:#ADD8E6>"
					+ adminDailyCount.getYesterdayTotalTransaction() + "</td>\r\n"
					+ "<td style=background-color:#ADD8E6>" + adminDailyCount.getYesterdayTotalAmount() + "</td>\r\n"
					+ "              </tr>           \r\n" + "          </tbody>\r\n" + "      </table>\r\n"
					+ "	    <br><br>\r\n" + "	  <br><br>Lifetime:\r\n" + "	  <br><br>\r\n" + "	  <table>\r\n"
					+ "          <thead>\r\n" + "              <tr>\r\n"
					+ "                  <th style=background-color:#98AFC7>Total Corporate</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Total underwriter bank</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Total Bank as customer</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Number of Transactions</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Total Amount</th>\r\n"
					+ "              </tr>\r\n" + "          </thead>\r\n" + "          <tbody>\r\n"
					+ "              <tr>\r\n" + "                  <td style=background-color:#ADD8E6>"
					+ adminDailyCount.getLifetimeTotalCorporate() + "</td>\r\n"
					+ "                  <td style=background-color:#ADD8E6>" + adminDailyCount.getLifetimeTotalBAU()
					+ "</td>\r\n" + "                  <td style=background-color:#ADD8E6>"
					+ adminDailyCount.getLifetimeTotalBAC() + "</td>\r\n"
					+ "                  <td style=background-color:#ADD8E6>"
					+ adminDailyCount.getLifetimeTotalTransaction() + "</td>\r\n"
					+ "                  <td style=background-color:#ADD8E6>" + adminDailyCount.getLifetimeTotalAmount()
					+ "</td>\r\n" + "              </tr>           \r\n" + "          </tbody>\r\n"
					+ "      </table>\r\n" + "	   <br><br>\r\n" + "	  <br><br>RM-wise count:\r\n"
					+ "	  <br><br>\r\n" + "	  <table>\r\n" + "          <thead>" + "             <tr>\r\n"
					+ "                  <th style=background-color:#98AFC7>RM Name</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Customer</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Bank As Underwriter</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Bank As Customer</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Referrer</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Addeded Yesterday</th>\r\n"
					+ "              </tr>\r\n" + "          </thead>");

			for (AdminRmWiseCount countDetails : rmWiseCount) {

				String empName = countDetails.getEmployeeName();
				String customerCount = String.valueOf(countDetails.getCustomer());
				String bAUCount = String.valueOf(countDetails.getBankasunderwriter());
				String bACCOunt = String.valueOf(countDetails.getBankcustomer());
				String rECount = String.valueOf(countDetails.getReferrer());
				String addededYes = String.valueOf(countDetails.getAddedyesterday());
				buf.append("<tr><td style=background-color:#ADD8E6>").append(empName).append("</td>")
						.append("<td style=background-color:#ADD8E6>").append(customerCount).append("</td>")
						.append("<td style=background-color:#ADD8E6>").append(bAUCount).append("</td>")
						.append("<td style=background-color:#ADD8E6>").append(bACCOunt).append("</td>")
						.append("<td style=background-color:#ADD8E6>").append(rECount).append("</td>")
						.append("<td style=background-color:#ADD8E6>").append(addededYes).append("</td></tr>");
			}
			buf.append("</tbody>\r\n" + "      </table>\r\n" + " <br>Warm Regards,<br>\r\n"
					+ "Team 360tf<br>  \r\n"
					+ "-------------------------------------------------------------------  \r\n"
					+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>  \r\n"
					+ "Please do not reply to this mail as this is automated mail service<br>\r\n" + "\r\n" + "\r\n");

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("============Inside generateAdminHtmlTemplateReport class==========");
			System.out.println(
					"===========================generateAdminHtmlTemplateReport:-=======================================");
		}

		return buf.toString();

	}

	public static String checkEmpName(String empName) {
		return empName;

	}

	public String generateCustEodHtmlTemplateReport(Map<String, List<EodCustomerDailyReort>> groupByUserId,
			NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		StringBuilder buf = new StringBuilder();

		String employeeName = checkEmpName(clientUseId.getFirstName());

		String pattern = "MM/dd/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String todayAsString = df.format(today);
		try {
			buf.append("<br>Dear" + " " + clientUseId.getFirstName() + ",\r\n"
					+ "<br><br>Please find below the transaction summary on NimaiTrade as of" + " " + todayAsString
					+ "\r\n" + "<br><br>\r\n" + "\r\n" + "<table style=width:75%>\r\n" + "          <thead>\r\n"
					+ "              <tr>\r\n"
					+ "                  <th style=background-color:#98AFC7>Transaction ID</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Amount</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Currency</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Total Quotes</th>\r\n" + "\r\n"
					+ "              </tr>\r\n" + "          </thead>\r\n" + "          <tbody>");

			for (Map.Entry<String, List<EodCustomerDailyReort>> entry : groupByUserId.entrySet()) {
				for (EodCustomerDailyReort value : entry.getValue()) {
					String transactioID = value.getTransactionId();

					String Amount = String.valueOf(value.getAmount());
					String currency = String.valueOf(value.getCurreny());
					String totalQuote = String.valueOf(value.getTotalQuotes());

					buf.append("<tr><td style=background-color:#ADD8E6>").append(transactioID).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(Amount).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(currency).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(totalQuote).append("</td></tr>");
				}
			}

			buf.append("</tbody>\r\n" + "      </table>\r\n" + "\r\n" + " <br>Warm Regards,<br>\r\n"
					+ "Team 360tf<br>  \r\n"
					+ "-------------------------------------------------------------------  \r\n"
					+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>  \r\n"
					+ "Please do not reply to this mail as this is automated mail service<br>\r\n" + "");

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("============Inside generateCustEodHtmlTemplateReport class==========");
			System.out.println(
					"===========================generateCustEodHtmlTemplateReport:-=======================================");
		}

		return buf.toString();

	}

	public String generateBankEodHtmlTemplateReport(Map<String, List<EodBankDailyReport>> groupByUserId,
			NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		StringBuilder buf = new StringBuilder();

		String employeeName = checkEmpName(clientUseId.getFirstName());

		String pattern = "MM/dd/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String todayAsString = df.format(today);
		try {
			buf.append("<br>Dear" + " " + clientUseId.getFirstName() + ",\r\n"
					+ "<br><br>Please find below the transaction summary on NimaiTrade as of" + " " + todayAsString
					+ "\r\n" + "<br><br>\r\n" + "\r\n" + "<table style=width:75%>\r\n" + "          <thead>\r\n"
					+ "              <tr>\r\n"
					+ "                  <th style=background-color:#98AFC7>Transaction ID</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Amount</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Quote</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Currency</th>\r\n" + "\r\n"
					+ "              </tr>\r\n" + "          </thead>\r\n" + "          <tbody>");

			for (Map.Entry<String, List<EodBankDailyReport>> entry : groupByUserId.entrySet()) {
				for (EodBankDailyReport value : entry.getValue()) {
					String transactioID = value.getTransactionId();

					String Amount = String.valueOf(value.getLcValue());
					String Quote = String.valueOf(value.getQuoteValue());
					String Currency = String.valueOf(value.getCurrency());

					buf.append("<tr><td style=background-color:#ADD8E6>").append(transactioID).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(Amount).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(Quote).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(Currency).append("</td></tr>");
				}
			}

			buf.append("</tbody>\r\n" + "      </table>\r\n" + "\r\n" + " <br>Warm Regards,<br>\r\n"
					+ "Team 360tf<br>  \r\n"
					+ "-------------------------------------------------------------------  \r\n"
					+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>  \r\n"
					+ "Please do not reply to this mail as this is automated mail service<br>\r\n" + "");

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("============Inside generateBankEodHtmlTemplateReport class==========");
			System.out.println(
					"===========================generateBankEodHtmlTemplateReport:-=======================================");
		}

		return buf.toString();

	}

	public static String generateInvoiceTemplate(NimaiEmailScheduler schdulerData, NimaiClient clientUseId,
			NimaiSubscriptionDetails subDetials) {

		return null;

	}

	public static InvoiceBean checkInvoiceDetails(NimaiEmailScheduler schdulerData, NimaiClient clientUseId,
			NimaiSubscriptionDetails subDetials) {
//		InvoiceBean bean=new InvoiceBean();
//		bean.setCustomerId(clientUseId.getUserid());
//		bean.setCustomerName(clientUseId.getFirstName());
//		bean.setCountry(clientUseId.getCountryName());
//		Utils util=new Utils();
//		bean.setInvoiceNumber(util.invoiceNumber());
//		bean.setInvoiceDate(util.invoiceDate());
//		bean.setReferenceNumber(subDetials.getSubscriptionId());
//		//bean.setVasPlanName(vasPlanName);
//		bean.setDiscountCouponAmt(String.valueOf(subDetials.getDiscountId()));
//		bean.setTotalAmt(String.valueOf(subDetials.getGrandAmount()));

		return null;

	}

	public String generateConsolidatedHtmlTemplateReport(List<TupleBean> listOfKeys2,
			NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		StringBuilder buf = new StringBuilder();

		String employeeName = checkEmpName(clientUseId.getFirstName());


		try {
			buf.append("<br>Dear" + " " + clientUseId.getFirstName() + ",\r\n"
					+ "<br><br>Subscription plan of below mentioned corporates is due for renewal in next 30 days."
					+ "\r\n" + "<br><br>\r\n" + "\r\n" + "<table style=width:75%>\r\n" + "          <thead>\r\n"
					+ "              <tr>\r\n"
					+ "                  <th style=background-color:#98AFC7;text-align: left;font-size:120%>Corporate</th>\r\n"
					+ "                  <th style=background-color:#98AFC7;text-align: left;font-size:120%>Current Plan\r\n" + "</th>\r\n"
					+ "                  <th style=background-color:#98AFC7;text-align: left;font-size:120%>Currency</th>\r\n"
					+ "                  <th style=background-color:#98AFC7;text-align: left;font-size:120%>Subscription Fee\r\n" + "</th>\r\n" + "\r\n"
					+ "                  <th style=background-color:#98AFC7;text-align: left;font-size:120%>Expiry Date\r\n" + "</th>\r\n"
					+ "              </tr>\r\n" + "          </thead>\r\n" + "          <tbody>");

			 
				for (TupleBean value : listOfKeys2) {
					String corporate = value.getCorporate();
					String currentPlan = String.valueOf(value.getsPlanName());
					String currency = String.valueOf(value.getCurrency());
					String subFee = value.getsPlanFee();
				
					String pattern = "MM/dd/yyyy";
					DateFormat df = new SimpleDateFormat(pattern);
					Date today = new Date();
					String expiryDate = df.format(value.getSplanendDate());
					System.out.println("=================@@@@@@@@@@@@@@@@@@expiryDate:"+expiryDate);

					buf.append("<tr><td style=background-color:#ADD8E6>").append(corporate).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(currentPlan).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(currency).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(subFee).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(expiryDate).append("</td></tr>");
				}
			
			buf.append("</tbody>\r\n" + "      </table>\r\n" + "\r\n" + " <br>Warm Regards,<br>\r\n"
					+ "Team 360tf<br>  \r\n"
					+ "-------------------------------------------------------------------  \r\n"
					+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>  \r\n"
					+ "Please do not reply to this mail as this is automated mail service<br>\r\n" + "");

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("============Inside generateCustEodHtmlTemplateReport class==========");
			System.out.println(
					"===========================generateCustEodHtmlTemplateReport:-=======================================");
		}

		return buf.toString();

	}


	public String generateCustMonthlyHtmlTemplateReport(Map<String, List<CustomerBankMonthlyReort>> groupByUserId,
			NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		StringBuilder buf = new StringBuilder();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		   LocalDateTime now = LocalDateTime.now();  			
		 LocalDate currentDate
         = LocalDate.parse(dtf.format(now));
		     Month month = currentDate.getMonth();
		     int years =currentDate.getYear();
     	
		try {
			buf.append("<br>Dear" + " " + clientUseId.getFirstName() + ",\r\n"
					+ "<br><br>Please find below your NimaiTrade summary for the month of " + " " + month +" "+years
					+ "\r\n" + "<br><br>\r\n" + "\r\n" + "<table style=width:75%>\r\n" + "          <thead>\r\n"
					+ "              <tr>\r\n"
					+ "                  <th style=background-color:#98AFC7>Transactions Placed</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Quotes Received</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Accepted Quotes</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Rejected Quotes</th>\r\n" 
					+ "                  <th style=background-color:#98AFC7>Expired Quotes</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Cancelled Transactions</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Credits Remaining</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Subsidiary Slot Remaining </th>\r\n"+ "\r\n"
					+ "              </tr>\r\n" + "          </thead>\r\n" + "          <tbody>");

			for (Map.Entry<String, List<CustomerBankMonthlyReort>> entry : groupByUserId.entrySet()) {
				for (CustomerBankMonthlyReort value : entry.getValue()) {
				    if(clientUseId.getUserid().equalsIgnoreCase(value.getUserId())) {	

					String transactioPlaced = String.valueOf(value.getTransactionPlaced());
					String quoteReceived = String.valueOf(value.getQuoteReceived());
					String acceptedQuotes = String.valueOf(value.getAcceptedQuotes());
					String rejectedQuotes = String.valueOf(value.getRejectdQuotes());
					String expiredQuotes = String.valueOf(value.getExpired_Quotes());
					String cancelledTransaction = String.valueOf(value.getCancelled_Transaction());
					String creditsRemainingQuotes = String.valueOf(value.getCreditRemaining());
					String subsidiarySlotRemaining = String.valueOf(value.getAcceptedQuotes());

					buf.append("<tr><td style=background-color:#ADD8E6>").append(transactioPlaced).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(quoteReceived).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(acceptedQuotes).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(rejectedQuotes).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(expiredQuotes).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(cancelledTransaction).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(creditsRemainingQuotes).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(subsidiarySlotRemaining).append("</td></tr>");
}
				}
			}

			buf.append("</tbody>\r\n" + "      </table>\r\n" + "\r\n" + " <br>Warm regards,<br>\r\n"
					+ "Team NimaiTrade<br>  \r\n"
					+ "-------------------------------------------------------------------  \r\n"
					+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>  \r\n"
					+ "Please do not reply to this mail as this is automated mail service<br>\r\n" + "");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("============Inside generateCustEodHtmlTemplateReport class==========");
		}

		return buf.toString();

	}
	
	public String generateBankMonthlyHtmlTemplateReport(Map<String, List<BankMonthlyReport>> groupByUserId,
			NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		StringBuilder buf = new StringBuilder();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		   LocalDateTime now = LocalDateTime.now();  			
		 LocalDate currentDate
         = LocalDate.parse(dtf.format(now));
		     Month month = currentDate.getMonth();
		     int years =currentDate.getYear();
     	
		try {
			buf.append("<br>Dear" + " " + clientUseId.getFirstName() + ",\r\n"
					+ "<br><br>Please find below your NimaiTrade summary for the month of " + " " + month +" "+years
					+ "\r\n" + "<br><br>\r\n" + "\r\n" + "<table style=width:75%>\r\n" + "          <thead>\r\n"
					+ "              <tr>\r\n"
					+ "                  <th style=background-color:#98AFC7>Transactions Received</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Quotes Placed</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Accepted Quotes</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Rejected Quotes</th>\r\n" 
					+ "                  <th style=background-color:#98AFC7>Expired Quotes</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Quotes withdrawn</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Credits Remaining</th>\r\n"
					+ "                  <th style=background-color:#98AFC7>Subsidiary Slot Remaining </th>\r\n"+ "\r\n"
					+ "              </tr>\r\n" + "          </thead>\r\n" + "          <tbody>");

			for (Map.Entry<String, List<BankMonthlyReport>> entry : groupByUserId.entrySet()) {
				for (BankMonthlyReport value : entry.getValue()) {
				    if(clientUseId.getUserid().equalsIgnoreCase(value.getUserId())) {	
					String transactionReceived = String.valueOf(value.getTransactionReceived());
					String quotePlaced = String.valueOf(value.getQuotePlaced());
					String acceptedQuotes = String.valueOf(value.getAcceptedQuotes());
					String rejectedQuotes = String.valueOf(value.getRejectdQuotes());
					String expiredQuotes = String.valueOf(value.getExpired_Quotes());
					String quotesWithdrawn = String.valueOf(value.getQuotesWithdrawn());
					String creditsRemainingQuotes = String.valueOf(value.getCreditRemaining());
					String subsidiarySlotRemaining = String.valueOf(value.getAcceptedQuotes());

					buf.append("<tr><td style=background-color:#ADD8E6>").append(transactionReceived).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(quotePlaced).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(acceptedQuotes).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(rejectedQuotes).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(expiredQuotes).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(quotesWithdrawn).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(creditsRemainingQuotes).append("</td>")
							.append("<td style=background-color:#ADD8E6>").append(subsidiarySlotRemaining).append("</td></tr>");
}
				}
			}

			buf.append("</tbody>\r\n" + "      </table>\r\n" + "\r\n" + " <br>Warm regards,<br>\r\n"
					+ "Team NimaiTrade<br>  \r\n"
					+ "-------------------------------------------------------------------  \r\n"
					+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>  \r\n"
					+ "Please do not reply to this mail as this is automated mail service<br>\r\n" + "");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("============Inside generateCustEodHtmlTemplateReport class==========");
		}

		return buf.toString();

	}
}

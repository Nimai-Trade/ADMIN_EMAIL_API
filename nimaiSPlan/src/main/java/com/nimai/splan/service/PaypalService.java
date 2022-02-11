package com.nimai.splan.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreateProfileResponse;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.InputFields;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Order;
import com.paypal.api.payments.Payee;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.api.payments.WebProfile;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.nimai.splan.config.PaypalPaymentIntent;
import com.nimai.splan.config.PaypalPaymentMethod;
import com.paypal.orders.ApplicationContext;

@Service
public class PaypalService {

	@Autowired
	private APIContext apiContext;
	
	public Payment createPayment(
			String userId,
			Double total, 
			String currency, 
			PaypalPaymentMethod method, 
			PaypalPaymentIntent intent, 
			String description,
			String cancelUrl, 
			String successUrl) throws PayPalRESTException{
		
		
		
		Amount amount = new Amount();
		amount.setCurrency(currency);
		total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();//+subtotal;
		//amount.setDetails(det);
		amount.setTotal(String.format("%.2f", total));
		
		Transaction transaction = new Transaction(); 
		//transaction.setItemList(itm);
		transaction.setDescription(description);
		transaction.setAmount(amount);
		//transaction.setp
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		PayerInfo pinfo=new PayerInfo();
		pinfo.setPayerId(userId);
		
		Payer payer = new Payer();
		payer.setPayerInfo(pinfo);
		payer.setPaymentMethod(method.toString());
		
		
		Payment payment = new Payment();
		
		payment.setIntent(intent.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		//payment.setn
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);

		InputFields ifi=new InputFields();
		ifi.setNoShipping(1);
		
		int aNumber = 0;
		aNumber = (int)((Math.random() * 9000000)+1000000);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		WebProfile wp=new WebProfile();
		wp.setName("ifi"+dateFormat.format(new Date())+""+aNumber);
		wp.setInputFields(ifi);
		CreateProfileResponse cpp=wp.create(apiContext);
		
		payment.setExperienceProfileId(cpp.getId());
		//wp.create(apiContext);
		return payment.create(apiContext);
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}
}

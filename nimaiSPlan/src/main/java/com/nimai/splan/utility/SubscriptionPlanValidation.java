package com.nimai.splan.utility;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.nimai.splan.payload.SubscriptionBean;

@Component
public class SubscriptionPlanValidation {

	final Pattern FIRSTNAME = Pattern.compile("[a-zA-Z]*$");

	public String subscriptionPlanValidation(SubscriptionBean SPlan) {

		String returnString = null;
		try {
			if ((SPlan.getSubscriptionName() == null) || (SPlan.getSubscriptionName().trim().isEmpty())) {
				return "Subscription Name should not be empty";
			}
			if (!FIRSTNAME.matcher(SPlan.getSubscriptionName()).matches()) {
				return "Invalid Subscription Name";
			}
			if ((SPlan.getSubscriptionAmount() == 0)) {
				return "Subscription Amount should not be empty";
			}
			if ((SPlan.getLcCount() == null) || (SPlan.getLcCount().trim().isEmpty())) {
				return "LC count should not be empty";
			}
			if ((SPlan.getSubsidiaries() == null) || (SPlan.getSubsidiaries().trim().isEmpty())) {
				return "Subscription Validity should not be empty";
			}
			if ((SPlan.getRelationshipManager() == null) || (SPlan.getRelationshipManager().trim().isEmpty())) {
				return "Subscription Validity should not be empty";
			}
			returnString = "success";
		} catch (Exception exception) {
			exception.printStackTrace();
			returnString = "Error";
		}
		return returnString;
	}
}

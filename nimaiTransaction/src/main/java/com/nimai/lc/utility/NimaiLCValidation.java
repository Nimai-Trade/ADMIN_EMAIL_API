package com.nimai.lc.utility;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimai.lc.bean.NimaiLCBean;
import com.nimai.lc.entity.NimaiLC;
import com.nimai.lc.service.LCService;

@Component
public class NimaiLCValidation 
{
	@Autowired
	LCService lcservice;
	
	public String validateLCDetails(NimaiLCBean nimaiLcBean) 
	{

		String returnStr = "Success";
		try 
		{
			if (!(nimaiLcBean.getUserId().substring(0, 2).equalsIgnoreCase("CU")) && !(nimaiLcBean.getUserId().substring(0, 2).equalsIgnoreCase("BC"))) 
			{
				returnStr = "You dont't have rights to upload LC.";
			}
			if ((nimaiLcBean.getlCIssuanceBank() == null) || (nimaiLcBean.getlCIssuanceBank().trim().isEmpty()))
			{
				returnStr = "Please enter Issuing Bank.";
			}
			if ((nimaiLcBean.getlCIssuanceBranch() == null) || (nimaiLcBean.getlCIssuanceBranch().trim().isEmpty()))
			{
				returnStr = "Please enter Issuing Branch.";
			}
			if ((nimaiLcBean.getSwiftCode() == null) || (nimaiLcBean.getlCIssuanceBank().trim().isEmpty()))
			{
				returnStr = "Please enter Swift Code.";
			}
			if ((nimaiLcBean.getlCIssuanceCountry() == null) || (nimaiLcBean.getlCIssuanceCountry().isEmpty()))
			{
				returnStr = "Country can't be left empty.";
			}
			if ((nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION AND DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("REFINANCING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("BANKER'S ACCEPTANCE")) &&
					((nimaiLcBean.getlCValue() == null) || (nimaiLcBean.getlCValue()<=0))) 
			{
				returnStr = "Please enter valid Amount.";
			}
			if ((nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION AND DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("REFINANCING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("BANKER'S ACCEPTANCE")) &&
					((nimaiLcBean.getlCCurrency() == null) || (nimaiLcBean.getlCCurrency().trim().isEmpty()))) 
			{
				returnStr = "Currency can't be left empty.";
			}
			if ((nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION AND DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("REFINANCING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("BANKER'S ACCEPTANCE")) &&
					((nimaiLcBean.getlCIssuingDate() == null) || (nimaiLcBean.getlCIssuingDate().toString().isEmpty()))) 
			{
				returnStr = "Issuing Date can't be left empty.";
			}
			if ((nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION AND DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("BANKER'S ACCEPTANCE"))&&
					((nimaiLcBean.getLastShipmentDate() == null) || (nimaiLcBean.getLastShipmentDate().toString().isEmpty()))) 
			{
				returnStr = "Last Shipment Date can't be left empty.";
			}
			if ((nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION AND DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("BANKER'S ACCEPTANCE")) &&
					((nimaiLcBean.getNegotiationDate() == null) || (nimaiLcBean.getNegotiationDate().toString().isEmpty()))) 
			{
				returnStr = "Negotiation Date can't be left empty.";
			}
			if ((nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("CONFIRMATION AND DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("DISCOUNTING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("REFINANCING") ||
					nimaiLcBean.getRequirementType().equalsIgnoreCase("BANKER'S ACCEPTANCE")) &&
					((nimaiLcBean.getGoodsType() == null) || (nimaiLcBean.getGoodsType().isEmpty()))) 
			{
				returnStr = "Goods can't be left empty.";
			}
			if ((nimaiLcBean.getFinancingPeriod() == null) || (nimaiLcBean.getFinancingPeriod().trim().isEmpty()))
			{
				returnStr = "Financing Period can't be left empty.";
			}
			if ((nimaiLcBean.getUserType() == null) || (nimaiLcBean.getUserType().trim().isEmpty()))
			{
				returnStr = "Please select User Type.";
			}
			else
			{
			if(nimaiLcBean.getUserType().equalsIgnoreCase("APPLICANT"))
			{
				if(nimaiLcBean.getApplicantName()==null || (nimaiLcBean.getApplicantName().trim().isEmpty()))
				{
					returnStr = "Please enter Applicant Name.";
				}
				if ((nimaiLcBean.getApplicantCountry() == null) || (nimaiLcBean.getApplicantCountry().trim().isEmpty()))
				{
					returnStr = "Applicant country can't be left empty.";
				}
				if ((nimaiLcBean.getBeneName() == null) || (nimaiLcBean.getBeneName().trim().isEmpty()))
				{
					returnStr = "Beneficiary Name can't be left empty.";
				}
				if ((nimaiLcBean.getBeneCountry() == null) || (nimaiLcBean.getBeneCountry().trim().isEmpty()))
				{
					returnStr = "Beneficiary Country can't be left empty.";
				}
				if ((nimaiLcBean.getBeneContactPerson() == null) || (nimaiLcBean.getBeneContactPerson().trim().isEmpty()))
				{
					returnStr = "Please enter Beneficiary Contact Person.";
				}
				if ((nimaiLcBean.getBeneContactPersonEmail() == null) || (nimaiLcBean.getBeneContactPersonEmail().trim().isEmpty()))
				{
					returnStr = "Please enter Beneficiary Contact Person Email.";
				}
				if ((nimaiLcBean.getBeneBankCountry() == null) || (nimaiLcBean.getBeneBankCountry().trim().isEmpty()))
				{
					returnStr = "Beneficiary Bank Country can't be left empty.";
				}
				if ((nimaiLcBean.getBeneBankName() == null) || (nimaiLcBean.getBeneBankName().trim().isEmpty()))
				{
					returnStr = "Beneficiary Bank Name can't be left empty.";
				}
				if ((nimaiLcBean.getBeneSwiftCode() == null) || (nimaiLcBean.getBeneSwiftCode().trim().isEmpty()))
				{
					returnStr = "Beneficiary Swift Code can't be left empty.";
				}
			}
			if(nimaiLcBean.getUserType().equalsIgnoreCase("BENEFICIARY"))
			{
				if(nimaiLcBean.getApplicantName()==null || (nimaiLcBean.getApplicantName().trim().isEmpty()))
				{
					returnStr = "Please enter Applicant Name.";
				}
				if ((nimaiLcBean.getApplicantCountry() == null) || (nimaiLcBean.getApplicantCountry().trim().isEmpty()))
				{
					returnStr = "Applicant country can't be left empty.";
				}
				if ((nimaiLcBean.getApplicantContactPerson() == null) || (nimaiLcBean.getApplicantContactPerson().trim().isEmpty()))
				{
					returnStr = "Please enter Applicant Contact Person.";
				}
				if ((nimaiLcBean.getApplicantContactPersonEmail() == null) || (nimaiLcBean.getApplicantContactPersonEmail().trim().isEmpty()))
				{
					returnStr = "Please enter Applicant Contact Person Email.";
				}
				if ((nimaiLcBean.getBeneName() == null) || (nimaiLcBean.getBeneName().trim().isEmpty()))
				{
					returnStr = "Beneficiary Name can't be left empty.";
				}
				if ((nimaiLcBean.getBeneCountry() == null) || (nimaiLcBean.getBeneCountry().trim().isEmpty()))
				{
					returnStr = "Beneficiary Country can't be left empty.";
				}
				
				if ((nimaiLcBean.getBeneBankCountry() == null) || (nimaiLcBean.getBeneBankCountry().trim().isEmpty()))
				{
					returnStr = "Beneficiary Bank Country can't be left empty.";
				}
				if ((nimaiLcBean.getBeneBankName() == null) || (nimaiLcBean.getBeneBankName().trim().isEmpty()))
				{
					returnStr = "Beneficiary Bank Name can't be left empty.";
				}
				if ((nimaiLcBean.getBeneSwiftCode() == null) || (nimaiLcBean.getBeneSwiftCode().trim().isEmpty()))
				{
					returnStr = "Beneficiary Swift Code can't be left empty.";
				}
			}
			}
			if ((nimaiLcBean.getPaymentTerms() == null) || (nimaiLcBean.getPaymentTerms().trim().isEmpty()))
			{
				returnStr = "Payment Terms can't be left empty.";
			}
			if ((nimaiLcBean.getStartDate() == null) || (nimaiLcBean.getStartDate().toString().isEmpty()))
			{
				returnStr = "Start Date can't be left empty.";
			}
			if ((nimaiLcBean.getLcMaturityDate() == null) || (nimaiLcBean.getLcMaturityDate().toString().isEmpty() ||
					(nimaiLcBean.getNegotiationDate() == null) || (nimaiLcBean.getNegotiationDate().toString().isEmpty())))
			{
				returnStr = "End Date(Maturity Date or Negotiation Date) can't be left empty.";
			}
			
			
			
			
			if ((nimaiLcBean.getLoadingCountry() == null) || (nimaiLcBean.getLoadingCountry().trim().isEmpty()))
			{
				returnStr = "Loading Country can't be left empty.";
			}
			if ((nimaiLcBean.getLoadingPort() == null) || (nimaiLcBean.getLoadingPort().trim().isEmpty()))
			{
				returnStr = "Loading Port can't be left empty.";
			}
			if ((nimaiLcBean.getDischargeCountry() == null) || (nimaiLcBean.getDischargeCountry().trim().isEmpty()))
			{
				returnStr = "Discharge Country can't be left empty.";
			}
			if ((nimaiLcBean.getDischargePort() == null) || (nimaiLcBean.getDischargePort().trim().isEmpty()))
			{
				returnStr = "Discharge Port can't be left empty.";
			}
			if ((nimaiLcBean.getChargesType() == null) || (nimaiLcBean.getChargesType().trim().isEmpty()))
			{
				returnStr = "Please select Charges Type.";
			}
			if ((nimaiLcBean.getValidity() == null) || (nimaiLcBean.getValidity().toString().isEmpty()))
			{
				returnStr = "Validity can't be left empty.";
			}
			if ((nimaiLcBean.getLcProForma() == null) || (nimaiLcBean.getLcProForma().trim().isEmpty()))
			{
				returnStr = "Please upload LC Pro Forma.";
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			returnStr = "Failed : " + e.getMessage();
		}
		return returnStr;
	}

	public String validateUserTransaction(NimaiLCBean nimaiLcBean) 
	{

		String returnStr = "Success";
		try 
		{
			/*NimaiLC tranDet=lcservice.findByTransactionIdToConfirm(nimaiLcBean.getTransactionId());
			if (tranDet==null) 
			{
				returnStr = "Please enter correct Transaction Id.";
			}*/
			NimaiLC tranUserDet=lcservice.findByTransactionUserIdToConfirm(nimaiLcBean.getTransactionId(),nimaiLcBean.getUserId());
			if (tranUserDet==null) 
			{
				returnStr = "Please enter correct Transaction Id / User Id for Transaction.";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			returnStr = "Failed : " + e.getMessage();
		}
		return returnStr;
	}
	
	public String validateTransaction(NimaiLCBean nimaiLcBean) 
	{

		String returnStr = "Success";
		try 
		{
			NimaiLC tranDet=lcservice.findByTransactionIdToConfirm(nimaiLcBean.getTransactionId());
			if (tranDet==null) 
			{
				returnStr = "Please enter correct Transaction Id.";
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			returnStr = "Failed : " + e.getMessage();
		}
		return returnStr;
	}
}

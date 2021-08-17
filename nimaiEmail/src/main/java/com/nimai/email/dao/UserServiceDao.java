package com.nimai.email.dao;

import java.util.Date;


import java.util.List;

import com.nimai.email.bean.BranchUserPassCodeBean;
import com.nimai.email.entity.InvoiceSequence;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiEmailScheduler;
import com.nimai.email.entity.NimaiEmailSchedulerAlertToBanks;
import com.nimai.email.entity.NimaiFSubsidiaries;
import com.nimai.email.entity.NimaiLC;
import com.nimai.email.entity.NimaiMBranch;
import com.nimai.email.entity.NimaiMEmployee;
import com.nimai.email.entity.NimaiMLogin;
import com.nimai.email.entity.NimaiMRefer;
import com.nimai.email.entity.NimaiSubscriptionDetails;
import com.nimai.email.entity.NimaiSubscriptionVas;
import com.nimai.email.entity.NimaiToken;



public interface UserServiceDao {

NimaiMLogin getCustomerDetailsByUserID(String userId) throws Exception;
NimaiMLogin update(NimaiMLogin nimaiLogin);
NimaiClient getClientDetailsbyUserId(String userId);
boolean checkUserTokenKey(String token);
NimaiMLogin getUserDetailsByTokenKey(String token);
NimaiClient getcliDetailsByEmailId(String emailId);
NimaiFSubsidiaries saveSubsidiaryDetails(NimaiFSubsidiaries subsidiaryDetails);
NimaiFSubsidiaries getSubsidiaryDetailsByToken(String token);
boolean isUserIdExisist(String userId);
NimaiMBranch getBranchUserDetails(String emailId, String string);
NimaiMBranch updateBranchUser(NimaiMBranch branchUserDetails);
NimaiMBranch updateBranchUserDetails(NimaiMBranch branchUserDetails);
NimaiMRefer saveReferTokenDetails(NimaiMRefer referDetails);
NimaiMRefer getReferDetailsByToken(String token);
NimaiMBranch getbranchDetailsByToken(String token,String passcode,String tokenke);
NimaiMBranch updateBranchUserDetails(String emailID, Date dnow, String passcodeValue, String token);
NimaiMBranch updateBranchUser(String passcode, String tokenKey, Date insertedDate, String emailId, int id, Date tokenExpiry);
boolean isEntryPresent(int id);
NimaiLC getTransactioDetailsByTransIs(String transactionid);
List<NimaiEmailScheduler> getSchedulerDetails();
void updateEmailStatus(int scedulerid);
void updateReferTokenDetails(Date tokenExpiry, String refertokenKey, NimaiClient clientUseId, Date insertedDate,
		String emailId,int referenceId);
NimaiMRefer getreferDetails(int referenceId);
NimaiMBranch getBranchUserbyUserId(String userid);
void updateEmailStatus(String userid);
void updateLoginEmailStatus(String userid);
List<NimaiLC> getCustTransactionList(Date todaysDate);
void updateReTokenLoginTable(String refertokenKey,Date insertedate,Date expiryTime, String userid);
void updateSubsidiaryTokenDetails(Date tokenExpiry, String refertokenKey, String clientUseId);
NimaiClient getClientDetailsBySubsidiaryId(String emailId);
NimaiMBranch saveBranchUser(NimaiMBranch branchUserDetails);
NimaiMRefer getreferDetailsByUserDetails(String emailAddress);
NimaiEmailScheduler saveSubDetails(NimaiEmailScheduler schedularData);
void updateInvalidIdEmailFlag(int scedulerid, String emailstatus);
NimaiSubscriptionDetails getsPlanDetailsBySubscriptionId(String subscriptionId);
InvoiceSequence getSequence();
NimaiMEmployee getempDetailsByEmpCode(String empCode);
NimaiSubscriptionDetails getSplanDetails(String subscriptionId, String string);
NimaiMBranch updateBranchUser(String emailId, Date dnow, String passcodeValue,String token);
NimaiMBranch updateBranchUserDetails(String token, Date dnow, int passcodeCount);
NimaiMBranch updatePassCount(Date currentDateTime, int count, int id);
NimaiMBranch updateBranchUserUnlockTime(String token, Date dnow, int passcodecount, Date accounUnlockTime);
NimaiMBranch getBranchUserDetailsById(int i);
NimaiMBranch getBranchUserDetailsByEmaild(String emailId);
NimaiMLogin existsByEmpCode(String empCode);
NimaiMBranch getbranchDetailsByToken(String token);
NimaiMBranch getbranchDetailsByTokenPassCode(String token, String passcode);
NimaiToken isTokenExists(String userId, String token);
NimaiMBranch getbranchDetailsByToPassCode(String token);
NimaiMBranch getbranchDetailsById(int id);
void updateInvalidCaptcha(BranchUserPassCodeBean passCodeBean, String flag);
NimaiMBranch getInvalidCaptchaStatus(BranchUserPassCodeBean passCodeBean);
NimaiToken isTokenExists(String userId);
void updateInvalCaptcha(String userId, String flag);
void updatepasscodeFlag(String userId, String flag);
NimaiSubscriptionDetails getSplanDetails(String userid);
NimaiSubscriptionVas getVasDetails(String subscriptionName, String userid);
NimaiMBranch getBranchUserDetailsByEMpId(String emailAddress, String empId);

}

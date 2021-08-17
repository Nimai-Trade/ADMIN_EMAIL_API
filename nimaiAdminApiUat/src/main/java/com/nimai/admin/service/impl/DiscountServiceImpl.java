package com.nimai.admin.service.impl;

import java.io.ByteArrayInputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nimai.admin.model.NimaiEmailScheduler;
import com.nimai.admin.model.NimaiMDiscount;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.model.NimaiMpDiscount;
import com.nimai.admin.payload.ApiResponse;
import com.nimai.admin.payload.DisQuantityAndCouponCode;
import com.nimai.admin.payload.DiscountCouponMResponse;
import com.nimai.admin.payload.DiscountCouponProjection;
import com.nimai.admin.payload.DiscountCouponRequest;
import com.nimai.admin.payload.DiscountCouponUpdateRequest;
import com.nimai.admin.payload.DiscountIdCouponCode;
import com.nimai.admin.payload.DiscountMpCouponRequest;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.repository.CustomerRepository;
import com.nimai.admin.repository.DiscountMpRepository;
import com.nimai.admin.repository.DiscountRepository;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.NimaiEmailSchedulerRepository;
import com.nimai.admin.repository.RoleRepository;
import com.nimai.admin.service.DiscountService;
import com.nimai.admin.specification.DiscountSpecification;
import com.nimai.admin.util.ExcelHelper;
import com.nimai.admin.util.Utility;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	DiscountRepository discountRepo;

	@Autowired
	DiscountMpRepository discountMpRepo;

	@Autowired
	DiscountSpecification discountSpec;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	NimaiEmailSchedulerRepository schRepo;

	@Autowired
	EmployeeRepository employeeRepo;

	@Autowired
	RoleRepository roleRepo;

	/*
	 * Save New Coupon/Edit Coupon
	 */
	@Override
	@Transactional
	public List<DiscountIdCouponCode> saveDiscountCoupon(DiscountCouponRequest request, String coupType) {

		List<DiscountIdCouponCode> idCoupons = new ArrayList<DiscountIdCouponCode>();
		int roleId = getEmpRoleId(request.getCouponFor());
		try {

			DisQuantityAndCouponCode[] details = request.getDetails().toArray(new DisQuantityAndCouponCode[0]);

			for (int i = 0; i < details.length; i++) {
				System.out.print(details[i].getQuantity() + " " + details[i].getCouponCode());
				String designation = "RM";
				String accountStatus = "ACTIVE";
				// List<NimaiMEmployee>
				// empDetails=employeeRepo.findEmpListByCountry(request.getCountry(),designation,accountStatus);
				NimaiMDiscount disc = new NimaiMDiscount();
				disc.setDiscountType(request.getDiscountType());
				disc.setAmount(request.getAmount());
				disc.setCurrency(request.getCurrency());
				disc.setDiscountPercentage(request.getDiscountPercentage());
				disc.setMaxDiscount(request.getMaxDiscount());
				disc.setCouponFor(request.getCouponFor());
				disc.setSubscriptionPlan(request.getSubscriptionPlan());
				disc.setCountry(request.getCountry());
				disc.setStatus("Pending");
				disc.setConsumedCoupons(0);
				disc.setStartDate(request.getStartDate());
				disc.setStartTime(Time.valueOf(request.getStartTime().toString()));
				disc.setEndDate(request.getEndDate());
				disc.setEndTime(request.getEndTime());
				disc.setCouponType(coupType);

				disc.setQuantity(details[i].getQuantity());
				disc.setCouponCode(details[i].getCouponCode());
				disc = discountRepo.save(disc);
				System.out.println("Start Time: " + request.getStartTime());
				System.out.println("End Time: " + request.getEndTime());
				System.out.println("============= After Save =============");
				System.out.println("Start Time: " + disc.getStartTime());
				System.out.println("End Time: " + disc.getEndTime());

				DiscountIdCouponCode dId = new DiscountIdCouponCode();
				dId.setDiscountId(disc.getDiscountId());
				dId.setCouponCode(disc.getCouponCode());

				idCoupons.add(dId);
			}
		} catch (Exception e) {
			System.out.println("Exception in Discount :" + e.getMessage());
			e.printStackTrace();

		}
		return idCoupons;
	}

	/*
	 * Approve/Reject/Deactivate Coupon
	 */
	@Transactional
	@Override
	public ResponseEntity<?> updateCoupon(DiscountCouponUpdateRequest request) {
		try {
			String msg = "";
			NimaiMDiscount plan = discountRepo.getOne(request.getDiscountId());
			int roleId = getEmpRoleId(request.getCouponFor());
			if (plan.getCreatedBy().equalsIgnoreCase(Utility.getUserId())) {
				return new ResponseEntity<>(new ApiResponse(true, "Action Denied, unauthorized operation!!!"),
						HttpStatus.OK);

			}
			if (request.getStatus().equalsIgnoreCase("Approved")) {
				plan.setStatus("Active");
				System.out.println("===================role id:" + roleId);
				System.out.println("===================Country:" + plan.getCountry());
				if (roleId != 0) {
					List<Tuple> eligibleRmList = employeeRepo.getEMpList(roleId, plan.getCountry());
					System.out.println("====================performanceList:" + eligibleRmList.size());
				
					if (eligibleRmList.size() > 0) {
						for (Tuple emp : eligibleRmList) {

							NimaiEmailScheduler schdata = new NimaiEmailScheduler();
							schdata.setCouponCode(plan.getCouponCode());
							schdata.setSubscriptionName(plan.getSubscriptionPlan());
							schdata.setrMName(
									(String) emp.get("EMP_FIRST_NAME") != null ? (String) emp.get("EMP_FIRST_NAME")
											: "");
							schdata.setrMemailId(
									(String) emp.get("EMP_EMAIL") != null ? (String) emp.get("EMP_EMAIL") : "");
							schdata.setDiscountType(request.getDiscountType());
							schdata.setsPLanCountry(plan.getCountry());
							schdata.setCustomerType(request.getCouponFor());
							DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
							schdata.setStartDate(df.format((plan.getStartDate())));
							DateFormat dt = new SimpleDateFormat("HH:mm:ss");
							schdata.setStartTime(dt.format(plan.getStartTime()));
							schdata.setEndDate(df.format(plan.getEndDate()));
							schdata.setEndTime(dt.format(plan.getEndTime()));
							schdata.setQuantity(String.valueOf(plan.getQuantity()));
							Calendar cal = Calendar.getInstance();
							Date today = cal.getTime();
							schdata.setInsertedDate(today);
							if (request.getDiscountType().equalsIgnoreCase("Fixed")) {
								schdata.setEvent("FIXED_COUPON_CODE_CREATED_ALERT");
							} else if (request.getDiscountType().equalsIgnoreCase("%")) {
								schdata.setEvent("Percent_COUPON_CODE_CREATED_ALERT");
								schdata.setDiscountPercentage(String.valueOf(plan.getDiscountPercentage()));
							}
							schdata.setEmailStatus("Pending");
							schRepo.save(schdata);

						}
					} else {
						System.out.println("============Countrywise List is not in DiscountServiceImpl present");
					}
				} else {
					System.out.println("============role Id is not in DiscountServiceImpl present");
				}
				msg = "Discount Coupon Approved for " + plan.getCountry();
			} else if (request.getStatus().equalsIgnoreCase("Deactivate")) {
				plan.setStatus("Inactive");
				msg = "Discount Coupon" + request.getDiscountId() + " Deactivated ";
			} else {
				plan.setStatus(request.getStatus());
				msg = "Discount Coupon Rejected for " + plan.getCountry();
			}
			plan.setModifiedBy(request.getModifiedBy());
			plan.setModifiedDate(new Date());
			plan.setApprovedBy(request.getModifiedBy());
			plan.setApprovalDate(new Date());
			NimaiMDiscount dis = discountRepo.save(plan);
			NimaiMpDiscount mpDis = discountMpRepo.getOne(dis.getDiscountId());
			if (mpDis != null) {

				discountMpRepo.updateMpDiscountStatus(dis.getStatus(), new NimaiMDiscount(dis.getDiscountId()));
			}

			return new ResponseEntity<>(new ApiResponse(true, msg), HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Exception in Discount Coupon update :" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse(true, "Error due to some technical issue"),
					HttpStatus.EXPECTATION_FAILED);

		}
	}

	/**
	 * List coupon details on basis of Inactive/no input
	 */
	@Override
	public PagedResponse<?> getCouponDetails(SearchRequest request) {
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());

		if (request.getRole() != null && request.getRole().equalsIgnoreCase("Customer RM")) {
			request.setCustomerType("CUSTOMER");
		} else if (request.getRole() != null && request.getRole().equalsIgnoreCase("Bank RM")) {
			request.setCustomerType("BANK");
		}
		Page<NimaiMDiscount> disclist = discountRepo.findAll(discountSpec.getFilter(request), pageable);

		List<DiscountCouponMResponse> responses = disclist.map(sub -> {
			DiscountCouponMResponse response = new DiscountCouponMResponse();
			BeanUtils.copyProperties(sub, response);
			return response;
		}).getContent();
		return new PagedResponse<>(responses, disclist.getNumber(), disclist.getSize(), disclist.getTotalElements(),
				disclist.getTotalPages(), disclist.isLast());
	}

	/* Get Coupon Details by Id */
	@Override
	public ResponseEntity<?> getCouponDetailsId(Integer discountId) {
		System.out.println("Coupon Id : " + discountId);
		try {
			NimaiMDiscount disc = discountRepo.getOne(discountId);

			DiscountCouponMResponse response = new DiscountCouponMResponse();
			BeanUtils.copyProperties(disc, response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(new ApiResponse(true, "No coupon with ID : " + discountId + " found"),
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	/* Active Coupons List */

	@Override
	public PagedResponse<?> getActiveCoupons(SearchRequest request) {
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());
		Page<NimaiMDiscount> discList = discountRepo.getActiveCoup(pageable);
		List<DiscountCouponMResponse> responses = discList.map(sub -> {
			DiscountCouponMResponse response = new DiscountCouponMResponse();
			BeanUtils.copyProperties(sub, response);
			return response;
		}).getContent();

		return new PagedResponse<>(responses, discList.getNumber(), discList.getSize(), discList.getTotalElements(),
				discList.getTotalPages(), discList.isLast());

	}

	private int getEmpRoleId(String customerType) {
		// TODO Auto-generated method stub
		int roleId = 0;
		if (customerType.equalsIgnoreCase("CUSTOMER")) {
			String role = "Bank RM";
			NimaiMRole bankRoleDtails = roleRepo.getBankRoleId(role);
			roleId = bankRoleDtails.getRoleId();
			return roleId;
		} else if (customerType.equalsIgnoreCase("BANK") || customerType.equalsIgnoreCase("BANK AS CUSTOMER")) {
			String role = "Customer RM";
			NimaiMRole cuRoleDtails = roleRepo.getBankRoleId(role);
			roleId = cuRoleDtails.getRoleId();
			return roleId;
		}
		return 0;

	}

	@Override
	public ByteArrayInputStream load(String country, String customerType, String bankType, List<String> coupon) {

		List<Object[]> subsDetails = customerRepo.getByCountryAndCustType(country, customerType, bankType);
		List<DiscountCouponProjection> couponList = new ArrayList<DiscountCouponProjection>();
		for (Object[] details : subsDetails) {
			DiscountCouponProjection dp = new DiscountCouponProjection();
			dp.setUserid((String) details[0]);
			dp.setFirstName((String) details[1]);
			dp.setLastName((String) details[2]);
			dp.setCompanyName((String) details[3]);
			dp.setCountryName((String) details[4]);
			dp.setSubscriptionName((String) details[5]);
			dp.setIsVasApplied((Integer) details[6]);
			dp.setStatus((String) details[7]);
			dp.setStartDate((Date) details[8]);
			dp.setEndDate((Date) details[9]);
			dp.setCreditsRemaining((Double) details[10]);
			dp.setCoupon(coupon);
			couponList.add(dp);
		}
		ByteArrayInputStream in = ExcelHelper.userDetailsToExcel(couponList, coupon);
		return in;

	}

	@Override
	public List<NimaiMpDiscount> saveMDiscount(List<DiscountMpCouponRequest> mpDiscouDetails,
			List<DiscountIdCouponCode> dCode) {
		List<NimaiMpDiscount> disc = new ArrayList<>();
		for (DiscountMpCouponRequest mCoupon : mpDiscouDetails) {

			NimaiMpDiscount mpDiscount = new NimaiMpDiscount();
			mpDiscount.setUserid(mCoupon.getUserid());
			mpDiscount.setFirstName(mCoupon.getFirstName());
			mpDiscount.setLastName(mCoupon.getLastName());
			mpDiscount.setCountry(mCoupon.getCountry());
			mpDiscount.setCompanyName(mCoupon.getCompanyName());
			mpDiscount.setVas(mCoupon.getVas());
			mpDiscount.setSubscriptionPlan(mCoupon.getSubscriptionPlan());
			mpDiscount.setCurrentStatus(mCoupon.getCurrentStatus());
			mpDiscount.setStartDate(mCoupon.getStartDate());
			mpDiscount.setEndDate(mCoupon.getEndDate());
			mpDiscount.setCreditsRemaining(mCoupon.getCreditsRemaining());
			mpDiscount.setCouponCode(mCoupon.getCouponCode());
			mpDiscount.setStatus("Pending");
			for (DiscountIdCouponCode code : dCode) {
				if (mCoupon.getCouponCode().equals(code.getCouponCode())) {
					mpDiscount.setDiscountId(new NimaiMDiscount(code.getDiscountId()));
				}
			}

			mpDiscount = discountMpRepo.save(mpDiscount);

			disc.add(mpDiscount);

		}
		return disc;
	}

}

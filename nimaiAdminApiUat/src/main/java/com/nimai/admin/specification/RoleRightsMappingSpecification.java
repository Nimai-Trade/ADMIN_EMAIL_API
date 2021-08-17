//package com.nimai.admin.specification;
//
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Component;
//
//import com.nimai.admin.model.NimaiMpRoleRights;
//
//@Component
//public class RoleRightsMappingSpecification extends BaseSpecification<NimaiMpRoleRights, 	>{
//	
//	@Override
//	public Specification<NimaiMpRoleRights> getFilter(MouldListRequest request) {
//		return (root, query, cb) -> {
//			return where((mouldContains("mouldStatus", request.getMouldStatus())))
//					.and(mouldContains("mouldType", request.getMouldType()))
//					.and(mouldContains("status", request.getStatus()))
////					.and((mouldContains("designNumber", request.getDesignNumber())))
//					.and(inMouldNumber(request.getMouldNumber())).toPredicate(root, query, cb);
//		};
//	}
//
////	private Specification<RjMMould> mouldContains(String attribute, String value) {
////		return (root, query, cb) -> {
////			if (value == null) {
////				return null;
////			}
////
////			return cb.equal(root.get(attribute), value);
////		};
////	}
////
////	private Specification<RjMMould> inMouldNumber(String designNumber) {
////		return (root, query, cb) -> {
////			if (designNumber != null) {
////				return cb.like(root.get("mouldNumber"), cb.literal("%" + designNumber + "%"));
////			} else {
////				return null;
////			}
////		};
////	}
//}

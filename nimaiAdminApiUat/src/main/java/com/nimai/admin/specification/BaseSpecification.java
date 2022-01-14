package com.nimai.admin.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.nimai.admin.model.NimaiMDiscount;
import com.nimai.admin.payload.SearchRequest;

public abstract class BaseSpecification<T, U> {

    public abstract Specification<T> getFilter(U request);


}

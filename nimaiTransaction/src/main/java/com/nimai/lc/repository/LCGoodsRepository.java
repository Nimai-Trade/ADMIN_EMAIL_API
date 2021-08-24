package com.nimai.lc.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.NimaiLC;
import com.nimai.lc.entity.NimaiLCGoods;

@Repository
@Transactional
public interface LCGoodsRepository extends JpaRepository<NimaiLCGoods, String>
{

	NimaiLCGoods findByNimailcgoods(NimaiLC nimailc);

}

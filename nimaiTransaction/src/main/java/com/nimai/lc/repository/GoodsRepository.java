package com.nimai.lc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimai.lc.entity.Goods;
import com.nimai.lc.entity.NimaiLCPort;

public interface GoodsRepository extends JpaRepository<Goods, Integer>
{

}

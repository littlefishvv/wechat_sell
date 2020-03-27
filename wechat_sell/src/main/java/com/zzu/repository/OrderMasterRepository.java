package com.zzu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zzu.dataobject.OrderMaster;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String>{
	//把某人的某页订单查出来 按页表示
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenId,Pageable pageable);
    
    
}

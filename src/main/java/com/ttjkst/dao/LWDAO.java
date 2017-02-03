package com.ttjkst.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ttjkst.bean.LeaveWords;
public interface LWDAO extends JpaSpecificationExecutor<LeaveWords>,JpaRepository<LeaveWords, Long>{
	
	@Query("select count(l) from LeaveWords l where l.leaveFor.id = ?1 and l.hasRead = false")
	public Long getCountByReaded(Long wordId);
	
	@Query("select count(l) from LeaveWords l where l.leaveFor.id = ?1")
	public Long getCount(Long wordId);
	@Modifying
	@Query("update LeaveWords l set l.hasRead = ?1 where l.id = ?2")
	public void updateHasRead(Boolean hasRead,Long id);
	
	

}

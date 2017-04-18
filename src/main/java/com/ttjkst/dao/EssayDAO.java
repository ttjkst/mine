package com.ttjkst.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ttjkst.bean.Essay;

public interface EssayDAO extends CrudRepository<Essay, String>,JpaSpecificationExecutor<Essay>{

}

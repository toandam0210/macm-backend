package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer>{

	@Query(value = "SELECT * FROM rule order by id ASC", nativeQuery = true)
	List<Rule> findAllRuleAndSortById();
}

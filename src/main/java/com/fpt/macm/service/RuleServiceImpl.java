package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.entity.Rule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.RuleRepository;

@Service
public class RuleServiceImpl implements RuleService{

	@Autowired RuleRepository ruleRepository;
	
	@Override
	public ResponseMessage getAllRule(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<Rule> pageResponse = ruleRepository.findAll(paging);
			List<Rule> rules = new ArrayList<Rule>();
			if (pageResponse != null && pageResponse.hasContent()) {
				rules = pageResponse.getContent();
			}
			responseMessage.setData(rules);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
			responseMessage.setTotalPage(pageResponse.getTotalPages());
			responseMessage.setMessage(Constant.MSG_019);
			
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage addRule(Rule rule) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
			rule.setCreatedBy("toandv");
			rule.setCreatedOn(LocalDateTime.now());
			ruleRepository.save(rule);
			responseMessage.setData(Arrays.asList(rule));
			responseMessage.setMessage(Constant.MSG_020);
		return responseMessage;
	}

	@Override
	public ResponseMessage editRule(int ruleId, Rule rule) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Rule> ruleOp = ruleRepository.findById(ruleId);
			Rule currentRule = ruleOp.get();
			currentRule.setUpdatedBy("toandv");
			currentRule.setUpdatedOn(LocalDateTime.now());
			currentRule.setDescription(rule.getDescription());
			ruleRepository.save(currentRule);
			responseMessage.setData(Arrays.asList(currentRule));
			responseMessage.setMessage(Constant.MSG_021);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteRule(int ruleId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Rule> ruleOp = ruleRepository.findById(ruleId);
			Rule rule = ruleOp.get();
			ruleRepository.delete(rule);
			responseMessage.setData(Arrays.asList(rule));
			responseMessage.setMessage(Constant.MSG_022);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}

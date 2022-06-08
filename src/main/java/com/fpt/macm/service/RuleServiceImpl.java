package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Rule;
import com.fpt.macm.repository.RuleRepository;

@Service
public class RuleServiceImpl implements RuleService{

	@Autowired RuleRepository ruleRepository;
	
	@Override
	public ResponseMessage getAllRule() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Rule> rules = ruleRepository.findAll();
			responseMessage.setData(rules);
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
		try {
			rule.setCreatedBy("toandv");
			rule.setCreatedOn(LocalDateTime.now());
			ruleRepository.save(rule);
			responseMessage.setData(Arrays.asList(rule));
			responseMessage.setMessage(Constant.MSG_020);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
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

package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Rule;

public interface RuleService {

	ResponseMessage getAllRule(int pageNo, int pageSize, String sortBy);
	ResponseMessage addRule(Rule rule);
	ResponseMessage editRule(int ruleId, Rule rule);
	ResponseMessage deleteRule(int ruleId);
}

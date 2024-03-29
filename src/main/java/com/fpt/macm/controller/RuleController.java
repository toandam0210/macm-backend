package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.entity.Rule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.RuleService;

@RestController
@RequestMapping("/api/rule")	
public class RuleController {

	@Autowired
	RuleService ruleService;
	
	@GetMapping("/getallrule")
	ResponseEntity<ResponseMessage> getAllRule(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy){
		return new ResponseEntity<ResponseMessage>(ruleService.getAllRule(pageNo, pageSize, sortBy), HttpStatus.OK);
	}
	
	@PostMapping("/vicehead/addrule")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> addRule(@RequestBody Rule rule){
		return new ResponseEntity<ResponseMessage>(ruleService.addRule(rule), HttpStatus.OK);
	}
	
	@PutMapping("/vicehead/editrulebyid/{ruleId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> editRuleById(@PathVariable(name = "ruleId") int ruleId, @RequestBody Rule rule){
		return new ResponseEntity<ResponseMessage>(ruleService.editRule(ruleId, rule), HttpStatus.OK);
	}
	
	@DeleteMapping("/vicehead/deleterulebyid/{ruleId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> deleteRuleById(@PathVariable(name = "ruleId") int ruleId){
		return new ResponseEntity<ResponseMessage>(ruleService.deleteRule(ruleId), HttpStatus.OK);
	}
}

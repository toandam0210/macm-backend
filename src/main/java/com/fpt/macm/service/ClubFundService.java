package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface ClubFundService {

	ResponseMessage getClubFund();
	ResponseMessage depositToClubFund(String studentId, double amount, String note);
	ResponseMessage withdrawFromClubFund(String studentId, double amount, String note);
	ResponseMessage getClubFundReport();
	
}

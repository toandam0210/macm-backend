package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface ClubFundService {

	ResponseMessage getClubFund();
	ResponseMessage depositToClubFund(double amount, String note);
	ResponseMessage withdrawFromClubFund(double amount, String note);
	ResponseMessage getClubFundReport();
	
}

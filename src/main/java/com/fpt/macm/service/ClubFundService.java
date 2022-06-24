package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface ClubFundService {

	ResponseMessage getClubFund();
	ResponseMessage depositToClubFund(double amount);
	ResponseMessage withdrawFromClubFund(double amount);
}

package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.ClubFundReport;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundReportRepository;
import com.fpt.macm.repository.ClubFundRepository;

@Service
public class ClubFundServiceImpl implements ClubFundService {

	@Autowired
	ClubFundRepository clubFundRepository;

	@Autowired
	ClubFundReportRepository clubFundReportRepository;

	@Override
	public ResponseMessage getClubFund() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ClubFund> clubFundOp = clubFundRepository.findById(1);
			ClubFund clubFund = clubFundOp.get();
			responseMessage.setData(Arrays.asList(clubFund));
			responseMessage.setMessage(Constant.MSG_090);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage depositToClubFund(double amount, String note) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if (!note.trim().equals("")) {
				Optional<ClubFund> clubFundOp = clubFundRepository.findById(1);
				ClubFund clubFund = clubFundOp.get();
				clubFund.setFundAmount(clubFund.getFundAmount() + amount);

				ClubFundReport clubFundReport = new ClubFundReport();
				clubFundReport.setFundChange(amount);
				clubFundReport.setFundBalance(clubFund.getFundAmount());
				clubFundReport.setNote(note);
				clubFundReport.setCreatedBy("toandv");
				clubFundReport.setCreatedOn(LocalDateTime.now());
				clubFundReportRepository.save(clubFundReport);

				clubFundRepository.save(clubFund);
				responseMessage.setData(Arrays.asList(clubFund));
				responseMessage.setMessage(Constant.MSG_091);
			} else {
				responseMessage.setMessage(Constant.MSG_094);
			}

		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage withdrawFromClubFund(double amount, String note) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if (!note.trim().equals("")) {
				Optional<ClubFund> clubFundOp = clubFundRepository.findById(1);
				ClubFund clubFund = clubFundOp.get();
				if (clubFund.getFundAmount() >= amount) {
					clubFund.setFundAmount(clubFund.getFundAmount() - amount);

					ClubFundReport clubFundReport = new ClubFundReport();
					clubFundReport.setFundChange(-amount);
					clubFundReport.setFundBalance(clubFund.getFundAmount());
					clubFundReport.setNote(note);
					clubFundReport.setCreatedBy("toandv");
					clubFundReport.setCreatedOn(LocalDateTime.now());
					clubFundReportRepository.save(clubFundReport);

					clubFundRepository.save(clubFund);
					responseMessage.setData(Arrays.asList(clubFund));
					responseMessage.setMessage(Constant.MSG_092);
				} else {
					responseMessage.setMessage(Constant.MSG_096);
				}
			} else {
				responseMessage.setMessage(Constant.MSG_094);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getClubFundReport() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<ClubFundReport> clubFundReports = clubFundReportRepository.findAll(Sort.by("id").descending());
			if (clubFundReports.size() > 0) {
				responseMessage.setData(clubFundReports);
				responseMessage.setMessage(Constant.MSG_095);
			} else {
				responseMessage.setMessage(Constant.MSG_086);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}

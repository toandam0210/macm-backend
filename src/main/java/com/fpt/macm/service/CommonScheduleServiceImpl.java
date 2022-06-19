package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.CommonSchedule;
import com.fpt.macm.repository.CommonScheduleRepository;

@Service
public class CommonScheduleServiceImpl implements CommonScheduleService{

	@Autowired
	CommonScheduleRepository commonScheduleRepository;
	
	@Override
	public CommonSchedule getCommonSessionByDate(LocalDate date) {
		// TODO Auto-generated method stub
		try {
			Optional<CommonSchedule> getSessionOp = commonScheduleRepository.findByDate(date);
			if(getSessionOp != null) {
				return getSessionOp.get();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}

package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Semester;
import com.fpt.macm.repository.SemesterRepository;

@Service
public class SemesterServiceImpl implements SemesterService {

	@Autowired
	SemesterRepository semesterRepository;

	@Override
	public ResponseMessage getCurrentSemester() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Semester> semesters = semesterRepository.findAll();
			if (semesters.size() > 0) {
				for (Semester semester : semesters) {
					if (LocalDate.now().isAfter(semester.getStartDate().minusDays(1))
							&& LocalDate.now().isBefore(semester.getEndDate().plusDays(1))) {
						responseMessage.setData(Arrays.asList(semester));
						responseMessage.setMessage("Lấy semester hiện tại thành công");
					}
				}
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getTop3Semesters() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Semester> semesters = semesterRepository.findTop3Semester();
			responseMessage.setData(semesters);
			responseMessage.setMessage("Lấy kì thành công");
			responseMessage.setTotalResult(semesters.size());
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}

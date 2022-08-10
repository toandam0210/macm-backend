package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.response.ResponseMessage;
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
			if(semesters.size() > 0) {
			responseMessage.setData(semesters);
			responseMessage.setMessage("Lấy kì thành công");
			responseMessage.setTotalResult(semesters.size());
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListMonthsBySemester(String semester) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if(semester == "") {
				semester = semesterRepository.findTop3Semester().get(0).getName();
			}
			List<Integer> listMonth = new ArrayList<Integer>();
			if(semester.contains("Spring")) {
				Integer [] list = {1, 2, 3, 4};
				listMonth = Arrays.asList(list);
			}
			else if(semester.contains("Summer")) {
				Integer [] list = {5, 6, 7, 8};
				listMonth = Arrays.asList(list);
			}
			else if(semester.contains("Fall")){
				Integer [] list = {9, 10, 11, 12};
				listMonth = Arrays.asList(list);
			}
			responseMessage.setData(listMonth);
			responseMessage.setMessage("Lấy danh sách tháng thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}

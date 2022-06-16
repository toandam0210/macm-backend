package com.fpt.macm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Constant;
import com.fpt.macm.model.MemberEvent;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.MemberEventRepository;

@Service
public class MemberEventServiceImpl implements MemberEventService{

	@Autowired
	MemberEventRepository memberEventRepository;
	
	@Override
	public ResponseMessage getAllMemberOfEvent(int eventId, int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<MemberEvent> pageResponse = memberEventRepository.findAllMemberEventByEventId(eventId, paging);
			List<MemberEvent> memberEvents = new ArrayList<MemberEvent>();
			if (pageResponse != null && pageResponse.hasContent()) {
				memberEvents = pageResponse.getContent();
			}
			responseMessage.setData(memberEvents);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
			responseMessage.setMessage(Constant.MSG_052);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}

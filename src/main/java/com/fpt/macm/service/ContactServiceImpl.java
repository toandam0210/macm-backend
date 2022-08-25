package com.fpt.macm.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.entity.Contact;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	ContactRepository contactRepository;
	
	@Override
	public ResponseMessage getAllContact() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Contact> contacts = contactRepository.findAll();
			if (contacts.size() > 0) {
				responseMessage.setData(contacts);
				responseMessage.setMessage(Constant.MSG_008);
			} else {
				responseMessage.setMessage("Chưa có thông tin liên hệ");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateContact(Contact newContact) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Contact> contactOp = contactRepository.findById(1);
			Contact contact = new Contact();
			if (contactOp.isPresent()) {
				contact = contactOp.get();
			}
			
			contact.setClubMail(newContact.getClubMail());
			contact.setClubName(newContact.getClubName());
			contact.setClubPhoneNumber(newContact.getClubPhoneNumber());
			contact.setFanpageUrl(newContact.getFanpageUrl());
			contact.setFoundingDate(newContact.getFoundingDate());
			contact.setImage(newContact.getImage());
			
			contactRepository.save(contact);
			responseMessage.setData(Arrays.asList(contact));
			responseMessage.setMessage(Constant.MSG_010);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}

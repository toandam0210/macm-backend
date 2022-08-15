package com.fpt.macm.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.entity.Contact;
import com.fpt.macm.model.entity.SocialNetwork;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ContactRepository;
import com.fpt.macm.repository.SocialNetworkRepository;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	SocialNetworkRepository socialNetworkRepository;
	
	@Override
	public ResponseMessage getAllContact() {
		// TODO Auto-generated method stub
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
	public ResponseMessage getAllSocialNetwork() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<SocialNetwork> socialNetworks = socialNetworkRepository.findAll();
			if (socialNetworks.size() > 0) {
				responseMessage.setData(socialNetworks);
				responseMessage.setMessage(Constant.MSG_009);
			} else {
				responseMessage.setMessage("Chưa có thông tin mạng xã hội");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateContact(Contact contact) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Contact oldContacts = contactRepository.findAll().get(0);
			
			oldContacts.setClubMail(contact.getClubMail());
			oldContacts.setClubName(contact.getClubName());
			oldContacts.setClubPhoneNumber(contact.getClubPhoneNumber());
			
			contactRepository.save(oldContacts);
			responseMessage.setData(Arrays.asList(oldContacts));
			responseMessage.setMessage(Constant.MSG_010);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}

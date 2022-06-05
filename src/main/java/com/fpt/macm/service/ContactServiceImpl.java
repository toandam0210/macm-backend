package com.fpt.macm.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Contact;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.SocialNetwork;
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
			responseMessage.setData(contacts);
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
			responseMessage.setData(socialNetworks);
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
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	public ResponseMessage updateSocialNetwork(List<SocialNetwork> socialNetworks) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			socialNetworkRepository.deleteAll();
			socialNetworkRepository.saveAll(socialNetworks);
			responseMessage.setData(socialNetworks);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}

package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.Contact;
import com.fpt.macm.model.entity.SocialNetwork;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ContactRepository;
import com.fpt.macm.repository.SocialNetworkRepository;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

	@InjectMocks
	ContactService contactService = new ContactServiceImpl();
	
	@Mock
	ContactRepository contactRepository;
	
	@Mock
	SocialNetworkRepository socialNetworkRepository;
	
	private Contact contact() {
		Contact contact = new Contact();
		contact.setId(1);
		contact.setClubName("FNC");
		contact.setClubMail("fnc@fpt.edu.vn");
		contact.setClubPhoneNumber("0123456789");
		return contact;
	}
	
	private SocialNetwork socialNetwork() {
		SocialNetwork socialNetwork = new SocialNetwork();
		socialNetwork.setId(1);
		socialNetwork.setName("Facebook");
		socialNetwork.setUrl("fb.com/fnc");
		return socialNetwork;
	}
	
	@Test
	public void getAllContactCaseSuccess() {
		when(contactRepository.findAll()).thenReturn(Arrays.asList(contact()));
		
		ResponseMessage responseMessage = contactService.getAllContact();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getAllSocialNetworkCaseSuccess() {
		when(socialNetworkRepository.findAll()).thenReturn(Arrays.asList(socialNetwork()));
		
		ResponseMessage responseMessage = contactService.getAllSocialNetwork();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void updateContactCaseSuccess() {
		when(contactRepository.findAll()).thenReturn(Arrays.asList(contact()));
		
		ResponseMessage responseMessage = contactService.updateContact(contact());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void updateContactCaseException() {
		when(contactRepository.findAll()).thenReturn(null);
		
		ResponseMessage responseMessage = contactService.updateContact(contact());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
}

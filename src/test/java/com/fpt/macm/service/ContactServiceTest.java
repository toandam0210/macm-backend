package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.Contact;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ContactRepository;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

	@InjectMocks
	ContactService contactService = new ContactServiceImpl();
	
	@Mock
	ContactRepository contactRepository;
	
	private Contact contact() {
		Contact contact = new Contact();
		contact.setId(1);
		contact.setClubName("FNC");
		contact.setClubMail("fnc@fpt.edu.vn");
		contact.setClubPhoneNumber("0123456789");
		return contact;
	}

	
	@Test
	public void getAllContactCaseSuccess() {
		when(contactRepository.findAll()).thenReturn(Arrays.asList(contact()));
		
		ResponseMessage responseMessage = contactService.getAllContact();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getAllContactCaseEmpty() {
		when(contactRepository.findAll()).thenReturn(new ArrayList<Contact>());
		
		ResponseMessage responseMessage = contactService.getAllContact();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getAllContactCaseException() {
		when(contactRepository.findAll()).thenReturn(null);
		
		ResponseMessage responseMessage = contactService.getAllContact();
		assertEquals(responseMessage.getData().size(), 0);
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

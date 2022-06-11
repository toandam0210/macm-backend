package com.fpt.macm.service;

import com.fpt.macm.model.Contact;
import com.fpt.macm.model.ResponseMessage;

public interface ContactService {

	ResponseMessage getAllContact();
	ResponseMessage getAllSocialNetwork();
	ResponseMessage updateContact(Contact contact);
}

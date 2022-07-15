package com.fpt.macm.service;

import com.fpt.macm.model.entity.Contact;
import com.fpt.macm.model.response.ResponseMessage;

public interface ContactService {

	ResponseMessage getAllContact();
	ResponseMessage getAllSocialNetwork();
	ResponseMessage updateContact(Contact contact);
}

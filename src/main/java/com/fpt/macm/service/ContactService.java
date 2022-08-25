package com.fpt.macm.service;

import com.fpt.macm.model.entity.Contact;
import com.fpt.macm.model.response.ResponseMessage;

public interface ContactService {

	ResponseMessage getAllContact();
	ResponseMessage updateContact(Contact contact);
}

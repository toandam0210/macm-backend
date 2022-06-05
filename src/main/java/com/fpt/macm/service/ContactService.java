package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.Contact;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.SocialNetwork;

public interface ContactService {

	ResponseMessage getAllContact();
	ResponseMessage getAllSocialNetwork();
	ResponseMessage updateContact(Contact contact);
	ResponseMessage updateSocialNetwork(List<SocialNetwork> socialNetworks);
}

package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>{

}

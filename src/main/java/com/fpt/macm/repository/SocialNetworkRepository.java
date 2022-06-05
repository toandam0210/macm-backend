package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.SocialNetwork;

@Repository
public interface SocialNetworkRepository extends JpaRepository<SocialNetwork, Integer>{

}

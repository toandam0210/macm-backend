package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.ExhibitionTypeRegistration;

@Repository
public interface ExhibitionTypeRegistrationRepository extends JpaRepository<ExhibitionTypeRegistration, Integer>{

	List<ExhibitionTypeRegistration> findByExhibitionTypeId(int exhibitionTypeId);
	
	Optional<ExhibitionTypeRegistration> findByExhibitionTypeIdAndExhibitionTeamRegistrationId(int exhibitionTypeId, int exhibitionTeamRegistrationId);
}

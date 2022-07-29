package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	Optional<User> findByStudentId(String studentId);
	
	@Query(value = "select * from \"user\" where role_id < 10 and role_id > 2", nativeQuery = true)
	Page<User> findAdminForViceHeadClubByRoleId(Pageable pageable);
	
	@Query(value = "select * from \"user\" where role_id < 10 and role_id >0", nativeQuery = true)
	Page<User> findAdminForHeadClubByRoleId(Pageable pageable);
	
	@Query(value = "select * from \"user\" where role_id > 9", nativeQuery = true)
	Page<User> findMemberAndCollaboratorByRoleId(Pageable pageable);
	
	@Query(value = "select * from \"user\" where student_id like CONCAT('%', ?1, '%') or \"name\" like CONCAT('%', ?1, '%')",nativeQuery = true)
	Page<User> searchByStudentIdOrName(String searchInput, Pageable pageable);
	
	Optional<User> findByEmail(String email);
	
	@Query(value = "select * from \"user\" where role_id in (13,14,15)", nativeQuery = true)
	List<User> findCollaborator();
	
	@Query(value = "select * from \"user\" where role_id in (10,11,12)", nativeQuery = true)
	Page<User> findMember(Pageable pageable);
	
	@Query(value = "select * from \"user\" where role_id in (10,11,12)", nativeQuery = true)
	List<User> findMemberWithoutPaging();
		
	@Query(value = "select * from \"user\" where role_id < 13 ", nativeQuery = true)
	List<User> findMembersAndAdmin();
	
	@Query(value = "select * from \"user\" where role_id < 10", nativeQuery = true)
	List<User> findAllAdmin();
	
	@Query(value = "select * from \"user\" where is_active and role_id < 13", nativeQuery = true)
	List<User> findMembersActive();
	
	@Query(value = "select * from \"user\" where student_id like ?1",nativeQuery = true)
	User getByStudentId(String studentId);
	
	@Query(value = "select * from \"user\" where is_active and role_id > 9", nativeQuery = true)
	List<User> findActiveMembersAndCollaborators();
	
	@Query(value = "select * from \"user\" where is_active", nativeQuery = true)
	List<User> findAllActiveUser();
	
}

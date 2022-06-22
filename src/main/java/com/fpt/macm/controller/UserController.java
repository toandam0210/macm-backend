package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.dto.UserDto;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.UserService;

@RestController
@RequestMapping("/api/admin/hr")
public class UserController {

	@Autowired
	UserService userSerivce;

	@GetMapping("/getbystudentid/{studentId}")
	ResponseEntity<ResponseMessage> getUserByStudentId(@PathVariable(name = "studentId") String studentId) {
		return new ResponseEntity<ResponseMessage>(userSerivce.getUserByStudentId(studentId), HttpStatus.OK);
	}

	@GetMapping("/viceheadclub/getalladmin")
	ResponseEntity<ResponseMessage> getAllAdminForViceHeadClub(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(userSerivce.getAllAdminForViceHeadClub(pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}

	@GetMapping("/headclub/getalladmin")
	ResponseEntity<ResponseMessage> getAllAdminForHeadClub(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(userSerivce.getAllAdminForHeadClub(pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}

	@PutMapping("/updateuser/{studentId}")
	ResponseEntity<ResponseMessage> updateUserByStudentId(@PathVariable(name = "studentId") String studentId,
			@RequestBody UserDto userDto) {
		return new ResponseEntity<ResponseMessage>(userSerivce.updateUser(studentId, userDto), HttpStatus.OK);
	}

	@PutMapping("/deleteadmin/{studentId}")
	ResponseEntity<ResponseMessage> deleteAdminByStudentId(@PathVariable(name = "studentId") String studentId, @RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(userSerivce.deleteAdmin(studentId,semester), HttpStatus.OK);
	}

	@GetMapping("/getallmemberandcollaborator")
	ResponseEntity<ResponseMessage> getAllMemberAndCollaborator(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(userSerivce.getAllMemberAndCollaborator(pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}

	@PostMapping("/adduser")
	ResponseEntity<ResponseMessage> addNewMember(@RequestBody UserDto user) {
		return new ResponseEntity<ResponseMessage>(userSerivce.addAnMemberOrCollaborator(user), HttpStatus.OK);
	}

	@PutMapping("/updatestatus")
	ResponseEntity<ResponseMessage> updateStatusForUser(@RequestParam String studentId, @RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(userSerivce.updateStatusForUser(studentId,semester), HttpStatus.OK);
	}

	@GetMapping("/users/search")
	ResponseEntity<ResponseMessage> searchUserByStudentIdOrName(@RequestParam(name = "inputSearch") String inputSearch,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(
				userSerivce.searchUserByStudentIdOrName(inputSearch, pageNo, pageSize, sortBy), HttpStatus.OK);
	}

	@PostMapping("/users/import")
	ResponseEntity<ResponseMessage> addListUserFromExcel(@RequestParam("file") MultipartFile file) throws Exception {
		return new ResponseEntity<ResponseMessage>(userSerivce.addUsersFromExcel(file), HttpStatus.OK);
	}

	@GetMapping("/users/export")
	public ResponseEntity<Resource> exportListUserToExcel() {
		String filename = "users.xlsx";
		InputStreamResource file = new InputStreamResource(userSerivce.exportUsersToExcel());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

	@GetMapping("/viceheadclub/getallmembers")
	ResponseEntity<ResponseMessage> getAllMembers(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(userSerivce.findAllMember(pageNo, pageSize, sortBy), HttpStatus.OK);
	}
	
	@GetMapping("/viceheadclub/getallusers")
	ResponseEntity<ResponseMessage> getAllUsers() {
		return new ResponseEntity<ResponseMessage>(userSerivce.getAllUser(), HttpStatus.OK);
	}
	
	@GetMapping("/viceheadclub/getmembers/semester")
	ResponseEntity<ResponseMessage> getMembersBySemester(@RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(userSerivce.getMembersBySemester(semester), HttpStatus.OK);
	}
	
	@GetMapping("/viceheadclub/getadmins/semester")
	ResponseEntity<ResponseMessage> getAdminsBySemester(@RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(userSerivce.getAdminBySemester(semester), HttpStatus.OK);
	}
	
	@GetMapping("/viceheadclub/member/search")
	ResponseEntity<ResponseMessage> searchByMutipleField(@RequestBody List<UserDto> userDtos,@RequestParam(required = false) String name,@RequestParam(required = false) String studentId,@RequestParam(required = false) String email,@RequestParam(required = false) String gender,
			@RequestParam(required = false) Integer generation,@RequestParam(required = false) Integer roleId,@RequestParam(required = false) String isActive,@RequestParam(required = false) Integer month,@RequestParam(required = false) Integer year) {
		return new ResponseEntity<ResponseMessage>(userSerivce.searchByMultipleField(userDtos, name, studentId, email, gender, generation, roleId, isActive, month, year), HttpStatus.OK);
	}

}

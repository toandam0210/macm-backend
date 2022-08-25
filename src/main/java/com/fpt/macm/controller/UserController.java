package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.model.dto.InforInQrCode;
import com.fpt.macm.model.dto.UserDto;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.UserService;

@RestController
@RequestMapping("/api/admin/hr")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/getbystudentid/{studentId}")
	ResponseEntity<ResponseMessage> getUserByStudentId(@PathVariable(name = "studentId") String studentId) {
		return new ResponseEntity<ResponseMessage>(userService.getUserByStudentId(studentId), HttpStatus.OK);
	}

	@GetMapping("/viceheadclub/getalladmin")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getAllAdminForViceHeadClub(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(userService.getAllAdminForViceHeadClub(pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}

	@GetMapping("/headclub/getalladmin")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getAllAdminForHeadClub(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(userService.getAllAdminForHeadClub(pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}

	@PutMapping("/updateuser/{studentId}")
	ResponseEntity<ResponseMessage> updateUserByStudentId(@PathVariable(name = "studentId") String studentId,
			@RequestBody UserDto userDto) {
		return new ResponseEntity<ResponseMessage>(userService.updateUser(studentId, userDto), HttpStatus.OK);
	}

	@PutMapping("/deleteadmin/{studentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub')")
	ResponseEntity<ResponseMessage> deleteAdminByStudentId(@PathVariable(name = "studentId") String studentId, @RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(userService.deleteAdmin(studentId,semester), HttpStatus.OK);
	}

	@GetMapping("/getallmemberandcollaborator")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getAllMemberAndCollaborator(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(userService.getAllMemberAndCollaborator(pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}

	@PostMapping("/adduser")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub')")
	ResponseEntity<ResponseMessage> addNewMember(@RequestBody UserDto user) {
		return new ResponseEntity<ResponseMessage>(userService.addAnMemberOrCollaborator(user), HttpStatus.OK);
	}

	@PutMapping("/updatestatus")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub')")
	ResponseEntity<ResponseMessage> updateStatusForUser(@RequestParam String studentId, @RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(userService.updateStatusForUser(studentId,semester), HttpStatus.OK);
	}

	@GetMapping("/users/search")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> searchUserByStudentIdOrName(@RequestParam(name = "inputSearch") String inputSearch,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(
				userService.searchUserByStudentIdOrName(inputSearch, pageNo, pageSize, sortBy), HttpStatus.OK);
	}

	@PostMapping("/users/import")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub')")
	ResponseEntity<ResponseMessage> addListUserFromExcel(@RequestParam("file") MultipartFile file) throws Exception {
		return new ResponseEntity<ResponseMessage>(userService.addUsersFromExcel(file), HttpStatus.OK);
	}

	@PostMapping("/users/export")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub')")
	public ResponseEntity<Resource> exportListUserToExcel(@RequestBody List<UserDto> users) {
		String filename = "users.xlsx";
		InputStreamResource file = new InputStreamResource(userService.exportUsersToExcel(users));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	
	@PostMapping("/users/exporterror")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub')")
	public ResponseEntity<Resource> exportListUserToExcelWithError(@RequestBody List<UserDto> users) {
		String filename = "users.xlsx";
		InputStreamResource file = new InputStreamResource(userService.exportUsersToExcelWithError(users));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

	@GetMapping("/viceheadclub/getallmembers")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getAllMembers(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(userService.findAllMember(pageNo, pageSize, sortBy), HttpStatus.OK);
	}
	
	@GetMapping("/viceheadclub/getallusers")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getAllUsers() {
		return new ResponseEntity<ResponseMessage>(userService.getAllUser(), HttpStatus.OK);
	}
	
	@GetMapping("/viceheadclub/getmembers/semester")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getMembersBySemester(@RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(userService.getMembersBySemester(semester), HttpStatus.OK);
	}
	
	@GetMapping("/viceheadclub/getadmins/semester")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getAdminsBySemester(@RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(userService.getAdminBySemester(semester), HttpStatus.OK);
	}
	
	@PostMapping("/viceheadclub/member/search")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> searchByMutipleField(@RequestBody List<UserDto> userDtos,@RequestParam(required = false,defaultValue = "") String name,@RequestParam(required = false,defaultValue = "") String studentId,@RequestParam(required = false,defaultValue = "") String email,@RequestParam(required = false,defaultValue = "") String gender,
			@RequestParam(required = false) Integer generation,@RequestParam(required = false) Integer roleId,@RequestParam(required = false,defaultValue = "") String isActive, @RequestParam(required = false) List<Integer> months) {
		return new ResponseEntity<ResponseMessage>(userService.searchByMultipleField(userDtos, name, studentId, email, gender, generation, roleId, isActive, months), HttpStatus.OK);
	}
	
	@PostMapping("/member/qrcode/create")
	ResponseEntity<ResponseMessage> generateQrCode(@RequestBody InforInQrCode inforInQrCode) {
		return new ResponseEntity<ResponseMessage>(userService.generateQrCode(inforInQrCode), HttpStatus.OK);
	}
	
	@GetMapping("/getallactivememberandcollaborator")
	ResponseEntity<ResponseMessage> getAllActiveMemberAndCollaborator() {
		return new ResponseEntity<ResponseMessage>(userService.getAllActiveMemberAndCollaborator(), HttpStatus.OK);
	}
	
	@GetMapping("/getallattendancestatusofuser/{studentId}")
	ResponseEntity<ResponseMessage> getAllAttendanceStatusOfUser(@PathVariable(name = "studentId") String studentId){
		return new ResponseEntity<ResponseMessage>(userService.getAllUserAttendanceStatus(studentId), HttpStatus.OK);
	}
	
	@GetMapping("/getallgen")
	ResponseEntity<ResponseMessage> getAllGen() {
		return new ResponseEntity<ResponseMessage>(userService.getAllGen(), HttpStatus.OK);
	}
	
	@PutMapping("/updatestatusbymember")
	ResponseEntity<ResponseMessage> updateStatusByUser(@RequestParam String studentId, @RequestParam String semester, @RequestParam int status) {
		return new ResponseEntity<ResponseMessage>(userService.updateStatusUserSide(studentId,semester,status), HttpStatus.OK);
	}
}

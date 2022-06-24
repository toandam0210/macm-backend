package com.fpt.macm.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Sort;

import com.fpt.macm.dto.RoleEventDto;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.Role;
import com.fpt.macm.model.RoleEvent;

public class Utils {
	public static final String SORT_BY_NAME_ASC = "SORT_BY_NAME_ASC";
	public static final String SORT_BY_GENDER_ASC = "SORT_BY_GENDER_ASC";
	public static final String SORT_BY_STUDENTID_ASC = "SORT_BY_STUDENTID_ASC";
	public static final String SORT_BY_ROLE_ASC = "SORT_BY_ROLE_ASC";
	public static final String SORT_BY_STATUS_ASC = "SORT_BY_STATUS_ASC";
	public static final String SORT_BY_NAME_DESC = "SORT_BY_NAME_DESC";
	public static final String SORT_BY_GENDER_DESC = "SORT_BY_GENDER_DESC";
	public static final String SORT_BY_STUDENTID_DESC = "SORT_BY_STUDENTID_DESC";
	public static final String SORT_BY_ROLE_DESC = "SORT_BY_ROLE_DESC";
	public static final String SORT_BY_STATUS_DESC = "SORT_BY_STATUS_DESC";

	public static final String ID_FIELD = "id";
	public static final String NAME_FIELD = "name";
	public static final String GENDER_FIELD = "gender";
	public static final String STUDENTID_FIELD = "student_id";
	public static final String ROLE_FIELD = "role";
	public static final String STATUS_FIELD = "status";

	public static Sort sortUser(String sortBy) {
		switch (sortBy) {
		case SORT_BY_NAME_ASC:
			return Sort.by(NAME_FIELD).ascending();
		case SORT_BY_NAME_DESC:
			return Sort.by(NAME_FIELD).descending();
		case SORT_BY_GENDER_ASC:
			return Sort.by(GENDER_FIELD).ascending();
		case SORT_BY_GENDER_DESC:
			return Sort.by(GENDER_FIELD).descending();
		case SORT_BY_STUDENTID_ASC:
			return Sort.by(STUDENTID_FIELD).ascending();
		case SORT_BY_STUDENTID_DESC:
			return Sort.by(STUDENTID_FIELD).descending();
		case SORT_BY_ROLE_ASC:
			return Sort.by(ROLE_FIELD).ascending();
		case SORT_BY_ROLE_DESC:
			return Sort.by(ROLE_FIELD).descending();
		case SORT_BY_STATUS_ASC:
			return Sort.by(STATUS_FIELD).ascending();
		case SORT_BY_STATUS_DESC:
			return Sort.by(STATUS_FIELD).descending();

		default:
			return Sort.by(ID_FIELD).ascending();
		}
	}

	public static LocalDate ConvertStringToLocalDate(String input) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(input, formatter);
	}

	public static void convertNameOfRole(Role role) {
		switch (role.getName()) {
		case Constant.ROLE_HEAD_CLUB:
			role.setName(Constant.ROLE_HEAD_CLUB_VN);
			break;
		case Constant.ROLE_VICE_HEAD_CLUB:
			role.setName(Constant.ROLE_VICE_HEAD_CLUB_VN);
			break;
		case Constant.ROLE_TREASURER:
			role.setName(Constant.ROLE_TREASURER_VN);
			break;
		case Constant.ROLE_HEAD_CULTURE:
			role.setName(Constant.ROLE_HEAD_CULTURE_VN);
			break;
		case Constant.ROLE_VICE_HEAD_CULTURE:
			role.setName(Constant.ROLE_VICE_HEAD_CULTURE_VN);
			break;
		case Constant.ROLE_HEAD_COMMUNICATION:
			role.setName(Constant.ROLE_HEAD_COMMUNICATION_VN);
			break;
		case Constant.ROLE_VICE_HEAD_COMMUNICATION:
			role.setName(Constant.ROLE_VICE_HEAD_COMMUNICATION_VN);
			break;
		case Constant.ROLE_HEAD_TECHNIQUE:
			role.setName(Constant.ROLE_HEAD_TECHNIQUE_VN);
			break;
		case Constant.ROLE_VICE_HEAD_TECHNIQUE:
			role.setName(Constant.ROLE_VICE_HEAD_TECHNIQUE_VN);
			break;
		case Constant.ROLE_MEMBER_COMMUNICATION:
			role.setName(Constant.ROLE_MEMBER_COMMUNICATION_VN);
			break;
		case Constant.ROLE_MEMBER_CULTURE:
			role.setName(Constant.ROLE_MEMBER_CULTURE_VN);
			break;
		case Constant.ROLE_MEMBER_TECHNIQUE:
			role.setName(Constant.ROLE_MEMBER_TECHNIQUE_VN);
			break;
		case Constant.ROLE_COLLABORATOR_COMMUNICATION:
			role.setName(Constant.ROLE_COLLABORATOR_COMMUNICATION_VN);
			break;
		case Constant.ROLE_COLLABORATOR_CULTURE:
			role.setName(Constant.ROLE_COLLABORATOR_CULTURE_VN);
			break;
		case Constant.ROLE_COLLABORATOR_TECHNIQUE:
			role.setName(Constant.ROLE_COLLABORATOR_TECHNIQUE_VN);
			break;

		default:
			role.setName(Constant.ROLE_MEMBER_TECHNIQUE_VN);
			break;
		}
	}

	public static void convertRoleFromExcelToDb(String roleInExcel, Role role) {
		switch (roleInExcel) {
		case Constant.ROLE_HEAD_CLUB_VN:
			role.setId(1);
			break;
		case Constant.ROLE_VICE_HEAD_CLUB_VN:
			role.setId(2);
			break;
		case Constant.ROLE_TREASURER_VN:
			role.setId(3);
			break;
		case Constant.ROLE_HEAD_CULTURE_VN:
			role.setId(4);
			break;
		case Constant.ROLE_VICE_HEAD_CULTURE_VN:
			role.setId(5);
			break;
		case Constant.ROLE_HEAD_COMMUNICATION_VN:
			role.setId(6);
			break;
		case Constant.ROLE_VICE_HEAD_COMMUNICATION_VN:
			role.setId(7);
			break;
		case Constant.ROLE_HEAD_TECHNIQUE_VN:
			role.setId(8);
			break;
		case Constant.ROLE_VICE_HEAD_TECHNIQUE_VN:
			role.setId(9);
			break;
		case Constant.ROLE_MEMBER_COMMUNICATION_VN:
			role.setId(10);
			break;
		case Constant.ROLE_MEMBER_CULTURE_VN:
			role.setId(11);
			break;
		case Constant.ROLE_MEMBER_TECHNIQUE_VN:
			role.setId(12);
			break;
		case Constant.ROLE_COLLABORATOR_COMMUNICATION_VN:
			role.setId(13);
			break;
		case Constant.ROLE_COLLABORATOR_CULTURE_VN:
			role.setId(14);
			break;
		case Constant.ROLE_COLLABORATOR_TECHNIQUE_VN:
			role.setId(15);
			break;

		default:
			role.setId(12);
			break;
		}
	}

	public static String convertRoleFromDbToExcel(Role role) {
		switch (role.getId()) {
		case 1:
			return Constant.ROLE_HEAD_CLUB_VN;
		case 2:
			return Constant.ROLE_VICE_HEAD_CLUB_VN;
		case 3:
			return Constant.ROLE_TREASURER_VN;
		case 4:
			return Constant.ROLE_HEAD_CULTURE_VN;
		case 5:
			return Constant.ROLE_VICE_HEAD_CULTURE_VN;
		case 6:
			return Constant.ROLE_HEAD_COMMUNICATION_VN;
		case 7:
			return Constant.ROLE_VICE_HEAD_COMMUNICATION_VN;
		case 8:
			return Constant.ROLE_HEAD_TECHNIQUE_VN;
		case 9:
			return Constant.ROLE_VICE_HEAD_TECHNIQUE_VN;
		case 10:
			return Constant.ROLE_MEMBER_COMMUNICATION_VN;
		case 11:
			return Constant.ROLE_MEMBER_CULTURE_VN;
		case 12:
			return Constant.ROLE_MEMBER_TECHNIQUE_VN;
		case 13:
			return Constant.ROLE_COLLABORATOR_COMMUNICATION_VN;
		case 14:
			return Constant.ROLE_COLLABORATOR_CULTURE_VN;
		case 15:
			return Constant.ROLE_COLLABORATOR_TECHNIQUE_VN;

		default:
			return Constant.ROLE_MEMBER_TECHNIQUE_VN;
		}
	}

	public static void convertNameOfEventRole(RoleEvent roleEvent, RoleEventDto roleEventDto) {
		switch (roleEvent.getName()) {
		case Constant.ROLE_EVENT_MEMBER:
			roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_VN);
			break;
		case Constant.ROLE_EVENT_MEMBER_COMMUNICATION:
			roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_COMMUNICATION_VN);
			break;
		case Constant.ROLE_EVENT_MEMBER_CULTURE:
			roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_CULTURE_VN);
			break;
		case Constant.ROLE_EVENT_MEMBER_LOGISTIC:
			roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_LOGISTIC_VN);
			break;
		default:
			roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_VN);
			break;
		}
	}
}

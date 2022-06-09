package com.fpt.macm.utils;

import org.springframework.data.domain.Sort;

import com.fpt.macm.dto.UserToCsvDto;
import com.fpt.macm.model.User;

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
	
	public static void convertUserRoleFromDbToCsv(User user, UserToCsvDto userToCsvDto) {
		switch (user.getRole().getName()) {
		case "ROLE_HeadClub":
			userToCsvDto.setRole("Chủ nhiệm");
			break;
		case "ROLE_ViceHeadClub":
			userToCsvDto.setRole("Phó chủ nhiệm");
			break;
		case "ROLE_Treasurer":
			userToCsvDto.setRole("Thủ quỹ");
			break;
		case "ROLE_HeadCulture":
			userToCsvDto.setRole("Trưởng ban văn hóa");
			break;
		case "ROLE_ViceHeadCulture":
			userToCsvDto.setRole("Phó ban văn hóa");
			break;
		case "ROLE_HeadCommunication":
			userToCsvDto.setRole("Trưởng ban truyền thông");
			break;
		case "ROLE_ViceHeadCommunication":
			userToCsvDto.setRole("Phó ban truyền thông");
			break;
		case "ROLE_HeadTechnique":
			userToCsvDto.setRole("Trưởng ban chuyên môn");
			break;
		case "ROLE_ViceHeadTechnique":
			userToCsvDto.setRole("Phó ban chuyên môn");
			break;
		case "ROLE_Member_Commnication":
			userToCsvDto.setRole("Thành viên ban truyền thông");
			break;
		case "ROLE_Member_Culture":
			userToCsvDto.setRole("Thành viên ban văn hóa");
			break;
		case "ROLE_Member_Technique":
			userToCsvDto.setRole("Thành viên ban chuyên môn");
			break;
		case "ROLE_Collaborator_Commnunication":
			userToCsvDto.setRole("CTV truyền thông");
			break;
		case "ROLE_Collaborator_Culture":
			userToCsvDto.setRole("CTV văn hóa");
			break;
		case "ROLE_Collaborator_Technique":
			userToCsvDto.setRole("CTV chuyên môn");
			break;

		default:
			userToCsvDto.setRole("Thành viên ban chuyên môn");
			break;
		}
	}
	
	public static UserToCsvDto convertUserToUserCsv(User user) {
		UserToCsvDto userToCsvDto = new UserToCsvDto();
		userToCsvDto.setStudentId(user.getStudentId());
		userToCsvDto.setName(user.getName());
		userToCsvDto.setDateOfBirth(user.getDateOfBirth());
		userToCsvDto.setPhone(user.getPhone());
		userToCsvDto.setEmail(user.getEmail());
		if (user.isGender()) {
			userToCsvDto.setGender("Nam");
		} else {
			userToCsvDto.setGender("Nữ");
		}
		userToCsvDto.setImage(user.getImage());
		if (user.isActive()) {
			userToCsvDto.setIsActive("Hoạt động");
		} else {
			userToCsvDto.setIsActive("Không hoạt động");
		}
		convertUserRoleFromDbToCsv(user, userToCsvDto);
		userToCsvDto.setCurrentAddress(user.getCurrentAddress());
		return userToCsvDto;
	}
}

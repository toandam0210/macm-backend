package com.fpt.macm.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Sort;

import com.fpt.macm.model.Role;

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
		case "ROLE_HeadClub":
			role.setName("Chủ nhiệm");
			break;
		case "ROLE_ViceHeadClub":
			role.setName("Phó chủ nhiệm");
			break;
		case "ROLE_Treasurer":
			role.setName("Thủ quỹ");
			break;
		case "ROLE_HeadCulture":
			role.setName("Trưởng ban văn hóa");
			break;
		case "ROLE_ViceHeadCulture":
			role.setName("Phó ban văn hóa");
			break;
		case "ROLE_HeadCommunication":
			role.setName("Trưởng ban truyền thông");
			break;
		case "ROLE_ViceHeadCommunication":
			role.setName("Phó ban truyền thông");
			break;
		case "ROLE_HeadTechnique":
			role.setName("Trưởng ban chuyên môn");
			break;
		case "ROLE_ViceHeadTechnique":
			role.setName("Phó ban chuyên môn");
			break;
		case "ROLE_MemberCommnication":
			role.setName("Thành viên ban truyền thông");
			break;
		case "ROLE_MemberCulture":
			role.setName("Thành viên ban văn hóa");
			break;
		case "ROLE_MemberTechnique":
			role.setName("Thành viên ban chuyên môn");
			break;
		case "ROLE_CollaboratorCommnunication":
			role.setName("CTV ban truyền thông");
			break;
		case "ROLE_CollaboratorCulture":
			role.setName("CTV ban văn hóa");
			break;
		case "ROLE_CollaboratorTechnique":
			role.setName("CTV ban chuyên môn");
			break;

		default:
			role.setName("Thành viên ban chuyên môn");
			break;
		}
	}
	public static void convertRoleFromExcelToDb(String roleInExcel, Role role) {
		switch (roleInExcel) {
		case "Chủ nhiệm":
			role.setId(1);
			break;
		case "Phó chủ nhiệm":
			role.setId(2);
			break;
		case "Thủ quỹ":
			role.setId(3);
			break;
		case "Trưởng ban văn hóa":
			role.setId(4);
			break;
		case "Phó ban văn hóa":
			role.setId(5);
			break;
		case "Trưởng ban truyền thông":
			role.setId(6);
			break;
		case "Phó ban truyền thông":
			role.setId(7);
			break;
		case "Trưởng ban chuyên môn":
			role.setId(8);
			break;
		case "Phó ban chuyên môn":
			role.setId(9);
			break;
		case "Thành viên ban truyền thông":
			role.setId(10);
			break;
		case "Thành viên ban văn hóa":
			role.setId(11);
			break;
		case "Thành viên ban chuyên môn":
			role.setId(12);
			break;
		case "CTV ban truyền thông":
			role.setId(13);
			break;
		case "CTV ban văn hóa":
			role.setId(14);
			break;
		case "CTV ban chuyên môn":
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
			return "Chủ nhiệm";
		case 2:
			return "Phó chủ nhiệm";
		case 3:
			return "Thủ quỹ";
		case 4:
			return "Trưởng ban văn hóa";
		case 5:
			return "Phó ban văn hóa";
		case 6:
			return "Trưởng ban truyền thông";
		case 7:
			return "Phó ban truyền thông";
		case 8:
			return "Trưởng ban chuyên môn";
		case 9:
			return "Phó ban chuyên môn";
		case 10:
			return "Thành viên ban truyền thông";
		case 11:
			return "Thành viên ban văn hóa";
		case 12:
			return "Thành viên ban chuyên môn";
		case 13:
			return "CTV ban truyền thông";
		case 14:
			return "CTV ban văn hóa";
		case 15:
			return "CTV ban chuyên môn";

		default:
			return "Thành viên ban chuyên môn";
		}
	}
}

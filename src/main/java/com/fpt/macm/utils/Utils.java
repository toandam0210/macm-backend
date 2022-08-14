package com.fpt.macm.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.entity.Role;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

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

	public static LocalDateTime ConvertStringToLocalDateTime(String input) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return LocalDateTime.parse(input, formatter);
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

//	public static void convertNameOfEventRole(RoleEvent roleEvent, RoleEventDto roleEventDto) {
//		switch (roleEvent.getName()) {
//		case Constant.ROLE_EVENT_MEMBER:
//			roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_VN);
//			break;
//		case Constant.ROLE_EVENT_MEMBER_COMMUNICATION:
//			roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_COMMUNICATION_VN);
//			break;
//		case Constant.ROLE_EVENT_MEMBER_CULTURE:
//			roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_CULTURE_VN);
//			break;
//		case Constant.ROLE_EVENT_MEMBER_LOGISTIC:
//			roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_LOGISTIC_VN);
//			break;
//		case Constant.ROLE_EVENT_MEMBER_TAKE_CARE:
//			roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_TAKE_CARE_VN);
//			break;
//		default:
//			roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_VN);
//			break;
//		}
//	}

	public static String generateQrCode(String data, int wid, int hei) {
		StringBuilder result = new StringBuilder();

		if (!data.isEmpty()) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				QRCodeWriter writer = new QRCodeWriter();
				BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, wid, hei);
				BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
				ImageIO.write(bufferedImage, "png", os);
				result.append("data:image/png;base64,");
				result.append(new String(Base64.getEncoder().encode(os.toByteArray())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	public static String prettyObject(Object object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String ConvertLocalDateTimeToString(LocalDateTime time) {
		return time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() 
				+ " ngày " + time.getDayOfMonth() + "/" + time.getMonthValue() + "/" + time.getYear();
	}
}

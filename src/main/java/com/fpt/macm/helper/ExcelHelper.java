package com.fpt.macm.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.model.dto.UserDto;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.utils.Utils;

public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "MSSV", "Tên", "Ngày sinh", "SĐT", "Email", "Giới tính", "Trạng thái hoạt động",
			"Vai trò", "Địa chỉ", "Generation"};
	static String[] HEADERsForErrors = { "MSSV", "Tên", "Ngày sinh", "SĐT", "Email", "Giới tính", "Trạng thái hoạt động",
			"Vai trò", "Địa chỉ", "Generation", "Lỗi"};
	static String SHEET = "Users";
	
	public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }

	public static ByteArrayInputStream usersToExcel(List<UserDto> users) {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);
			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}
			int rowIdx = 1;
			for (UserDto user : users) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(user.getStudentId());
				row.createCell(1).setCellValue(user.getName());
				row.createCell(2).setCellValue(user.getDateOfBirth().toString());
				row.createCell(3).setCellValue(user.getPhone());
				row.createCell(4).setCellValue(user.getEmail());
				if(user.isGender()) {
					row.createCell(5).setCellValue("Nam");
				}else {
					row.createCell(5).setCellValue("Nữ");
				}
				if(user.isActive()) {
					row.createCell(6).setCellValue("Hoạt động");
				}else {
					row.createCell(6).setCellValue("Không hoạt động");
				}
				row.createCell(7).setCellValue(user.getRoleName());
				row.createCell(8).setCellValue(user.getCurrentAddress());
				row.createCell(9).setCellValue(user.getGeneration());
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static List<User> excelToUsers(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();

			List<User> users = new ArrayList<User>();
			int rowNumber = 0;
			while (rows.hasNext()) {
				XSSFRow row = ((XSSFRow) rows.next());
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = row.iterator();
				User user = new User();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						user.setStudentId(currentCell.getStringCellValue().trim());
						break;
					case 1:
						user.setName(currentCell.getStringCellValue().trim());
						break;
					case 2:
						DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						if(isValid(currentCell.getStringCellValue().trim())) {
						user.setDateOfBirth(LocalDate.parse(currentCell.getStringCellValue().trim(), f));
						}else {
							user.setDateOfBirth(null);
						}
						break;
					case 3:
						user.setPhone(currentCell.getStringCellValue().trim());
						break;
					case 4:
							user.setEmail(currentCell.getStringCellValue().trim());
						break;
					case 5:
						String gender = currentCell.getStringCellValue().trim().toLowerCase();
						if (gender.equals("nam")) {
							user.setGender(true);
						} else if(gender.equals("nữ")){
							user.setGender(false);
						}else {
							user.setGender(null);
						}
						break;
					case 6:
						String active = currentCell.getStringCellValue().trim().toLowerCase();
						if (active.equals("hoạt động")) {
							user.setActive(true);
						} else if (active.equals("không hoạt động")) {
							user.setActive(false);
						} else {
							user.setActive(null);
						}
						break;
					case 7:
						String roleInExcel = currentCell.getStringCellValue().trim();
						Role roleInDb = new Role();
						Utils.convertRoleFromExcelToDb(roleInExcel, roleInDb);
						user.setRole(roleInDb);
						break;
					case 8:
						user.setCurrentAddress(currentCell.getStringCellValue().trim());
						break;
					case 9:
							user.setGeneration((int)currentCell.getNumericCellValue());
					default:
						break;
					}
					cellIdx++;
				}
				users.add(user);
			}
			workbook.close();
			return users;
		} catch (IOException e) {
			// TODO: handle exception
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream usersToExcelWithErrorMessage(List<UserDto> users) {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);
			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < HEADERsForErrors.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERsForErrors[col]);
			}
			int rowIdx = 1;
			for (UserDto user : users) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(user.getStudentId());
				row.createCell(1).setCellValue(user.getName());
				if(user.getDateOfBirth().equals(null)) {
					row.createCell(2).setCellValue("Error");
				}else {
					row.createCell(2).setCellValue(user.getDateOfBirth().toString());
				}
				
				row.createCell(3).setCellValue(user.getPhone());
				row.createCell(4).setCellValue(user.getEmail());
				if (user.isGender().equals(null)) {
					row.createCell(5).setCellValue("Error");
				} else if (user.isGender()) {
					row.createCell(5).setCellValue("Nam");
				} else {
					row.createCell(5).setCellValue("Nữ");
				}
				if (user.isActive().equals(null)) {
					row.createCell(6).setCellValue("Error");
				} else if (user.isActive()) {
					row.createCell(6).setCellValue("Hoạt động");
				} else {
					row.createCell(6).setCellValue("Không hoạt động");
				}
				row.createCell(7).setCellValue(user.getRoleName());
				row.createCell(8).setCellValue(user.getCurrentAddress());
				row.createCell(9).setCellValue(user.getGeneration());
				row.createCell(10).setCellValue(user.getMessageError());
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}
	 public static boolean isValid(String dateStr) {
	        try {
	            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	        } catch (DateTimeParseException e) {
	            return false;
	        }
	        return true;
	    }
	 
}

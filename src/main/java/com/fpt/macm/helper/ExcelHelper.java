package com.fpt.macm.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				User user = new User();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						user.setStudentId(currentCell.getStringCellValue());
						break;
					case 1:
						user.setName(currentCell.getStringCellValue());
						break;
					case 2:
						DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						user.setDateOfBirth(LocalDate.parse(currentCell.getStringCellValue(), f));
						break;
					case 3:
						user.setPhone(currentCell.getStringCellValue());
						break;
					case 4:
						user.setEmail(currentCell.getStringCellValue());
						break;
					case 5:
						String gender = currentCell.getStringCellValue();
						if (gender.equals("Nam")) {
							user.setGender(true);
						} else {
							user.setGender(false);
						}
						break;
					case 6:
						String active = currentCell.getStringCellValue();
						if (active.equals("Hoạt động")) {
							user.setActive(true);
						} else {
							user.setGender(false);
						}
						break;
					case 7:
						String roleInExcel = currentCell.getStringCellValue();
						Role roleInDb = new Role();
						Utils.convertRoleFromExcelToDb(roleInExcel, roleInDb);
						user.setRole(roleInDb);
						break;
					case 8:
						user.setCurrentAddress(currentCell.getStringCellValue());
						break;
					case 9:
						user.setGeneration((int) currentCell.getNumericCellValue());
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

}

package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.dto.ExhibitionResultByTypeDto;
import com.fpt.macm.model.dto.ExhibitionTeamDto;
import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.entity.CompetitiveMatch;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionResult;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AreaRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.ExhibitionResultRepository;
import com.fpt.macm.repository.ExhibitionTeamRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
import com.fpt.macm.utils.Utils;

@Service
public class ExhibitionResultServiceImpl implements ExhibitionResultService {

	@Autowired
	TournamentRepository tournamentRepository;

	@Autowired
	TournamentScheduleRepository tournamentScheduleRepository;

	@Autowired
	AreaRepository areaRepository;

	@Autowired
	ExhibitionResultRepository exhibitionResultRepository;

	@Autowired
	ExhibitionTeamRepository exhibitionTeamRepository;

	@Autowired
	CompetitiveTypeService competitiveTypeService;

	@Autowired
	CompetitiveMatchRepository competitiveMatchRepository;

	@Autowired
	ExhibitionTypeRepository exhibitionTypeRepository;

	@Override
	public ResponseMessage spawnTimeAndArea(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CompetitiveMatch> listMatch = new ArrayList<CompetitiveMatch>();
			Set<CompetitiveType> listCompetitiveType = competitiveTypeService.getAllTypeByTournament(tournamentId);
			List<Area> listArea = areaRepository.findAll();
			for (CompetitiveType competitiveType : listCompetitiveType) {
				List<CompetitiveMatch> listMatchByType = competitiveMatchRepository
						.listMatchsByType(competitiveType.getId());
				for (CompetitiveMatch competitiveMatch : listMatchByType) {
					if (competitiveMatch.getRound() == 1 && (competitiveMatch.getFirstStudentId() == null
							|| competitiveMatch.getSecondStudentId() == null)) {
						continue;
					}
					listMatch.add(competitiveMatch);
				}
			}
			Collections.sort(listMatch);
			List<TournamentSchedule> listTournamentSchedules = tournamentScheduleRepository
					.findByTournamentId(tournamentId);
			int countMatchNeedHeld = listMatch.size();

			int matchEveryDay = countMatchNeedHeld / listTournamentSchedules.size();
			int matchSurplus = countMatchNeedHeld % listTournamentSchedules.size();
			matchEveryDay += matchSurplus;
			List<ExhibitionResult> listResult = new ArrayList<ExhibitionResult>();
			Tournament getTournament = tournamentRepository.findById(tournamentId).get();
			Set<ExhibitionType> listType = getTournament.getExhibitionTypes();
			List<ExhibitionType> listTypeNeedHeld = new ArrayList<ExhibitionType>();
			for (ExhibitionType exhibitionType : listType) {

				if (exhibitionType.getExhibitionTeams().size() > 0) {
					listTypeNeedHeld.add(exhibitionType);
				}
			}
			Area getArea = listArea.get(0);
			int index = 0;
			boolean isRunning = true;
			for (TournamentSchedule tournamentSchedule : listTournamentSchedules) {
				LocalDate getDate = tournamentSchedule.getDate();

				LocalTime startTime = tournamentSchedule.getStartTime();
				startTime = startTime.plusMinutes(10 * (matchEveryDay / listArea.size() + 1));
				while (true) {
					int countMatchCanHeld = ((tournamentSchedule.getFinishTime().getHour()
							- tournamentSchedule.getStartTime().getHour()) * 60
							+ tournamentSchedule.getFinishTime().getMinute()
							- tournamentSchedule.getStartTime().getMinute()) / 5;
					if (listTypeNeedHeld.get(index).getExhibitionTeams().size() > countMatchCanHeld) {
						break;
					}
					ExhibitionType getType = listTypeNeedHeld.get(index);
					Set<ExhibitionTeam> getTeamsByType = getType.getExhibitionTeams();
					List<ExhibitionTeam> getTeams = new ArrayList<ExhibitionTeam>();
					for (ExhibitionTeam exhibitionTeam : getTeamsByType) {
						getTeams.add(exhibitionTeam);
					}
					for (ExhibitionTeam exhibitionTeam : getTeams) {
						ExhibitionResult newResult = new ExhibitionResult();
						newResult.setTeam(exhibitionTeam);
						newResult.setArea(getArea);
						LocalDateTime getTime = LocalDateTime.of(getDate, startTime);
						newResult.setTime(getTime);
						newResult.setCreatedBy("LinhLHN");
						newResult.setCreatedOn(LocalDateTime.now());
						newResult.setUpdatedBy("LinhLHN");
						newResult.setUpdatedOn(LocalDateTime.now());
						exhibitionResultRepository.save(newResult);
						listResult.add(newResult);
						startTime = startTime.plusMinutes(5);
					}
					index++;

					if (index == listTypeNeedHeld.size()) {
						isRunning = false;
					} else {
						continue;
					}
					if (!isRunning) {
						break;
					}
				}
				if (!isRunning) {
					break;
				}
			}
			responseMessage.setData(listResult);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListExhibitionResult(int exhibitionTypeId, String date) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<ExhibitionResult> listResult = new ArrayList<ExhibitionResult>();
			if (exhibitionTypeId != 0) {
				ExhibitionType getType = exhibitionTypeRepository.findById(exhibitionTypeId).get();
				Set<ExhibitionTeam> getTeams = getType.getExhibitionTeams();
				for (ExhibitionTeam exhibitionTeam : getTeams) {
					ExhibitionResult getResult = exhibitionResultRepository.findByTeam(exhibitionTeam.getId()).get();
					listResult.add(getResult);
				}
			} else if (date != "") {
				LocalDate getDate = Utils.ConvertStringToLocalDate(date);
				List<ExhibitionResult> listAll = exhibitionResultRepository.findAll();
				for (ExhibitionResult exhibitionResult : listAll) {
					if (exhibitionResult.getTime().toLocalDate().equals(getDate)) {
						listResult.add(exhibitionResult);
					}
				}
			}
			if (listResult.size() > 0) {
				responseMessage.setData(listResult);
				responseMessage.setMessage("Danh sách các trận đấu");
			} else {
				responseMessage.setMessage("Không có trận nào");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateExhibitionResult(int exhibitionTeamId, double score) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			ExhibitionResult getResult = exhibitionResultRepository.findByTeam(exhibitionTeamId).get();
			getResult.setScore(score);
			getResult.setUpdatedBy("LinhLHN");
			getResult.setUpdatedOn(LocalDateTime.now());
			exhibitionResultRepository.save(getResult);
			responseMessage.setData(Arrays.asList(getResult));
			responseMessage.setMessage(
					"Cập nhật điểm cho đội " + exhibitionTeamRepository.findById(exhibitionTeamId).get().getTeamName());
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getExhibitionResultByType(int exhibitionTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionType> getTypeOp = exhibitionTypeRepository.findById(exhibitionTypeId);
			if(getTypeOp.isPresent()) {
				ExhibitionResultByTypeDto exhibitionResultByTypeDto = new ExhibitionResultByTypeDto();
				ExhibitionType getType = getTypeOp.get();
				exhibitionResultByTypeDto.setExhibitionType(getType);
				responseMessage.setData(Arrays.asList(exhibitionResultByTypeDto));
				Set<ExhibitionTeam> getTeams = getType.getExhibitionTeams();
				List<ExhibitionTeamDto> listResult = new ArrayList<ExhibitionTeamDto>();
				for (ExhibitionTeam exhibitionTeam : getTeams) {
					Optional<ExhibitionResult> exhibitionResultOp = exhibitionResultRepository.findByTeam(exhibitionTeam.getId());
					if(exhibitionResultOp.isPresent()) {
						ExhibitionResult exhibitionResult = exhibitionResultOp.get();
						if(exhibitionResult.getScore() == null) {
							responseMessage.setMessage("Đội " + exhibitionTeam.getTeamName() + " chưa có điểm");
							return responseMessage;
						} else {
							ExhibitionTeamDto exhibitionTeamDto = new ExhibitionTeamDto();
							exhibitionTeamDto.setTeamName(exhibitionTeam.getTeamName());
							exhibitionTeamDto.setScore(exhibitionResult.getScore());
							listResult.add(exhibitionTeamDto);
						}
					}
					else {
						responseMessage.setMessage("Đội " + exhibitionTeam.getTeamName() + " chưa được xếp lịch thi đấu");
						return responseMessage;
					}
				}
				Collections.sort(listResult, new Comparator<ExhibitionTeamDto>() {
					@Override
					public int compare(ExhibitionTeamDto o1, ExhibitionTeamDto o2) {
						// TODO Auto-generated method stub
						return o1.getScore() - o2.getScore() < 0? 1 : (o1.getScore() - o2.getScore() > 0? -1 : 0);
					}
				});
				exhibitionResultByTypeDto.setListResult(listResult);
				responseMessage.setData(Arrays.asList(exhibitionResultByTypeDto));
				responseMessage.setMessage("Kết quả thi đấu của nội dung " + getType.getName());
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}

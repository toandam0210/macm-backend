package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;

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
	CompetitiveTypeService competitiveTypeService;

	@Autowired
	CompetitiveMatchRepository competitiveMatchRepository;

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
				while(true) {
					int countMatchCanHeld = ((tournamentSchedule.getFinishTime().getHour() - tournamentSchedule.getStartTime().getHour()) * 60
							+ tournamentSchedule.getFinishTime().getMinute() - tournamentSchedule.getStartTime().getMinute()) / 5;
					if(listTypeNeedHeld.get(index).getExhibitionTeams().size() > countMatchCanHeld) {
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

}

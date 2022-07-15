package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.dto.ExhibitionTeamDto;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionResult;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.ExhibitionResultRepository;
import com.fpt.macm.repository.ExhibitionTeamRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;



@Service
public class ExhibitionTeamServiceImpl implements ExhibitionTeamService{

	@Autowired
	ExhibitionTypeRepository exhibitionTypeRepository;
	
	@Autowired
	ExhibitionTeamRepository exhibitionTeamRepository;
	
	@Autowired
	ExhibitionPlayerRepository exhibitionPlayerRepository;
	
	@Autowired
	ExhibitionResultRepository exhibitionResultRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TournamentPlayerRepository tournamentPlayerRepository;
	
	@Autowired
	TournamentRepository tournamentRepository;
	
	@Override
	public ResponseMessage registerTeam(int exhibitionTypeId, String name, List<String> listStudentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			ExhibitionType getType = exhibitionTypeRepository.getById(exhibitionTypeId);
			ExhibitionTeam newTeam = new ExhibitionTeam();
			newTeam.setTeamName(name);
			Set<ExhibitionPlayer> listMembers = new HashSet<>();
			boolean isRegister = true;
			int countMale = 0;
			int countFemale = 0;
			for (String studentId : listStudentId) {
				User getUser = userRepository.findByStudentId(studentId).get();
				if(getUser.isGender()) {
					countMale++;
				}
				else {
					countFemale++;
				}
				Optional<TournamentPlayer> getTournamentPlayerOp = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(getUser.getId(), exhibitionTypeRepository.findTournamentOfType(exhibitionTypeId));
				if(getTournamentPlayerOp.isPresent()) {
					TournamentPlayer getTournamentPlayer = getTournamentPlayerOp.get();
					Optional<ExhibitionPlayer> getExhibitionPlayerOp = exhibitionPlayerRepository.findByTournamentPlayerAndType(getTournamentPlayer.getId(), getType.getId());
					if(getExhibitionPlayerOp.isPresent()) {
						isRegister = false;
						responseMessage.setMessage("Có thành viên đã đăng ký nội dung này");
						break;
					}
					else {
						continue;
					}
				}
			}
			if(countMale != getType.getNumberMale() || countFemale != getType.getNumberFemale()) {
				responseMessage.setMessage("Số lượng thành viên không hợp lệ");
			}
			else if(isRegister) {
				for (int i = 0; i < listStudentId.size(); i++) {
					User getUser = userRepository.findByStudentId(listStudentId.get(i)).get();
					Optional<TournamentPlayer> getTournamentPlayerOp = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(getUser.getId(), exhibitionTypeRepository.findTournamentOfType(exhibitionTypeId));
					if(getTournamentPlayerOp.isPresent()) {
						TournamentPlayer getTournamentPlayer = getTournamentPlayerOp.get();
						ExhibitionPlayer newExhibitionPlayer = new ExhibitionPlayer();
						newExhibitionPlayer.setTournamentPlayer(getTournamentPlayer);
						newExhibitionPlayer.setRoleInTeam(i == 0? true : false);
						listMembers.add(newExhibitionPlayer);
					}
					else {
						TournamentPlayer newTournamentPlayer = new TournamentPlayer();
						newTournamentPlayer.setUser(getUser);
						newTournamentPlayer.setPaymentStatus(false);
						newTournamentPlayer.setCreatedBy("LinhLHN");
						newTournamentPlayer.setCreatedOn(LocalDateTime.now());
						newTournamentPlayer.setUpdatedBy("LinhLHN");
						newTournamentPlayer.setUpdatedOn(LocalDateTime.now());
						Tournament getTournament = tournamentRepository.getById(exhibitionTypeRepository.findTournamentOfType(exhibitionTypeId));
						Set<TournamentPlayer> getTournamentPlayers = getTournament.getTournamentPlayers();
						getTournamentPlayers.add(newTournamentPlayer);
						getTournament.setTournamentPlayers(getTournamentPlayers);
						TournamentPlayer getTournamentPlayer = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(getUser.getId(), exhibitionTypeRepository.findTournamentOfType(exhibitionTypeId)).get();
						ExhibitionPlayer newExhibitionPlayer = new ExhibitionPlayer();
						newExhibitionPlayer.setTournamentPlayer(getTournamentPlayer);
						newExhibitionPlayer.setRoleInTeam(i == 0);
						listMembers.add(newExhibitionPlayer);
					}
				}
				newTeam.setExhibitionPlayers(listMembers);
				responseMessage.setData(Arrays.asList(newTeam));
				responseMessage.setMessage("Đăng ký thành công");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getTeamByType(int exhibitionTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			ExhibitionType getType = exhibitionTypeRepository.findById(exhibitionTypeId).get();
			Set<ExhibitionTeam> getTeams = getType.getExhibitionTeams();
			responseMessage.setData(Arrays.asList(getTeams));
			responseMessage.setMessage("Danh sách các đội biểu diễn nội dung " + getType.getName());
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getTop3TeamByType(int exhibitionTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			ExhibitionType getType = exhibitionTypeRepository.findById(exhibitionTypeId).get();
			Set<ExhibitionTeam> getTeams = getType.getExhibitionTeams();
			List<ExhibitionTeamDto> getTeamDto = new ArrayList<ExhibitionTeamDto>();
			boolean isDone = true;
			for (ExhibitionTeam exhibitionTeam : getTeams) {
				Optional<ExhibitionResult> getResultOp = exhibitionResultRepository.findByTeam(exhibitionTeam.getId());
				if(getResultOp.isPresent()) {
					ExhibitionTeamDto newExhibitionTeamDto = new ExhibitionTeamDto();
					newExhibitionTeamDto.setId(exhibitionTeam.getId());
					newExhibitionTeamDto.setTeamName(exhibitionTeam.getTeamName());
					newExhibitionTeamDto.setScore(getResultOp.get().getScore());
					getTeamDto.add(newExhibitionTeamDto);
				}
				else {
					isDone = false;
					break;
				}
			}
			if(isDone) {
				Collections.sort(getTeamDto);
				if(getTeamDto.size() >= 3) {
					responseMessage.setData(getTeamDto.subList(0, 2));
				}
				else {
					responseMessage.setData(getTeamDto);
				}
				responseMessage.setMessage("Danh sách các đội biểu diễn nội dung " + getType.getName());
			}
			else {
				responseMessage.setMessage("Nội dung này vẫn còn đội biểu diễn chưa thi");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}

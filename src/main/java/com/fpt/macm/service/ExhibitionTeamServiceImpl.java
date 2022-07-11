package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.ExhibitionPlayer;
import com.fpt.macm.model.ExhibitionTeam;
import com.fpt.macm.model.ExhibitionType;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Tournament;
import com.fpt.macm.model.TournamentPlayer;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
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
	UserRepository userRepository;
	
	@Autowired
	TournamentPlayerRepository tournamentPlayerRepository;
	
	@Autowired
	TournamentRepository tournamentRepository;
	
	@Override
	public ResponseMessage registerTeam(int exhibition_type_id, String name, List<String> listStudentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			ExhibitionType getType = exhibitionTypeRepository.getById(exhibition_type_id);
			ExhibitionTeam newTeam = new ExhibitionTeam();
			newTeam.setTeamName(name);
			Set<ExhibitionPlayer> listMembers = new HashSet<>();
			boolean isRegister = true;
			for (String studentId : listStudentId) {
				User getUser = userRepository.findByStudentId(studentId).get();
				Optional<TournamentPlayer> getTournamentPlayerOp = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(getUser.getId(), exhibitionTypeRepository.findTournamentOfType(exhibition_type_id));
				if(getTournamentPlayerOp.isPresent()) {
					TournamentPlayer getTournamentPlayer = getTournamentPlayerOp.get();
					Optional<ExhibitionPlayer> getExhibitionPlayerOp = exhibitionPlayerRepository.findByTournamentPlayerAndType(getTournamentPlayer.getId(), getType.getId());
					if(getExhibitionPlayerOp.isPresent()) {
						isRegister = false;
						responseMessage.setMessage("Có thành viên đã đăng ký nội dung này");
					}
					else {
						continue;
					}
				}
			}
			if(isRegister) {
				for (int i = 0; i < listStudentId.size(); i++) {
					User getUser = userRepository.findByStudentId(listStudentId.get(i)).get();
					Optional<TournamentPlayer> getTournamentPlayerOp = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(getUser.getId(), exhibitionTypeRepository.findTournamentOfType(exhibition_type_id));
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
						Tournament getTournament = tournamentRepository.getById(exhibitionTypeRepository.findTournamentOfType(exhibition_type_id));
						Set<TournamentPlayer> getTournamentPlayers = getTournament.getTournamentPlayers();
						getTournamentPlayers.add(newTournamentPlayer);
						getTournament.setTournamentPlayers(getTournamentPlayers);
						TournamentPlayer getTournamentPlayer = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(getUser.getId(), exhibitionTypeRepository.findTournamentOfType(exhibition_type_id)).get();
						ExhibitionPlayer newExhibitionPlayer = new ExhibitionPlayer();
						newExhibitionPlayer.setTournamentPlayer(getTournamentPlayer);
						newExhibitionPlayer.setRoleInTeam(i == 0? true : false);
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

}

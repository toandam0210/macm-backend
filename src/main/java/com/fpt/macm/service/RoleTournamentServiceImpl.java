package com.fpt.macm.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.RoleTournament;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.RoleTournamentRepository;

@Service
public class RoleTournamentServiceImpl implements RoleTournamentService{

	@Autowired
	RoleTournamentRepository roleTournamentRepository;

	@Override
	public ResponseMessage getAllRoleTournament() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<RoleTournament> rolesTournament = roleTournamentRepository.findByIsActiveOrderByIdAsc(true);
			if (!rolesTournament.isEmpty()) {
				responseMessage.setData(rolesTournament);
				responseMessage.setMessage("Lấy tất cả vai trò mặc định trong giải đấu thành công");
			} else {
				responseMessage.setMessage("Chưa có vai trò mặc định nào trong sự kiện");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage addNewRoleTournament(String newName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if (!newName.trim().equals("")) {
				List<RoleTournament> rolesTournament = roleTournamentRepository.findAll();
				for (RoleTournament roleTournament : rolesTournament) {
					if (roleTournament.getName().toLowerCase().equals(newName.toLowerCase().trim())) {
						responseMessage.setMessage("Đã có vai trò này");
						return responseMessage;
					}
				}

				RoleTournament roleTournament = new RoleTournament();
				roleTournament.setName(newName);
				roleTournament.setActive(true);
				roleTournamentRepository.save(roleTournament);

				responseMessage.setData(Arrays.asList(roleTournament));
				responseMessage.setMessage("Thêm vai trò mặc định cho giải đấu thành công");
			} else {
				responseMessage.setMessage("Không được để trống tên vai trò");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateRoleTournamentName(int roleTournamentId, String newName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if (!newName.trim().equals("")) {
				List<RoleTournament> rolesTournament = roleTournamentRepository.findAll();
				for (RoleTournament roleTournament : rolesTournament) {
					if (roleTournament.getName().toLowerCase().equals(newName.toLowerCase().trim())) {
						responseMessage.setMessage("Đã có vai trò này");
						return responseMessage;
					}
				}

				Optional<RoleTournament> roleTournamentOp = roleTournamentRepository.findById(roleTournamentId);
				if (roleTournamentOp.isPresent()) {
					RoleTournament roleTournament = roleTournamentOp.get();
					roleTournament.setName(newName);
					roleTournamentRepository.save(roleTournament);
				} else {
					responseMessage.setMessage("Không có vai trò này");
				}
			} else {
				responseMessage.setMessage("Không được để trống tên vai trò");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateStatusRoleTournament(int roleTournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<RoleTournament> roleTournamenttOp = roleTournamentRepository.findById(roleTournamentId);
			if (roleTournamenttOp.isPresent()) {
				RoleTournament roleTournament = roleTournamenttOp.get();
				roleTournament.setActive(!roleTournament.isActive());
				roleTournamentRepository.save(roleTournament);

				responseMessage.setData(Arrays.asList(roleTournament));
				responseMessage.setMessage("Cập nhật trạng thái của vai trò mặc định trong sự kiện thành công");
			} else {
				responseMessage.setMessage("Không có vai trò này");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
}

package com.fpt.macm.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.CompetitivePlayerBracket;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;

@Service
public class CompetitivePlayerBracketServiceImpl implements CompetitivePlayerBracketService{

	@Autowired
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;
	
	@Override
	public ResponseMessage getListPlayerBracket(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CompetitivePlayerBracket> listPlayers = competitivePlayerBracketRepository.listPlayersByType(competitiveTypeId);
			int numberPlayer = listPlayers.size();
			boolean isOrder = true;
			for (CompetitivePlayerBracket competitivePlayerBracket : listPlayers) {
				if(competitivePlayerBracket.getNumericalOrderId() == null) {
					isOrder = false;
					break;
				}
			}
			if(!isOrder) {
				Collections.shuffle(listPlayers);
				for(int i = 0; i < numberPlayer; i++) {
					CompetitivePlayerBracket getcompetitivePlayerBracket = listPlayers.get(i);
					getcompetitivePlayerBracket.setNumericalOrderId(i + 1);
					competitivePlayerBracketRepository.save(getcompetitivePlayerBracket);
				}
			}
			List<CompetitivePlayerBracket> listSortByNumerical = competitivePlayerBracketRepository.listSortByNumerical(competitiveTypeId);
			responseMessage.setData(listSortByNumerical);
			responseMessage.setMessage("Danh sách tuyển thủ");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
}

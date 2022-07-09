package com.fpt.macm.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.CompetitivePlayerBracket;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;

@Service
public class CompetitivePlayerBracketServiceImpl implements CompetitivePlayerBracketService{

	@Autowired
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;
	
	@Override
	public ResponseMessage getListPlayerBracket(int competitiveTypeId, int round) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CompetitivePlayerBracket> listByTypeAndRound = competitivePlayerBracketRepository.listByTypeAndRound(competitiveTypeId, round);
			int numberPlayer = listByTypeAndRound.size();
			if(round == 1) {
				if(numberPlayer < 4) {
					responseMessage.setMessage("Số lượng tuyển thủ không đạt 4 người trở lên, hủy bỏ hạng cân này");
				}
				else {
					boolean isOrder = true;
					for (CompetitivePlayerBracket competitivePlayerBracket : listByTypeAndRound) {
						if(competitivePlayerBracket.getNumerical_order_id() == null) {
							isOrder = false;
							break;
						}
					}
					if(!isOrder) {
						Collections.shuffle(listByTypeAndRound);
						for(int i = 0; i < numberPlayer; i++) {
							CompetitivePlayerBracket getcompetitivePlayerBracket = listByTypeAndRound.get(i);
							getcompetitivePlayerBracket.setNumerical_order_id(i + 1);
							competitivePlayerBracketRepository.save(getcompetitivePlayerBracket);
						}
					}
					List<CompetitivePlayerBracket> listSortByNumerical = competitivePlayerBracketRepository.listSortByNumerical(competitiveTypeId, round);
					responseMessage.setData(listSortByNumerical);
					responseMessage.setMessage("Danh sách tuyển thủ vòng 1");
				}
			}
			else {	
				List<CompetitivePlayerBracket> listSortByNumerical = competitivePlayerBracketRepository.listSortByNumerical(competitiveTypeId, round);
				if(listSortByNumerical.isEmpty()) {
					responseMessage.setMessage("Không thi đấu đến vòng này");
				}
				else if(listSortByNumerical.size() == 1) {
					responseMessage.setData(listSortByNumerical);
					responseMessage.setMessage("Tuyển thủ vô địch sau " + round + " vòng đấu");
				}
				else {
					responseMessage.setData(listSortByNumerical);
					responseMessage.setMessage("Danh sách tuyển thủ vòng " + round);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
}

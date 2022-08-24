package com.fpt.macm.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.dto.TournamentSampleTypeDto;
import com.fpt.macm.model.entity.CompetitiveTypeSample;
import com.fpt.macm.model.entity.ExhibitionTypeSample;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitiveTypeSampleRepository;
import com.fpt.macm.repository.ExhibitionTypeSampleRepository;

@Service
public class TournamentSampleServiceImpl implements TournamentSampleService{

	@Autowired
	CompetitiveTypeSampleRepository competitiveTypeSampleRepository;
	
	@Autowired
	ExhibitionTypeSampleRepository exhibitionTypeSampleRepository;
	
	@Override
	public ResponseMessage getAllSuggestType() {
		ResponseMessage responseMessage = new ResponseMessage();
		List<CompetitiveTypeSample> competitiveTypeSamples = competitiveTypeSampleRepository
				.findAll(Sort.by("id").ascending());
		List<ExhibitionTypeSample> exhibitionTypeSamples = exhibitionTypeSampleRepository
				.findAll(Sort.by("id").ascending());

		TournamentSampleTypeDto tournamentSampleTypeDto = new TournamentSampleTypeDto();
		tournamentSampleTypeDto.setCompetitiveTypeSamples(competitiveTypeSamples);
		tournamentSampleTypeDto.setExhibitionTypeSamples(exhibitionTypeSamples);

		responseMessage.setData(Arrays.asList(tournamentSampleTypeDto));
		responseMessage.setMessage("Lấy danh sách các thể thức thi đấu thành công");
		return responseMessage;
	}

	@Override
	public ResponseMessage addCompetitiveTypeSample(CompetitiveTypeSample competitiveTypeSample) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			competitiveTypeSampleRepository.save(competitiveTypeSample);
			responseMessage.setData(Arrays.asList(competitiveTypeSample));
			responseMessage.setMessage("Thêm hạng cân thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateCompetitiveTypeSample(int competitiveTypeSampleId,
			CompetitiveTypeSample newCompetitiveTypeSample) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitiveTypeSample> competitiveTypeSampleOp = competitiveTypeSampleRepository.findById(competitiveTypeSampleId);
			if (competitiveTypeSampleOp.isPresent()) {
				CompetitiveTypeSample competitiveTypeSample = competitiveTypeSampleOp.get();
				competitiveTypeSample.setGender(newCompetitiveTypeSample.isGender());
				competitiveTypeSample.setWeightMin(newCompetitiveTypeSample.getWeightMin());
				competitiveTypeSample.setWeightMax(newCompetitiveTypeSample.getWeightMax());
				competitiveTypeSampleRepository.save(competitiveTypeSample);
				
				responseMessage.setData(Arrays.asList(competitiveTypeSample));
				responseMessage.setMessage("Cập nhật hạng cân thành công");
			} else {
				responseMessage.setMessage("Không có hạng cân này");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteCompetitiveTypeSample(int competitiveTypeSampleId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitiveTypeSample> competitiveTypeSampleOp = competitiveTypeSampleRepository.findById(competitiveTypeSampleId);
			if (competitiveTypeSampleOp.isPresent()) {
				CompetitiveTypeSample competitiveTypeSample = competitiveTypeSampleOp.get();
				competitiveTypeSampleRepository.delete(competitiveTypeSample);
				
				responseMessage.setData(Arrays.asList(competitiveTypeSample));
				responseMessage.setMessage("Xóa hạng cân thành công");
			} else {
				responseMessage.setMessage("Không có hạng cân này");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage addExhibitionTypeSample(ExhibitionTypeSample exhibitionTypeSample) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			exhibitionTypeSampleRepository.save(exhibitionTypeSample);
			responseMessage.setData(Arrays.asList(exhibitionTypeSample));
			responseMessage.setMessage("Thêm nội dung thi đấu biểu diễn thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateExhibitionTypeSample(int exhibitionTypeSampleId,
			ExhibitionTypeSample newExhibitionTypeSample) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionTypeSample> exhibitionTypeSampleOp = exhibitionTypeSampleRepository.findById(exhibitionTypeSampleId);
			if (exhibitionTypeSampleOp.isPresent()) {
				ExhibitionTypeSample exhibitionTypeSample = exhibitionTypeSampleOp.get();
				exhibitionTypeSample.setName(newExhibitionTypeSample.getName());
				exhibitionTypeSample.setNumberFemale(newExhibitionTypeSample.getNumberFemale());
				exhibitionTypeSample.setNumberMale(newExhibitionTypeSample.getNumberMale());
				exhibitionTypeSampleRepository.save(exhibitionTypeSample);
				
				responseMessage.setData(Arrays.asList(exhibitionTypeSample));
				responseMessage.setMessage("Cập nhật hạng cân thành công");
			} else {
				responseMessage.setMessage("Không có hạng cân này");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteExhibitionTypeSample(int exhibitionTypeSampleId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionTypeSample> exhibitionTypeSampleOp = exhibitionTypeSampleRepository.findById(exhibitionTypeSampleId);
			if (exhibitionTypeSampleOp.isPresent()) {
				ExhibitionTypeSample exhibitionTypeSample = exhibitionTypeSampleOp.get();
				exhibitionTypeSampleRepository.delete(exhibitionTypeSample);
				
				responseMessage.setData(Arrays.asList(exhibitionTypeSample));
				responseMessage.setMessage("Cập nhật hạng cân thành công");
			} else {
				responseMessage.setMessage("Không có hạng cân này");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}

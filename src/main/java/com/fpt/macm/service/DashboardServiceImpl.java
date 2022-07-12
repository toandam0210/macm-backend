package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.AttendanceReportDto;
import com.fpt.macm.dto.EventDashboardDto;
import com.fpt.macm.dto.FeeBalanceDashboardDto;
import com.fpt.macm.dto.FeeDashboardDto;
import com.fpt.macm.model.AttendanceStatus;
import com.fpt.macm.model.ClubFund;
import com.fpt.macm.model.ClubFundReport;
import com.fpt.macm.model.CollaboratorReport;
import com.fpt.macm.model.Event;
import com.fpt.macm.model.EventPaymentStatusReport;
import com.fpt.macm.model.MemberEvent;
import com.fpt.macm.model.MembershipPaymentStatusReport;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Semester;
import com.fpt.macm.model.TournamentOrganizingCommitteePaymentStatusReport;
import com.fpt.macm.model.TournamentPlayerPaymentStatusReport;
import com.fpt.macm.model.TrainingSchedule;
import com.fpt.macm.model.UserStatusReport;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.ClubFundReportRepository;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.CollaboratorReportRepository;
import com.fpt.macm.repository.EventPaymentStatusReportRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.MembershipPaymentStatusReportRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteePaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentPlayerPaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.repository.UserStatusReportRepository;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	CollaboratorReportRepository collaboratorReportRepository;

	@Autowired
	SemesterRepository semesterRepository;

	@Autowired
	AttendanceStatusRepository attendanceStatusRepository;

	@Autowired
	TrainingScheduleRepository trainingScheduleRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	TournamentRepository tournamentRepository;

	@Autowired
	MemberEventRepository memberEventRepository;

	@Autowired
	EventService eventService;

	@Autowired
	UserStatusReportRepository userStatusReportRepository;

	@Autowired
	ClubFundReportRepository clubFundReportRepository;

	@Autowired
	MembershipPaymentStatusReportRepository membershipPaymentStatusReportRepository;

	@Autowired
	EventPaymentStatusReportRepository eventPaymentStatusReportRepository;

	@Autowired
	TournamentOrganizingCommitteePaymentStatusReportRepository tournamentOrganizingCommitteePaymentStatusReportRepository;

	@Autowired
	TournamentPlayerPaymentStatusReportRepository tournamentPlayerPaymentStatusReportRepository;
	
	@Autowired
	ClubFundRepository clubFundRepository;

	@Override
	public ResponseMessage getCollaboratorReport() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CollaboratorReport> collaboratorReports = collaboratorReportRepository.findAll();
			responseMessage.setData(collaboratorReports);
			responseMessage.setMessage("Lấy dữ liệu báo cáo CTV thành công");
			responseMessage.setTotalResult(collaboratorReports.size());
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage attendanceReport(String semesterName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Semester> semesterOp = semesterRepository.findByName(semesterName);
			List<AttendanceStatus> listAttendanceStatus = attendanceStatusRepository.findAll();
			List<AttendanceReportDto> attendanceReportDtos = new ArrayList<AttendanceReportDto>();
			if (semesterOp.isPresent()) {
				Semester semester = semesterOp.get();
				List<TrainingSchedule> trainingSchedulesBySemester = trainingScheduleRepository
						.listTrainingScheduleByTime(semester.getStartDate(), semester.getEndDate());
				for (TrainingSchedule trainingSchedule : trainingSchedulesBySemester) {
					int totalUserJoinTrainingSession = 0;
					int totalUserAttentInTrainingSession = 0;
					int totalUserAbsentInTrainingSession = 0;
					AttendanceReportDto attendanceReportDto = new AttendanceReportDto();
					attendanceReportDto.setDate(trainingSchedule.getDate());
					for (AttendanceStatus attendanceStatus : listAttendanceStatus) {
						if (trainingSchedule.getId() == attendanceStatus.getTrainingSchedule().getId()) {
							totalUserJoinTrainingSession++;
							if (attendanceStatus.getStatus() == 1) {
								totalUserAttentInTrainingSession++;
							}
							if (attendanceStatus.getStatus() == 0) {
								totalUserAbsentInTrainingSession++;
							}
						}
					}
					attendanceReportDto.setTotalAttendInTrainingSession(totalUserAttentInTrainingSession);
					attendanceReportDto.setTotalAbsentInTrainingSession(totalUserAbsentInTrainingSession);
					attendanceReportDto.setTotalUserJoin(totalUserJoinTrainingSession);
					attendanceReportDtos.add(attendanceReportDto);
				}
				List<AttendanceReportDto> listRemove = new ArrayList<AttendanceReportDto>();
				for (AttendanceReportDto attendanceReportDto : attendanceReportDtos) {
					if (attendanceReportDto.getDate().isAfter(LocalDate.now())) {
						listRemove.add(attendanceReportDto);
					}
				}
				attendanceReportDtos.removeAll(listRemove);
				responseMessage.setData(attendanceReportDtos);
				responseMessage.setMessage("Lấy dữ liệu điểm danh thành công");
				responseMessage.setTotalResult(attendanceReportDtos.size());
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage eventReport() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Event> events = eventRepository.findAll();
			List<EventDashboardDto> eventDashboardDtos = new ArrayList<EventDashboardDto>();
			for (Event event : events) {
				LocalDate startDate = eventService.getStartDate(event.getId());
				if (startDate.isBefore(LocalDate.now())) {
					List<MemberEvent> memberEvents = memberEventRepository.findByEventIdOrderByIdAsc(event.getId());
					EventDashboardDto eventDashboardDto = new EventDashboardDto();
					eventDashboardDto.setTotalMemberJoin(memberEvents.size());
					eventDashboardDto.setTotalRevenueInEvent(event.getTotalAmountActual());
					eventDashboardDto.setTotalExpenditure(event.getAmountPerRegisterActual() * memberEvents.size());
					eventDashboardDto.setEventName(event.getName());
					eventDashboardDtos.add(eventDashboardDto);
				}
			}
			responseMessage.setData(eventDashboardDtos);
			responseMessage.setMessage("Lấy dữ liệu sự kiện thành công");
			responseMessage.setTotalResult(eventDashboardDtos.size());
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage statusMemberReport() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<UserStatusReport> userStatusReports = userStatusReportRepository.findAll();
			if (userStatusReports.size() > 0) {
				responseMessage.setData(userStatusReports);
				responseMessage.setMessage("Lấy dữ liệu report thành công");
				responseMessage.setTotalResult(userStatusReports.size());
			} else {
				responseMessage.setMessage("Không có dữ liệu");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage feeReport(String semesterName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Semester> semesterOp = semesterRepository.findByName(semesterName);
			if (semesterOp.isPresent()) {
				Semester semester = semesterOp.get();

				int startMonth = semester.getStartDate().getMonthValue();
				int endMonth = semester.getEndDate().getMonthValue();
				if (endMonth == 1) {
					endMonth = 13;
				}
				int year = semester.getStartDate().getYear();

				List<FeeDashboardDto> feeDashboardsDto = new ArrayList<FeeDashboardDto>();
				
				for (int i = startMonth; i < endMonth; i++) {
					int totalIncome = 0;
					int totalSpend = 0;
					
					LocalDateTime startDate = LocalDateTime.of(year, i, 1, 0, 0, 0);
					LocalDate startDateTemp = startDate.toLocalDate();
					LocalDateTime endDate = LocalDateTime.of(year, i,
							startDateTemp.withDayOfMonth(startDateTemp.getMonth().length(startDateTemp.isLeapYear()))
									.getDayOfMonth(),
							23, 59, 59);

					List<ClubFundReport> clubFundReports = clubFundReportRepository.findAllFundChange(startDate,
							endDate);
					for (ClubFundReport clubFundReport : clubFundReports) {
						double fundChange = clubFundReport.getFundChange();
						if (fundChange < 0) {
							totalSpend += -fundChange;
						} else {
							totalIncome += fundChange;
						}
					}
					
					
					List<MembershipPaymentStatusReport> membershipPaymentStatusReports = membershipPaymentStatusReportRepository
							.findAllFundChange(startDate, endDate);
					for (MembershipPaymentStatusReport membershipPaymentStatusReport : membershipPaymentStatusReports) {
						double fundChange = membershipPaymentStatusReport.getFundChange();
						totalIncome += fundChange;
					}
					
					List<EventPaymentStatusReport> eventPaymentStatusReports = eventPaymentStatusReportRepository
							.findAllFundChange(startDate, endDate);
					for (EventPaymentStatusReport eventPaymentStatusReport : eventPaymentStatusReports) {
						double fundChange = eventPaymentStatusReport.getFundChange();
						totalIncome += fundChange;
					}
					
					List<TournamentOrganizingCommitteePaymentStatusReport> tournamentOrganizingCommitteePaymentStatusReports = tournamentOrganizingCommitteePaymentStatusReportRepository
							.findAllFundChange(startDate, endDate);
					for (TournamentOrganizingCommitteePaymentStatusReport tournamentOrganizingCommitteePaymentStatusReport : tournamentOrganizingCommitteePaymentStatusReports) {
						double fundChange = tournamentOrganizingCommitteePaymentStatusReport.getFundChange();
						totalIncome += fundChange;
					}
					
					List<TournamentPlayerPaymentStatusReport> tournamentPlayerPaymentStatusReports = tournamentPlayerPaymentStatusReportRepository
							.findAllFundChange(startDate, endDate);
					for (TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport : tournamentPlayerPaymentStatusReports) {
						double fundChange = tournamentPlayerPaymentStatusReport.getFundChange();
						totalIncome += fundChange;
					}
					
					List<FeeBalanceDashboardDto> feeBalancesDashboardDto = new ArrayList<FeeBalanceDashboardDto>();
					
					if (!clubFundReports.isEmpty()) {
						Comparator<ClubFundReport> clubFundReportComparator = Comparator.comparing(ClubFundReport::getCreatedOn);
						ClubFundReport latestClubFundReport = clubFundReports.stream().max(clubFundReportComparator).get();
						feeBalancesDashboardDto.add(createFeeBalanceDashboardDto(latestClubFundReport.getFundBalance(), latestClubFundReport.getCreatedOn()));
					}
					
					if (!membershipPaymentStatusReports.isEmpty()) {
						Comparator<MembershipPaymentStatusReport> membershipPaymentStatusReportComparator = Comparator.comparing(MembershipPaymentStatusReport::getCreatedOn);
						MembershipPaymentStatusReport latestMembershipPaymentStatusReport = membershipPaymentStatusReports.stream().max(membershipPaymentStatusReportComparator).get();
						feeBalancesDashboardDto.add(createFeeBalanceDashboardDto(latestMembershipPaymentStatusReport.getFundBalance(), latestMembershipPaymentStatusReport.getCreatedOn()));
					}
					
					if (!eventPaymentStatusReports.isEmpty()) {
						Comparator<EventPaymentStatusReport> eventPaymentStatusReportComparator = Comparator.comparing(EventPaymentStatusReport::getCreatedOn);
						EventPaymentStatusReport latestEventPaymentStatusReport = eventPaymentStatusReports.stream().max(eventPaymentStatusReportComparator).get();
						feeBalancesDashboardDto.add(createFeeBalanceDashboardDto(latestEventPaymentStatusReport.getFundBalance(), latestEventPaymentStatusReport.getCreatedOn()));
					}
					
					if (!tournamentOrganizingCommitteePaymentStatusReports.isEmpty()) {
						Comparator<TournamentOrganizingCommitteePaymentStatusReport> tournamentOrganizingCommitteePaymentStatusReportComparator = Comparator.comparing(TournamentOrganizingCommitteePaymentStatusReport::getCreatedOn);
						TournamentOrganizingCommitteePaymentStatusReport latestTournamentOrganizingCommitteePaymentStatusReport = tournamentOrganizingCommitteePaymentStatusReports.stream().max(tournamentOrganizingCommitteePaymentStatusReportComparator).get();
						feeBalancesDashboardDto.add(createFeeBalanceDashboardDto(latestTournamentOrganizingCommitteePaymentStatusReport.getFundBalance(), latestTournamentOrganizingCommitteePaymentStatusReport.getCreatedOn()));
					}
					
					if (!tournamentPlayerPaymentStatusReports.isEmpty()) {
						Comparator<TournamentPlayerPaymentStatusReport> tournamentPlayerPaymentStatusReportComparator = Comparator.comparing(TournamentPlayerPaymentStatusReport::getCreatedOn);
						TournamentPlayerPaymentStatusReport latestTournamentPlayerPaymentStatusReport = tournamentPlayerPaymentStatusReports.stream().max(tournamentPlayerPaymentStatusReportComparator).get();
						feeBalancesDashboardDto.add(createFeeBalanceDashboardDto(latestTournamentPlayerPaymentStatusReport.getFundBalance(), latestTournamentPlayerPaymentStatusReport.getCreatedOn()));
					}
					
					double latestBalance = 0;
					
					if (!feeBalancesDashboardDto.isEmpty()) {
						Comparator<FeeBalanceDashboardDto> feeBalanceDashboardDtoComparator = Comparator.comparing(FeeBalanceDashboardDto::getCreatedOn);
						FeeBalanceDashboardDto latestFeeBalanceDashboardDto = feeBalancesDashboardDto.stream().max(feeBalanceDashboardDtoComparator).get();
						latestBalance = latestFeeBalanceDashboardDto.getBalance();
					}
					
					FeeDashboardDto feeDashboardDto = new FeeDashboardDto();
					feeDashboardDto.setSemester(semesterName);
					feeDashboardDto.setMonth(i);
					feeDashboardDto.setTotalIncome(totalIncome);
					feeDashboardDto.setTotalSpend(totalSpend);
					
					if (i < LocalDate.now().getMonthValue()) {
						feeDashboardDto.setBalance(latestBalance);
					}
					else {
						feeDashboardDto.setBalance(getClubFund());
					}
					
					feeDashboardsDto.add(feeDashboardDto);
				}
				
				responseMessage.setData(feeDashboardsDto);
				responseMessage.setMessage("Lấy dữ liệu báo cáo thu chi theo kỳ thành công");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
	private FeeBalanceDashboardDto createFeeBalanceDashboardDto(double balance, LocalDateTime createdOn) {
		FeeBalanceDashboardDto feeBalanceDashboardDto = new FeeBalanceDashboardDto();
		feeBalanceDashboardDto.setBalance(balance);
		feeBalanceDashboardDto.setCreatedOn(createdOn);
		return feeBalanceDashboardDto;
	}
	
	private double getClubFund() {
		ClubFund clubFund = clubFundRepository.findById(1).get();
		return clubFund.getFundAmount();
	}
}

package com.spring.bioMedical.service;

import com.spring.bioMedical.entity.Appointment;
import com.spring.bioMedical.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private EmailService emailService;

	public void approveByAdmin(int id) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Appointment with ID " + id + " not found"));
		appointment.setAdminApproval(true);
		appointment.updateApprovalStatus();
		appointmentRepository.save(appointment);

		sendNotificationEmail(appointment);
	}

	public void rejectByAdmin(int id) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Appointment with ID " + id + " not found"));
		appointment.setAdminApproval(false);
		appointment.updateApprovalStatus();
		appointmentRepository.save(appointment);

		sendNotificationEmail(appointment);
	}

	public void approveByDoctor(int id) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Appointment with ID " + id + " not found"));
		appointment.setDoctorApproval(true);
		appointment.updateApprovalStatus();
		appointmentRepository.save(appointment);

		sendNotificationEmail(appointment);
	}

	public void rejectByDoctor(int id) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Appointment with ID " + id + " not found"));
		appointment.setDoctorApproval(false);
		appointment.updateApprovalStatus();
		appointmentRepository.save(appointment);

		sendNotificationEmail(appointment);
	}

	public List<Appointment> findAllAppointments() {
		return appointmentRepository.findAll();
	}

	public void save(Appointment obj) {
		appointmentRepository.save(obj);
	}

	private void sendNotificationEmail(Appointment appointment) {
		String status = appointment.getApprovalStatus().toString().toLowerCase();

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(appointment.getEmail());
		message.setSubject("Appointment Status Update");
		message.setText("Hello " + appointment.getName() + ",\n\n" +
				"Your appointment has been " + status + ".\n\n" +
				"Date: " + appointment.getDate() + "\n" +
				"Time: " + appointment.getTime() + "\n\n" +
				"Thank you,\nBioMedical Team");

		emailService.sendEmail(message);
	}
}

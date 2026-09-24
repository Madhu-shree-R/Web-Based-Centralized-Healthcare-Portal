package com.spring.bioMedical.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.spring.bioMedical.entity.Admin;
import com.spring.bioMedical.entity.Appointment;
import com.spring.bioMedical.service.AdminService;
import com.spring.bioMedical.service.AppointmentService;
import com.spring.bioMedical.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	private final UserService userService;
	private final AppointmentService appointmentService;
	private final AdminService adminService;

	@Autowired
	public DoctorController(UserService userService, AppointmentService appointmentService, AdminService adminService) {
		this.userService = userService;
		this.appointmentService = appointmentService;
		this.adminService = adminService;
	}

	@GetMapping("/index")
	public String index(Model model) {
		// Get logged-in doctor's username
		String username = getLoggedInUsername();

		// Fetch doctor details
		Admin admin = adminService.findByEmail(username);
		if (admin != null) {
			updateLastSeen(admin);
			model.addAttribute("name", admin.getFirstName());
			model.addAttribute("email", admin.getEmail());
			model.addAttribute("user", admin.getFirstName() + " " + admin.getLastName());
		}

		List<Appointment> list = appointmentService.findAllAppointments();
		model.addAttribute("appointments", list);
		return "doctor/index";
	}

	// Approve Appointment by Doctor
	@GetMapping("/approve-appointment/{id}")
	public String approveAppointment(@PathVariable("id") int appointmentId) {
		appointmentService.approveByDoctor(appointmentId);
		return "redirect:/doctor/index"; // Redirect back to the Doctor's appointments list
	}

	// Reject Appointment by Doctor
	@GetMapping("/reject-appointment/{id}")
	public String rejectAppointment(@PathVariable("id") int appointmentId) {
		appointmentService.rejectByDoctor(appointmentId);
		return "redirect:/doctor/index"; // Redirect back to the Doctor's appointments list
	}

	// Helper method to get the logged-in username
	private String getLoggedInUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return principal.toString();
	}

	// Helper method to update last seen time
	private void updateLastSeen(Admin admin) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		admin.setLastseen(formatter.format(new Date()));
		adminService.save(admin);
	}

	// Logout
	@GetMapping("/logout")
	public RedirectView logout() {
		SecurityContextHolder.clearContext();
		return new RedirectView("/showMyLoginPage?logout");
	}
}

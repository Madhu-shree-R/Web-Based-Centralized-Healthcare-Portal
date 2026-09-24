package com.spring.bioMedical.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.spring.bioMedical.entity.Appointment;
import com.spring.bioMedical.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.spring.bioMedical.entity.Admin;
import com.spring.bioMedical.service.AdminServiceImplementation;
import com.spring.bioMedical.service.UserService;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/admin")
public class AdminController {


	private UserService userService;

	private AdminServiceImplementation adminServiceImplementation;

	private AppointmentService appointmentService;


	@Autowired
	public AdminController(UserService userService,AdminServiceImplementation obj,
						   AppointmentService appointment) {
		this.userService = userService;
		adminServiceImplementation=obj;
		appointmentService=appointment;
	}


	@RequestMapping("/user-details")
	public String index(Model model){


		List<Admin> list=adminServiceImplementation.findByRole("ROLE_USER");
		model.addAttribute("user", list);


		// get last seen
		String username="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		  String Pass = ((UserDetails)principal).getPassword();
		  System.out.println("One + "+username+"   "+Pass);


		} else {
		 username = principal.toString();
		  System.out.println("Two + "+username);
		}

		Admin admin = adminServiceImplementation.findByEmail(username);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    Date now = new Date();

		         String log=now.toString();

		         admin.setLastseen(log);

		         adminServiceImplementation.save(admin);



		return "admin/user";
	}

	@RequestMapping("/doctor-details")
	public String doctorDetails(Model model){


		// get last seen
		String username="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		  String Pass = ((UserDetails)principal).getPassword();
		  System.out.println("One + "+username+"   "+Pass);


		} else {
		 username = principal.toString();
		  System.out.println("Two + "+username);
		}

		Admin admin = adminServiceImplementation.findByEmail(username);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    Date now = new Date();

		         String log=now.toString();

		         admin.setLastseen(log);

		         adminServiceImplementation.save(admin);



		List<Admin> list=adminServiceImplementation.findByRole("ROLE_DOCTOR");



		// add to the spring model
		model.addAttribute("user", list);


		return "admin/doctor";
	}

	@RequestMapping("/admin-details")
	public String adminDetails(Model model){


		// get last seen
		String username="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		  String Pass = ((UserDetails)principal).getPassword();
		  System.out.println("One + "+username+"   "+Pass);


		} else {
		 username = principal.toString();
		  System.out.println("Two + "+username);
		}

		Admin admin = adminServiceImplementation.findByEmail(username);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    Date now = new Date();

		         String log=now.toString();

		         admin.setLastseen(log);

		         adminServiceImplementation.save(admin);



		List<Admin> list=adminServiceImplementation.findByRole("ROLE_ADMIN");



		// add to the spring model
		model.addAttribute("user", list);


		return "admin/admin";
	}


	@GetMapping("/add-doctor")
	public String showFormForAdd(Model theModel) {


		// get last seen
		String username="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		  String Pass = ((UserDetails)principal).getPassword();
		  System.out.println("One + "+username+"   "+Pass);


		} else {
		 username = principal.toString();
		  System.out.println("Two + "+username);
		}

		Admin admin1 = adminServiceImplementation.findByEmail(username);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    Date now = new Date();

		         String log=now.toString();

		         admin1.setLastseen(log);

		         adminServiceImplementation.save(admin1);


		// create model attribute to bind form data
		Admin admin = new Admin();

		theModel.addAttribute("doctor", admin);

		return "admin/addDoctor";
	}


	@PostMapping("/save-doctor")
	public String saveEmployee(@ModelAttribute("doctor") Admin admin) {

		// save the employee
	//	admin.setId(0);

		admin.setRole("ROLE_DOCTOR");

		admin.setPassword("default");

		admin.setEnabled(true);

		admin.setConfirmationToken("ByAdmin-Panel");

		System.out.println(admin);

		adminServiceImplementation.save(admin);

		// use a redirect to prevent duplicate submissions
		return "redirect:/admin/add-doctor";
	}



	@GetMapping("/add-admin")
	public String showForm(Model theModel) {


		// get last seen
		String username="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		  String Pass = ((UserDetails)principal).getPassword();
		  System.out.println("One + "+username+"   "+Pass);


		} else {
		 username = principal.toString();
		  System.out.println("Two + "+username);
		}

		Admin admin1 = adminServiceImplementation.findByEmail(username);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    Date now = new Date();

		         String log=now.toString();

		         admin1.setLastseen(log);

		         adminServiceImplementation.save(admin1);


		// create model attribute to bind form data
		Admin admin = new Admin();
		theModel.addAttribute("doctor", admin);
		return "admin/addAdmin";
	}

	@PostMapping("/save-admin")
	public String saveEmploye(@ModelAttribute("admin") Admin admin) {
		// save the employee
	//	admin.setId(0);
		admin.setRole("ROLE_ADMIN");
		admin.setPassword("default");
		admin.setEnabled(true);
		admin.setConfirmationToken("ByAdmin-Panel");
		System.out.println(admin);
		adminServiceImplementation.save(admin);
		// use a redirect to prevent duplicate submissions
		return "redirect:/admin/add-admin";
	}

	@GetMapping("/edit-my-profile")
	public String EditForm(Model theModel) {

		String username="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		  String Pass = ((UserDetails)principal).getPassword();
		  System.out.println("One + "+username+"   "+Pass);
		} else {
		 username = principal.toString();
		  System.out.println("Two + "+username);
		}

		// get the employee from the service

		Admin admin = adminServiceImplementation.findByEmail(username);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    Date now = new Date();

		         String log=now.toString();

		         admin.setLastseen(log);

		         adminServiceImplementation.save(admin);

		System.out.println(admin);

		theModel.addAttribute("profile", admin);

		return "admin/updateMyProfile";
	}


	@PostMapping("/update")
	public String update(@ModelAttribute("profile") Admin admin) {


		System.out.println(admin);

		adminServiceImplementation.save(admin);

		// use a redirect to prevent duplicate submissions
		return "redirect:/admin/user-details";
	}



// ✅ FIX: Ensure the mapping matches the Thymeleaf navigation link
@GetMapping("/appointments")
public String viewAppointmentsAdmin(Model model) {
	List<Appointment> appointments = appointmentService.findAllAppointments();
	model.addAttribute("appointments", appointments);
	return "admin/appointment";
}

	@GetMapping("/approve-appointment/{id}")
	public String approveAppointmentAdmin(@PathVariable("id") int id) {
		appointmentService.approveByAdmin(id);
		return "redirect:/admin/appointments";
	}

	@GetMapping("/reject-appointment/{id}")
	public String rejectAppointmentAdmin(@PathVariable("id") int id) {
		appointmentService.rejectByAdmin(id);
		return "redirect:/admin/appointments";
	}
	@GetMapping("/logout")
	public RedirectView logout() {
		// Clear session data (if needed)
		SecurityContextHolder.clearContext();  // Clear security context

		// Redirect to login page after logout
		return new RedirectView("/showMyLoginPage?logout");  // Redirect after logout
	}
}



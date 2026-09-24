package com.spring.bioMedical.entity;

import javax.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    private String email;
    private String date;
    private String time;
    private String description;

    private Boolean adminApproval = null;
    private Boolean doctorApproval = null;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    public enum ApprovalStatus {
        PENDING, APPROVED, REJECTED
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getAdminApproval() { return adminApproval; }
    public void setAdminApproval(Boolean adminApproval) {
        this.adminApproval = adminApproval;
        updateApprovalStatus();
    }

    public Boolean getDoctorApproval() { return doctorApproval; }
    public void setDoctorApproval(Boolean doctorApproval) {
        this.doctorApproval = doctorApproval;
        updateApprovalStatus();
    }

    public ApprovalStatus getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(ApprovalStatus approvalStatus) { this.approvalStatus = approvalStatus; }

    // Automatically update status based on approvals
    public void updateApprovalStatus() {
        if (Boolean.TRUE.equals(adminApproval) || Boolean.TRUE.equals(doctorApproval)) {
            approvalStatus = ApprovalStatus.APPROVED;
        } else if (Boolean.FALSE.equals(adminApproval) && (doctorApproval == null || doctorApproval == false)) {
            approvalStatus = ApprovalStatus.REJECTED;
        } else {
            approvalStatus = ApprovalStatus.PENDING;
        }
    }
}

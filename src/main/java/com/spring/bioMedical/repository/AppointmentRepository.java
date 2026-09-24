package com.spring.bioMedical.repository;

import com.spring.bioMedical.entity.Appointment.ApprovalStatus; // ✅ Import the enum
import com.spring.bioMedical.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    // ✅ Fetch all pending appointments (Fixed Enum type)
    List<Appointment> findByApprovalStatus(ApprovalStatus approvalStatus);

    // ✅ Fetch all approved appointments
    List<Appointment> findByAdminApprovalTrueOrDoctorApprovalTrue();

    // ✅ Fetch all rejected appointments
    List<Appointment> findByAdminApprovalFalseAndDoctorApprovalFalse();

    // ✅ Fetch all appointments where admin approved but doctor hasn't
    List<Appointment> findByAdminApprovalTrueAndDoctorApprovalIsNull();

    // ✅ Fetch all appointments where doctor approved but admin hasn't
    List<Appointment> findByDoctorApprovalTrueAndAdminApprovalIsNull();

    // ✅ Fetch pending appointments using Query (Optimized)
    @Query("SELECT a FROM Appointment a WHERE a.approvalStatus = 'PENDING'")
    List<Appointment> findPendingAppointments();
}

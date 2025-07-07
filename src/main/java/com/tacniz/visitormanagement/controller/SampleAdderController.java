package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.dto.*;
import com.tacniz.visitormanagement.model.Appointment;
import com.tacniz.visitormanagement.model.Role;
import com.tacniz.visitormanagement.model.VisitType;
import com.tacniz.visitormanagement.repo.AppointmentRepository;
import com.tacniz.visitormanagement.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sample")
@RequiredArgsConstructor
public class SampleAdderController {

    private final UserService userService;
    private final VisitTypeService visitTypeService;
    private final VisitOptionService visitOptionService;
    private final DynamicQuestionService dynamicQuestionService;
    private final AppointmentService appointmentService;



    @GetMapping("/add")
    public void addSamples() throws IOException {
        createVisitor();
        craeteVisittype();
        createVisitOption();
    }

    @GetMapping("/future")
    public ResponseEntity<List<Appointment>> getFutureAppointments() {
        return ResponseEntity.ok(appointmentService.getAllFutureAppointments());
    }

    @GetMapping("/future-from")
    public ResponseEntity<List<Appointment>> getFutureAppointmentsFrom(
            @RequestParam LocalDateTime from) {
        return ResponseEntity.ok(appointmentService.getFutureAppointmentsFrom(from));
    }

    @GetMapping("/future-from-date")
    public ResponseEntity<List<Appointment>> getFutureAppointmentsFromDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate) {
        List<Appointment> appointments = appointmentService.getFutureAppointmentsFromDate(fromDate);
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Appointment>> createBulkAppointments(
        @RequestBody List<Appointment> request) {
        List<Appointment> savedAppointments =
                appointmentService.saveAllAppointments(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointments);
    }


    private void createVisitOption() {
        // Create button answers for multiple choice questions
        ButtonAnswerDTO buttonAnswer1 = ButtonAnswerDTO.builder()
                .buttonText("Business Meeting")
                .build();

        ButtonAnswerDTO buttonAnswer2 = ButtonAnswerDTO.builder()
                .buttonText("Personal Visit")
                .build();

        ButtonAnswerDTO buttonAnswer3 = ButtonAnswerDTO.builder()
                .buttonText("Delivery")
                .build();

        ButtonAnswerDTO buttonAnswer4 = ButtonAnswerDTO.builder()
                .buttonText("Interview")
                .build();

        ButtonAnswerDTO buttonAnswer5 = ButtonAnswerDTO.builder()
                .buttonText("Yes")
                .build();

        ButtonAnswerDTO buttonAnswer6 = ButtonAnswerDTO.builder()
                .buttonText("No")
                .build();

// Create dynamic questions
        DynamicQuestionDTO purposeQuestion = DynamicQuestionDTO.builder()
                .questionText("What is the purpose of your visit?")
                .isRequired(true)
                .answerType("button")
                .buttonAnswers(List.of(buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonAnswer4))
                .isActive(true)
                .canSelectMoreThanOne(false)
                .build();

        DynamicQuestionDTO covidQuestion = DynamicQuestionDTO.builder()
                .questionText("Have you had any COVID-19 symptoms in the last 14 days?")
                .specialInstructions("Please answer truthfully for everyone's safety")
                .isRequired(true)
                .answerType("button")
                .buttonAnswers(List.of(buttonAnswer5, buttonAnswer6))
                .isActive(true)
                .canSelectMoreThanOne(false)
                .build();

        DynamicQuestionDTO temperatureQuestion = DynamicQuestionDTO.builder()
                .questionText("What is your current body temperature?")
                .isRequired(false)
                .answerType("number")
                .isActive(true)
                .canSelectMoreThanOne(false)
                .build();

        DynamicQuestionDTO notesQuestion = DynamicQuestionDTO.builder()
                .questionText("Any special notes or requirements?")
                .isRequired(false)
                .answerType("text")
                .isActive(true)
                .canSelectMoreThanOne(false)
                .build();

// Create the visit option with dynamic questions
        VisitOptionDTO visitOption = VisitOptionDTO.builder()
                .visitOptionName("Standard Office Visit")
                .visitType(VisitTypeDTO.builder().id(1L).build())
                .description("General office visit with standard screening")
                .isPreRegistration(true)
                .isPhotoRequired(true)
                .isPhotoOptional(false)
                .isPhoneNumberRequired(true)
                .isEmailRequired(false)
                .dynamicQuestions(List.of(purposeQuestion, covidQuestion, temperatureQuestion, notesQuestion))
                .build();

        System.out.println(visitOption);
        VisitOptionDTO visitOptionDTO =  visitOptionService.createVisitOption(visitOption);
        System.out.println(visitOptionDTO);

    }

    private void craeteVisittype() throws IOException {
        visitTypeService.createVisitType(VisitTypeDTO.builder()
                .id(1L)
                .visitTypeName("Business Meeting")
                .visitTypeDescription("Meeting with clients or partners")
                .imageName("meeting-icon.png")
                .image(null) // or set a MultipartFile if available
                .visitOptions(new ArrayList<>())
                .build());
    }

    private void createVisitor(){
    userService.addVisitor(VisitorReqDto.builder()
            .id(null)
            .firstName("John")
            .lastName("Doe")
            .imagePath("path/to/image.jpg")
            .email("john.doie@example.com")
            .isEmailVerified(true)
            .phoneNumber("+1234567890")
            .isPhoneNumberVerified(true)
            .password("securePassword123")
            .role(Role.ROLE_VISITOR.toString())
            .build());

}}


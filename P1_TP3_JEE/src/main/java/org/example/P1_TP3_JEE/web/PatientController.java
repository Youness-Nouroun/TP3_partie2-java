package org.example.P1_TP3_JEE.web;

import jakarta.validation.*;
import lombok.AllArgsConstructor;
import org.example.P1_TP3_JEE.entities.Patient;
import org.example.P1_TP3_JEE.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size", defaultValue = "6") int s,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {
        Page<Patient> pagePatients = patientRepository.findByNomContains(kw, PageRequest.of(p,s));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", p);// utile pour la pagination
        model.addAttribute("keyword", kw);
        return "patients";
    }

    @GetMapping("/delete")
    public String delete(Long id, @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "") String keyword) {
        patientRepository.deleteById(id);
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/edit")
    public String editPatient(Model model, Long id,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "") String keyword) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            return "redirect:/index";
        }
        model.addAttribute("patient", patient);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "editPatient";
    }

    @PostMapping("/save")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "") String keyword) {
        if (bindingResult.hasErrors()) {
            return "editPatient";
        }

        // If it's an existing patient, preserve creation date if needed
        if (patient.getId() != null) {
            Patient existingPatient = patientRepository.findById(patient.getId()).orElse(null);
            if (existingPatient != null && patient.getDateNaissance() == null) {
                patient.setDateNaissance(existingPatient.getDateNaissance());
            }
        }

        patientRepository.save(patient);
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }


}
package net.vms.springboot.controller;

import net.vms.springboot.model.*;
import net.vms.springboot.repository.UserRepository;
import net.vms.springboot.service.VolunteerService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/login")
    public String loginCustomer(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, Model model) throws Exception {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (Objects.nonNull(existingUser) && user.getPassword().equals(existingUser.getPassword())) {
            return findPaginated(1, "name", "asc", model, "  ");
        }
        return "login";
    }


    @GetMapping("/")
    public String viewHomePage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @RequestMapping(path = {"/search"})
    public String search(Volunteer volunteer, Model model, String keyword) {
        if (keyword != null && !StringUtils.isEmpty(keyword)) {
            User user = new User();
            model.addAttribute("user", user);
            return findPaginated(1, "name", "asc", model, keyword);
        }
        return findPaginated(1, "name", "asc", model, "++");
    }

    @GetMapping("/homePage")
    public String viewHomePageList(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return findPaginated(1, "name", "asc", model, null);
    }

    @GetMapping("/addVolunteerOS")
    public String addVolunteerOS(Model model) {
        Volunteer volunteer = new Volunteer();
        model.addAttribute("Volunteer", volunteer);
        List<Departments> department = volunteerService.getDepartments();
        model.addAttribute("Department", department);
        return "AddVolunteerOS";
    }

    @PostMapping("/saveVolunteer")
    public String saveVolunteer(@ModelAttribute("Volunteer") Volunteer volunteer) {
        // save Volunteer to DB
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        if (volunteer.getArrival_status() != null && (volunteer.getArrival_status().equalsIgnoreCase("Arrived") || (volunteer.getArrival_status().equalsIgnoreCase("Up/Down")))){
            volunteer.setArrival_ts(date);
        } else if (volunteer.getArrival_status() != null && volunteer.getArrival_status().equalsIgnoreCase("Departed")){
            Volunteer existingVolunteer = volunteerService.getVolunteerBySCId(volunteer.getId());
            volunteer.setArrival_ts(existingVolunteer.getArrival_ts());
            volunteer.setDeparture_ts(date);
        }
        volunteerService.saveVolunteer(volunteer);
        return "redirect:/homePage";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Volunteer volunteer = volunteerService.getVolunteerBySCId(id);
        model.addAttribute("Volunteer", volunteer);
        List<Departments> listDepartment = volunteerService.getDepartments();
        model.addAttribute("listDepartment", listDepartment);
        return "UpdateVolunteerOS";
    }

    @GetMapping("/deleteVolunteer/{id}")
    public String deleteVolunteer(@PathVariable(value = "id") long id) {
        this.volunteerService.deleteVolunteerBySCId(id);
        return "redirect:/homePage";
    }


    @GetMapping("/volunteerSummary")
    public String volunteerSummary(Model model) {
        List<VolunteerSummary> volunteers = volunteerService.getSummary();
        model.addAttribute("volunteers",volunteers);

        List<OverallSummary> OSvolunteers = volunteerService.getOverallSummary();
        model.addAttribute("OSvolunteers", OSvolunteers);

        return "volunteerSummary";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam(value = "sortSeq", defaultValue = "ASC") String sortSeq,
                                Model model, String keyword) {

        int pageSize = 50;

        Page<Volunteer> page;
        if (StringUtils.isEmpty(keyword)) {
            page = volunteerService.findPaginated(pageNo, pageSize, sortField, sortSeq);
        } else {
            page = volunteerService.findPaginated2(keyword, pageNo, pageSize = 500, sortField, sortSeq);
        }

        List<Volunteer> listVolunteers = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortSeq", sortSeq);
        model.addAttribute("toggleSortSeq", sortSeq.equals("asc") ? "ASC" : "DESC");

        model.addAttribute("listVolunteers", listVolunteers);
        return "index";
    }

    @GetMapping(value="/downloadTemplate")
    public static void exportDataFromSearch(String query, String fileName) throws Exception {
        // Connect to the database
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/VolunteerRegistration", "postgres", "password");

        // Execute the search query

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // Create a new Excel workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a new sheet in the workbook
        Sheet sheet = workbook.createSheet("Data");

        // Create a new row in the sheet
        Row row = sheet.createRow(0);

        // Create new cells in the row
        Cell cell1 = row.createCell(0);
        Cell cell2 = row.createCell(1);

        // Set the values of the cells
        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            row.createCell(i-1).setCellValue(metadata.getColumnName(i));
        }

        // Iterate through the result set and add the data to the sheet
        int rowNum = 1;
        while (rs.next()) {
            row = sheet.createRow(rowNum++);
            for (int i = 1; i <= columnCount; i++) {
                row.createCell(i-1).setCellValue(rs.getString(i));
            }
        }

        // Write the workbook to a file
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        // Close the workbook and the database connection
        workbook.close();
        conn.close(); 
    }
}

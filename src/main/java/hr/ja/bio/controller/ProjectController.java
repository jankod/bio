package hr.ja.bio.controller;

import hr.ja.bio.conf.BioConfig;
import hr.ja.bio.form.FileUploadForm;
import hr.ja.bio.form.ProjectForm;
import hr.ja.bio.model.Project;
import hr.ja.bio.model.SampleFile;
import hr.ja.bio.model.util.FileTypeEnum;
import hr.ja.bio.parser.FilesParser;
import hr.ja.bio.parser.ParseSampleFileException;
import hr.ja.bio.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller()
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectService projectService;
    private BioConfig config;


    @Autowired
    public ProjectController(BioConfig config) {
        this.config = config;
    }

    @GetMapping("/addProject")
    public String showAddProject(Model model) {
        model.addAttribute("projectForm", new ProjectForm());
        return "projects/new_project.html";
    }

    @PostMapping("addProject")
    public String saveForm(@ModelAttribute("projectForm") ProjectForm form) {

        Project p = new Project();
        p.setName(form.getName());


        return "redirect:/projects/list";
    }

    @GetMapping("list")
    public String projectList(Model model) {
        List<Project> projects = projectService.getMyProjects();
        model.addAttribute("projects", projects);
        log.debug("evo list projects");

        return "projects/list_projects";
    }


    @GetMapping("/upload-files")
    public String showFileuploadForm() {
        return "/projects/upload-files";
    }


    @PostMapping(value = "/save-files")
    public String fileSave(@ModelAttribute("uploadForm") FileUploadForm uploadForm,
                           Model map, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
        String saveDirectory = "c:/tmp/";

        saveDirectory = config.getUploadDir();
        log.debug("save directory  {}", saveDirectory);

        List<MultipartFile> files = uploadForm.getFiles();

        List<String> fileNames = new ArrayList<>();

        if (null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {

                String fileName = multipartFile.getOriginalFilename();
                if (!"".equalsIgnoreCase(fileName)) {


                    try {
                        List<String> lines = FilesParser.getLines(multipartFile.getResource());
                        FileTypeEnum type = FilesParser.detectType(lines);




                       // sampleFile.setFileName(fileName);
                        File newFile = new File(saveDirectory + "/" + fileName);


                        log.debug("save file  to: {}", newFile);
                        multipartFile.transferTo(newFile);
                        fileNames.add(fileName);

                    } catch (ParseSampleFileException e) {
                        log.debug("", e);
                        map.addAttribute("errorMgs", e.getMessage());
                        return "redirect:projects/messages";
                    }



                }
            }
        }
        redirectAttributes.addFlashAttribute("Uspjesno uplodano sve " + fileNames);
        map.addAttribute("files", fileNames);

        return "projects/messages";

    }

}

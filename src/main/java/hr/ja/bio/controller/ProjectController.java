package hr.ja.bio.controller;

import hr.ja.bio.conf.BioConfig;
import hr.ja.bio.form.FileUploadForm;
import hr.ja.bio.form.ProjectForm;
import hr.ja.bio.model.Project;
import hr.ja.bio.model.SampleFile;
import hr.ja.bio.model.User;
import hr.ja.bio.parser.ParserUtil;
import hr.ja.bio.parser.ParseSampleFileException;
import hr.ja.bio.parser.old.TaxonomyAbundanceParser;
import hr.ja.bio.parser.old.TaxonomyAbundanceResult;
import hr.ja.bio.repository.ProjectMemberRepository;
import hr.ja.bio.repository.ProjectRepository;
import hr.ja.bio.repository.SampleFileRepository;
import hr.ja.bio.repository.SampleRepository;
import hr.ja.bio.security.SecurityUtils;
import hr.ja.bio.service.ProjectService;
import hr.ja.bio.parser.model.SampleFileType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	SampleRepository sampleRepository;

	@Autowired
	SampleFileRepository sampleFileRepository;

	@Autowired
	ProjectMemberRepository projectMemberRepository;

	private BioConfig config;

	@Autowired
	public ProjectController(BioConfig config) {
		this.config = config;
	}

	@GetMapping("")
	public String listProjects(Model model) {
		User user = SecurityUtils.getCurrentUser();
		// log.debug("Current user {}", user);
		List<Project> projects = projectService.getProjectsFromUser(user);
		model.addAttribute("projects", projects);
		// log.debug("evo list projects {}", projects);

		return "projects/list_projects";
	}

	@GetMapping("/new-project")
	public String newProject(Model model) {
		model.addAttribute("projectForm", new ProjectForm());

		return "projects/new_project.html";
	}

	@PostMapping("")
	public String processProject(@Valid @ModelAttribute("projectForm") ProjectForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.debug("Imam erroroe " + bindingResult.toString());
			return "projects/new_project.html";
		}

		projectService.saveProject(form);

		return "redirect:/projects";
	}

	@GetMapping("{projectId}/upload-files")
	public String showFileuploadForm(@PathVariable Long projectId, Model model) {
		Project project = projectRepository.getOne(projectId);

		// model.addAttribute("file_types", SampleFileType.values() );
		model.addAttribute("project", project);

		return "/projects/upload-files";
	}

	@PostMapping(value = "/new-files")
	public String fileSave(@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map,
			RedirectAttributes redirectAttributes, BindingResult bindingResult)
			throws IllegalStateException, IOException {

		Project project = projectRepository.getOne(uploadForm.getProjectId());
		if (project == null) {
			throw new RuntimeException("Project not find with id " + uploadForm.getProjectId());
		}

		// TODO check project of user

		List<MultipartFile> files = uploadForm.getFiles();

		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {
				String fileName = multipartFile.getOriginalFilename();
				byte[] fileBytes = IOUtils.toByteArray(multipartFile.getInputStream());
				SampleFile sampleFile = new SampleFile();
				sampleFile.setProject(project);
				sampleFile.setFile(fileBytes);
				sampleFile.setType(uploadForm.getFileType());
				sampleFile.setFileName(fileName);
				sampleFileRepository.save(sampleFile);
				log.debug("Save {}", fileName);
			}
		}
		redirectAttributes.addFlashAttribute("message", "Uspjesno uplodano sve ");
		// map.addAttribute("files", fileNames);
		return "redirect:/projects/messages";
	}

	@GetMapping("/messages")
	public String message() {
		return "projects/messages.html";
	}

}

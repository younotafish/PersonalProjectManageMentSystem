package io.cunjisong.ppmtool.web;

import io.cunjisong.ppmtool.domain.Project;
import io.cunjisong.ppmtool.services.MapValidationErrorService;
import io.cunjisong.ppmtool.services.ProjectService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
  @Autowired
  private ProjectService projectService;
  @Autowired
  private MapValidationErrorService mapValidationErrorService;

  @PostMapping("")
  public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,
      BindingResult result){
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if(errMap != null) {
      return  errMap;
    }

    Project project1 = projectService.saveOrUpdateProject(project);
    return  new ResponseEntity<Project>(project, HttpStatus.CREATED);
  }

  @GetMapping("/{projectId}")
  public  ResponseEntity<?> getProjectById(@PathVariable String projectId){
    Project project = projectService.findProjectByIdentifier(projectId.toUpperCase());
    return  new ResponseEntity<Project>(project,HttpStatus.OK);
  }

  @GetMapping("/all")
  public Iterable<Project> getAllProjects(){
    return projectService.findAllProjects();
  }
  @DeleteMapping("/{projectId}")
  public ResponseEntity<?> deleteProject(@PathVariable String projectId){
    projectService.deleteProjectByIdentifier(projectId);
    return new ResponseEntity<String>("Project with id" + projectId+ "  was delected",HttpStatus.OK);
  }
}

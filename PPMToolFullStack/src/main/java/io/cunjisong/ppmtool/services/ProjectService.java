package io.cunjisong.ppmtool.services;

import io.cunjisong.ppmtool.domain.Project;
import io.cunjisong.ppmtool.exceptions.ProjectIdException;
import io.cunjisong.ppmtool.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
  @Autowired
  private ProjectRepository projectRepository;


  public Project saveOrUpdateProject(Project project){
    try {
      project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
      return projectRepository.save(project);
    }
    catch (Exception e){
      throw new ProjectIdException("Project ID " + project.getProjectIdentifier().toUpperCase() + " already exists");
    }

  }

  public Project findProjectByIdentifier(String projectId){

    //Only want to return the project if the user looking for it is the owner

    Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

    if(project == null){
      throw new ProjectIdException("Project ID '"+projectId+"' does not exist");

    }

    return project;
  }

  public Iterable<Project> findAllProjects(){
    return projectRepository.findAll();
  }

  public void deleteProjectByIdentifier(String projectId){
    Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
    if(project == null){
      throw new ProjectIdException("Cannot delete project with id " + projectId +". This project does not exist");
    }
    projectRepository.delete(project);
  }


}

package delivery.pipeline.ui.project;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import delivery.pipeline.ui.common.Constants;
import delivery.pipeline.ui.common.RestTemplateService;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by kim on 2017-08-01.
 */
@Service
public class ProjectService {

    private final Logger LOGGER = getLogger(getClass());

    private static final String REQ_URL_Inspection = "/projects";
    private static final String REQ_URL_Common = "/project";

    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new Project service.
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public ProjectService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    /**
     * project 리스트
     *
     * @param project the project
     * @return Project project list
     */
    public List getProjectList(Project project) {
        String param = "";
        if (project != null) {
            param += "?";
            param += "serviceInstancesId=" + project.getServiceInstancesId() + "&";
            param += "projectId=" + project.getProjectId() + "&";
            param += "projectKey=" + project.getProjectKey() + "&";
            param += "projectName=" + project.getProjectName();
        }


        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/projectList" + param, HttpMethod.GET, null, List.class);
    }

    /**
     * pipelineId에 속한 project
     *
     * @param project the project
     * @return Project project
     */
    public List getProject(Project project) {
        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/getProject", HttpMethod.POST, project, List.class);
    }


    /**
     * project create
     *
     * @param project the project
     * @return Project project
     */
    public Project createProjects(Project project) {
        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/projectsCreate", HttpMethod.POST, project, Project.class);
    }

    /**
     * project delete
     *
     * @param project the project
     * @return Project project
     */
    public Project deleteProjects(Project project) {
        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/projectsDelete", HttpMethod.POST, project, Project.class);
    }

    /**
     * project update
     *
     * @param project the project
     * @return project project
     */
    public Project updateProjects(Project project) {
        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/projectsUpdate", HttpMethod.POST, project, Project.class);
    }

    /**
     * projectid로 projectKey 가져오기
     *
     * @param project the project
     * @return project project key
     */
    public Project getProjectKey(Project project) {
        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/projectKey", HttpMethod.POST, project, Project.class);
    }


    /**
     * qualityGate Linked
     *
     * @param project the project
     * @return project project
     */
    public Project qualityGateProjectLiked(Project project) {
        LOGGER.info("Likend ProjectKey : " + project.getProjectKey());
        LOGGER.info("Likend ProjectId : " + project.getProjectId());
        LOGGER.info("Likend QualityGateId : " + project.getQualityGateId());

        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/qualityGateProjectLiked", HttpMethod.POST, project, Project.class);
    }

    /**
     * qualityProfile Linked
     *
     * @param project the project
     * @return project project
     */
    public Project qualityProfileProjectLinked(Project project) {
        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/qualityProfileProjectLinked", HttpMethod.POST, project, Project.class);
    }

    ///////////////////////////////////

    /**
     * getProject sonarKey(uuid)
     *
     * @param project the project
     * @return List project sonar key
     */
    public List getProjectSonarKey(Project project) {
        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/getProjectSonarKey?projectKey=" + project.getProjectKey(), HttpMethod.GET, null, List.class);
    }

    /**
     * quality Management
     *
     * @param project the project
     * @return project project
     */
    public Project qualityManagementList(Project project) {
        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/qualityManagementList?uuid=" + project.getUuid(), HttpMethod.GET, null, Project.class);
    }

    /**
     * quality coverage
     *
     * @param project the project
     * @return project list
     */
    public List<Project> qualityCoverageList(Project project) {
        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/qualityCoverageList?resource=" + project.getResource(), HttpMethod.GET, null, List.class);
    }

    /**
     * tests source
     *
     * @param project the project
     * @return List list
     */
    public List testsSourceList(Project project) {
        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/testsSource?projectKey=" + project.getProjectKey() + "&metrics=" + project.getMetrics(), HttpMethod.GET, null, List.class);
    }

    /**
     * tests source
     *
     * @param project the project
     * @return Project project
     */
    public Project testsSourceShow(Project project) {
        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/testsSourceShow?key=" + project.getProjectKey() + "&uuid=" + project.getUuid(), HttpMethod.GET, null, Project.class);
    }


    /**
     * Get project detail project.
     *
     * @param id the id
     * @return the project
     */
    Project getProjectDetail(int id) {
        return restTemplateService.send(Constants.TARGET_COMMON_API, REQ_URL_Common + "/" + id, HttpMethod.GET, null, Project.class);
    }


    /**
     * Set update project.
     *
     * Project Link / UnLink
     *
     * @param project the project
     * @return the update project
     */
    public Project setUpdateProject(Project project) {

        return restTemplateService.send(Constants.TARGET_INSPECTION_API, REQ_URL_Inspection + "/setUpdateProject", HttpMethod.POST, project, Project.class);
    }
}

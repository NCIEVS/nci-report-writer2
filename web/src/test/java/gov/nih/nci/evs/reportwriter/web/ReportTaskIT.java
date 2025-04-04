package gov.nih.nci.evs.reportwriter.web;

import gov.nih.nci.evs.reportwriter.web.support.ReportTaskUI;
import gov.nih.nci.evs.reportwriter.web.support.ReportTemplateUI;
import gov.nih.nci.evs.reportwriter.web.support.RunReportTemplateInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReportTaskIT {
    private List<Integer> TEST_TEMPLATE_IDS = Arrays.asList(120);
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test() {
        ResponseEntity<ArrayList<ReportTemplateUI>> response = restTemplate.exchange(String.format("http://localhost:%d/ncreportwriter/reportwriter/reporttemplates", port), HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<ReportTemplateUI>>() {
        });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        RunReportTemplateInfo runReportTemplateInfo = new RunReportTemplateInfo();
        runReportTemplateInfo.setReportTemplates(response.getBody().stream().filter(template -> TEST_TEMPLATE_IDS.contains(template.getId())).collect(Collectors.toCollection(ArrayList::new)));
        runReportTemplateInfo.setDatbaseType("monthly");
        HttpEntity<RunReportTemplateInfo> httpEntity = new HttpEntity<RunReportTemplateInfo>(runReportTemplateInfo);
        ResponseEntity<ArrayList<ReportTaskUI>> reportTasksResponse = restTemplate.exchange(String.format("http://localhost:%d/ncreportwriter/reportwriter/runReportTemplates", port), HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ArrayList<ReportTaskUI>>() {
        });
        assertEquals(HttpStatus.OK, reportTasksResponse.getStatusCode());
        ArrayList<ReportTaskUI> tasks = reportTasksResponse.getBody();
        assertEquals(1, tasks.size());
        await().atMost(5, TimeUnit.DAYS).pollDelay(1, TimeUnit.MINUTES).pollInterval(1, TimeUnit.MINUTES).until(() -> completed(tasks));
    }

    private boolean completed(ArrayList<ReportTaskUI> reportTaskUIs) {
        List<Integer> taskIds = reportTaskUIs.stream().map(ReportTaskUI::getId).collect(Collectors.toList());
        ResponseEntity<ArrayList<ReportTaskUI>> response = restTemplate.exchange(String.format("http://localhost:%d/ncreportwriter/reportwriter/reporttasks", port), HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<ReportTaskUI>>() {
        });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ReportTaskUI> filteredTasks = response.getBody().stream().filter(task -> taskIds.contains(task.getId())).collect(Collectors.toList());
        assertEquals(taskIds.size(), filteredTasks.size());
        List<ReportTaskUI> completedTasks = filteredTasks.stream().filter(task -> StringUtils.isNotBlank(task.getDateCompleted())).collect(Collectors.toList());
        completedTasks.forEach(task -> System.out.println(task.getId() + " " + task.getDateCompleted()));
        return taskIds.size() == completedTasks.size();
    }
}

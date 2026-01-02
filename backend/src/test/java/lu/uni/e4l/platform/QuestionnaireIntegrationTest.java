package lu.uni.e4l.platform;

import lu.uni.e4l.platform.model.Question;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.*;

public class QuestionnaireIntegrationTest {

    @Test
    public void staging_questionnaireEndpoint_returnsNonEmptyQuestionList() {
        String baseUrl = "http://host.docker.internal:3001/e4lapi";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Question>> qResp;
        try {
            qResp = restTemplate.exchange(
                    baseUrl + "/questionnaire",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Question>>() {}
            );
        } catch (HttpStatusCodeException e) {
            fail("Failed to fetch questionnaire from " + baseUrl + "/questionnaire"
                    + " | status=" + e.getStatusCode()
                    + " | body=" + e.getResponseBodyAsString());
            return;
        } catch (ResourceAccessException e) {
            fail("Failed to fetch questionnaire from " + baseUrl + "/questionnaire"
                    + " | connection error: " + e.getMessage()
                    + " (is staging running and reachable from this runner?)");
            return;
        } catch (Exception e) {
            fail("Failed to fetch questionnaire from " + baseUrl + "/questionnaire"
                    + " | error=" + e.getClass().getName() + ": " + e.getMessage());
            return;
        }

        assertEquals("Questionnaire endpoint must return 200 OK", HttpStatus.OK, qResp.getStatusCode());
        assertNotNull("Questionnaire response body must not be null", qResp.getBody());
        assertFalse("Questionnaire response body must not be empty", qResp.getBody().isEmpty());
    }
}

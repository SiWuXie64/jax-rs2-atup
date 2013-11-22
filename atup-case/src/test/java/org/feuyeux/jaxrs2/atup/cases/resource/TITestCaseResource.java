package org.feuyeux.jaxrs2.atup.cases.resource;

import org.feuyeux.jaxrs2.atup.core.constant.AtupApi;
import org.feuyeux.jaxrs2.atup.core.info.AtupTestCaseInfo;
import org.feuyeux.jaxrs2.atup.core.info.AtupTestCaseListInfo;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TITestCaseResource extends JerseyTest {
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(AtupTestCaseResource.class);
    }

    @Test
    public void testGetList() {
        WebTarget webTarget = target(AtupApi.TEST_CASE_PATH).path("cases").queryParam("start", 0).queryParam("size", 100);
        Invocation.Builder request = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        AtupTestCaseListInfo result = request.get(AtupTestCaseListInfo.class);
        List<AtupTestCaseInfo> testCaseList = result.getTestCaseList();
        if (testCaseList != null && testCaseList.size() > 0) {
            Assert.assertNotNull(testCaseList.get(0).getCaseId());
        } else {
            Assert.assertTrue(testCaseList == null || result.getErrorInfo() != null);
        }
    }
}
package com.tmobile.cloud.azurerules.sqlDatabase;

import static com.tmobile.cloud.gcprules.utils.TestUtils.getHitsJsonArrayForVMPublicAccess;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.google.common.collect.HashMultimap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tmobile.cloud.awsrules.utils.RulesElasticSearchRepositoryUtil;
import com.tmobile.pacman.commons.PacmanSdkConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tmobile.cloud.awsrules.utils.CommonTestUtils;
import com.tmobile.cloud.awsrules.utils.PacmanUtils;
import com.tmobile.cloud.azurerules.SQLDatabase.CheckThreatDetectionRule;
import com.tmobile.pacman.commons.exception.InvalidInputException;
import com.tmobile.pacman.commons.exception.RuleExecutionFailedExeption;
import com.tmobile.pacman.commons.policy.BasePolicy;

import java.util.HashMap;

@PowerMockIgnore({ "javax.net.ssl.*", "javax.management.*" })
@RunWith(PowerMockRunner.class)
@PrepareForTest({ PacmanUtils.class, BasePolicy.class, RulesElasticSearchRepositoryUtil.class })
public class CheckThreatDetectionRuleTest {
        @InjectMocks
        CheckThreatDetectionRule checkThreadDetection;
        public  JsonObject getFailureJsonArrayForThreadDetection() {
                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("hits", gson.fromJson("{\n    \"hits\": [\n        {\n            \"_source\": {\n                \"discoverydate\": \"2022-06-28 06:00:00+0000\",\n                \"_cloudType\": \"Azure\",\n                \"subscription\": \"f4d319d8-7eac-4e15-a561-400f7744aa81\",\n                \"region\": \"centralus\",\n                \"subscriptionName\": \"dev-paladincloud\",\n                \"resourceGroupName\": \"dev-paladincloud\",\n                \"id\": \"subscriptions/f4d319d8-7eac-4e15-a561-400f7744aa81/resourceGroups/dev-paladincloud/providers/Microsoft.Network/networkSecurityGroups/testing-nsg\",\n                \"key\": \"ccb7e20e-47c3-478b-a960-580c7a6b9d1e\",\n                \"name\": \"testing-nsg\",\n                \"tags\": {},\n                \"excludedDetectionTypes\": [\n                    \"Access_Anomaly\",\n                    \"Data_Exfiltration\",\n                    \"Unsafe_Action\"\n                ]\n            }\n        }\n    ]\n}", JsonElement.class));
                return jsonObject;
        }
        public  JsonObject getHitJsonArrayForThreadDetection() {
                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("hits", gson.fromJson("{\n    \"hits\": [\n        {\n            \"_source\": {\n                \"discoverydate\": \"2022-06-28 06:00:00+0000\",\n                \"_cloudType\": \"Azure\",\n                \"subscription\": \"f4d319d8-7eac-4e15-a561-400f7744aa81\",\n                \"region\": \"centralus\",\n                \"subscriptionName\": \"dev-paladincloud\",\n                \"resourceGroupName\": \"dev-paladincloud\",\n                \"id\": \"subscriptions/f4d319d8-7eac-4e15-a561-400f7744aa81/resourceGroups/dev-paladincloud/providers/Microsoft.Network/networkSecurityGroups/testing-nsg\",\n                \"key\": \"ccb7e20e-47c3-478b-a960-580c7a6b9d1e\",\n                \"name\": \"testing-nsg\",\n                \"tags\": {},\n                \"excludedDetectionTypes\": [\n           \n                ]\n            }\n        }\n    ]\n}", JsonElement.class));


                return jsonObject;
        }

        @Test
        public void executeSucessTest() throws Exception {
                mockStatic(PacmanUtils.class);
                mockStatic(RulesElasticSearchRepositoryUtil.class);
                when(PacmanUtils.doesAllHaveValue(anyString(), anyString()))
                                .thenReturn(
                                                true);
                when(RulesElasticSearchRepositoryUtil.getQueryDetailsFromES(anyString(),anyObject(),
                        anyObject(),
                        anyObject(), anyObject(), anyInt(), anyObject(), anyObject(), anyObject())).thenReturn(getHitJsonArrayForThreadDetection());
                assertThat(checkThreadDetection.execute(CommonTestUtils.getMapString("r_123 "),
                        CommonTestUtils.getMapString("r_123 ")).getStatus(), is(PacmanSdkConstants.STATUS_SUCCESS));

        }
        @Test
        public void executeFailureTest() throws Exception {
                mockStatic(PacmanUtils.class);
                mockStatic(RulesElasticSearchRepositoryUtil.class);
                when(PacmanUtils.doesAllHaveValue(anyString(), anyString()))
                        .thenReturn(
                                true);
                when(RulesElasticSearchRepositoryUtil.getQueryDetailsFromES(anyString(),anyObject(),
                        anyObject(),
                        anyObject(), anyObject(), anyInt(), anyObject(), anyObject(), anyObject())).thenReturn(getFailureJsonArrayForThreadDetection());
                assertThat(checkThreadDetection.execute(CommonTestUtils.getMapString("r_123 "),
                        CommonTestUtils.getMapString("r_123 ")).getStatus(), is(PacmanSdkConstants.STATUS_FAILURE));

        }

        @Test
        public void getHelpTextTest() {
                assertThat(checkThreadDetection.getHelpText(), is(notNullValue()));
        }



}

/*
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package openshift.cop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiDocumentationTest {

  @Rule
  public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
        .apply(documentationConfiguration(this.restDocumentation)).build();
  }

  @Test
  public void greetingV1() throws Exception {
    this.mockMvc
        .perform(
            get("/v1/greeting")
                .header("Origin", "*")
        )
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("Hello, World!")))
        .andDo(
            document("greeting-v1",
                responseFields(
                    fieldWithPath("id").description("The number of greetings that have been requested.").type(JsonFieldType.NUMBER),
                    fieldWithPath("content").description("The content of the greeting.").type(JsonFieldType.STRING)
                )
            )
        );
  }

  @Test
  public void hostinfoV1() throws Exception {
    this.mockMvc
        .perform(
            get("/v1/hostinfo")
                .header("Origin", "*")
        )
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(anything()))
        .andDo(
            document("hostname-v1",
                responseFields(
                    fieldWithPath("hostname").description("The instance's hostname.").type(JsonFieldType.STRING)
                )
            )
        );
  }

  @Test
  public void envInfoV1() throws Exception {
    this.mockMvc
        .perform(
            get("/v1/envinfo")
                .param("filter", "*")
                .header("Origin", "*")
        )
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(anything()))
        .andDo(
            document("envinfo-v1",
                requestParameters(
                    parameterWithName("filter")
                        .description("A filter for the environment variables. Use * for all variables.")
                        .optional()
                )
            )
        );
  }

}

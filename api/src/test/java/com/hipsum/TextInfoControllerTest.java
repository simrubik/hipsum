package com.hipsum;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hipsum.api.controller.TextInfoController;
import com.hipsum.service.KafkaProducerService;
import com.hipsum.service.TextInfo;
import com.hipsum.service.TextInfoService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TextInfoController.class)
@ContextConfiguration(classes = TestConfig.class)
class TextInfoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private KafkaProducerService kafkaProducerService;

  @MockBean
  private TextInfoService textInfoService;

  @ParameterizedTest
  @ValueSource(strings = {"/api/v1/betvictor/text"})
  void getRetrievedTextInfo_successful(String endpoint) throws Exception {
    TextInfo textInfo = new TextInfo("text_message", 1L, 1L, 1L);
    when(textInfoService.getTextInfo(1)).thenReturn(textInfo);

    mockMvc.perform(get(endpoint)
        .param("paragraphNumber", "1")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.freq_word").value("text_message"))
        .andExpect(jsonPath("$.avg_paragraph_size").value(1))
        .andExpect(jsonPath("$.avg_paragraph_processing_time").value(1))
        .andExpect(jsonPath("$.total_processing_time").value(1));

    verify(textInfoService).getTextInfo(1);
  }
}

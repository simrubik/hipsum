package com.hipsum.api.controller;

import com.hipsum.api.dto.RetrievedTextInfoResponseDto;
import com.hipsum.service.TextInfo;
import org.springframework.stereotype.Component;

@Component
public class TextInfoMapper {

  public RetrievedTextInfoResponseDto toRetrievedTextInfoResponseDto(TextInfo textInfo) {
    return new RetrievedTextInfoResponseDto().freqWord(textInfo.getFrequentWord())
        .avgParagraphSize(textInfo.getAvgParagraphSize())
        .avgParagraphProcessingTime(textInfo.getAvgParagraphProcessingTime())
        .totalProcessingTime(textInfo.getTotalProcessingTime());
  }

}

package com.hipsum.api.controller;

import com.hipsum.api.dto.RetrievedTextInfoResponseDto;
import com.hipsum.service.TextInfo;
import com.hipsum.service.TextInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/betvictor")
@RequiredArgsConstructor
public class TextInfoController implements TextApi {

  private final TextInfoService textInfoService;
  private final TextInfoMapper textInfoMapper;

  @Override
  public ResponseEntity<RetrievedTextInfoResponseDto> getRetrievedTextInfo(Integer paragraphNumber)
      throws Exception {
    TextInfo textInfo = textInfoService.getTextInfo(paragraphNumber);
    RetrievedTextInfoResponseDto responseDto = textInfoMapper
        .toRetrievedTextInfoResponseDto(textInfo);
    return ResponseEntity.ok(responseDto);
  }

}

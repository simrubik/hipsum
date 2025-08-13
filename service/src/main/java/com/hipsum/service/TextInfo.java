package com.hipsum.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TextInfo {

  private String frequentWord;

  private Long avgParagraphSize;

  private Long avgParagraphProcessingTime;

  private Long totalProcessingTime;

}

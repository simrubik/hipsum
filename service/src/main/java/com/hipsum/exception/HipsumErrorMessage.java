package com.hipsum.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HipsumErrorMessage {

  private String message;

  private String errorCode;

}

package com.urgentneed.godmode.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneralResponse {

    boolean status;
    int statusCode;
    String message;
    Object data;
}

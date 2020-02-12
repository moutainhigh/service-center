package com.shengsu.any.clue.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ClueBuyVo implements Serializable {
    @NotBlank
    private String clueId;
    private String userId;
    private String token;
}

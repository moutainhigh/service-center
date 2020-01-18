package com.shengsu.any.clue.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class ClueShelfVo implements Serializable {
    @NotBlank
    private String clueId;
}

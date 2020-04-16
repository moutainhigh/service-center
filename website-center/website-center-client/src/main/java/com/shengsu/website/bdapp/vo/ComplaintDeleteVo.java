package com.shengsu.website.bdapp.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-16 15:05
 **/
@Data
public class ComplaintDeleteVo  implements Serializable {
    @NotBlank
    private String complaintId;
}

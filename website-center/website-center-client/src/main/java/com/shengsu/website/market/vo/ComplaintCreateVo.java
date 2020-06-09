package com.shengsu.website.market.vo;

import com.shengsu.website.market.entity.ComplaintAppendix;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-16 09:12
 **/
@Data
public class ComplaintCreateVo implements Serializable {
    @NotBlank
    private String url;
    @NotBlank
    private String name;
    @NotBlank
    private String tel;
    @NotBlank
    private String appeal;
    @NotBlank
    private String complaintType;
    private List<ComplaintAppendix> complaintAppendices = new ArrayList<>();//附件
}

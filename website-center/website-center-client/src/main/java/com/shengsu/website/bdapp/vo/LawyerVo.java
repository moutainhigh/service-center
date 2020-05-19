package com.shengsu.website.bdapp.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-10 16:36
 **/
@Data
public class LawyerVo implements Serializable {
    /**
     * 用户id
     */
    @NotBlank
    private String lawyerId;
}

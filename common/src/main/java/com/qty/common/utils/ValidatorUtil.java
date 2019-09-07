package com.qty.common.utils;

import org.springframework.validation.BindingResult;

public class ValidatorUtil {

    //统一参数校验工具
    public static String checkResult(BindingResult result){
        StringBuilder sb=new StringBuilder("");
        if (result!=null&&result.hasErrors()){
            result.getAllErrors().stream().forEach(objectError -> sb.append(objectError.getDefaultMessage()).append("\n"));
        }
        return sb.toString();
    }
}

package com.qty.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public abstract class AbstractController {
    //日志
    protected Logger log= LoggerFactory.getLogger(AbstractController.class);
}

package com.demo.web.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * Home controller
 *
 * @version Revision History
 * <pre>
 * Author   Version     Date            Changes
 * pankplee  1.0         9/20/2017         Created
 * </pre>
 * @since 1.0
 */
@Controller
@RequestMapping("/")
public class HomeController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RequestMapping(value = "/showBeans", method = RequestMethod.GET)
    @ResponseBody
    public String showBeans() {
        return Arrays.asList(applicationContext.getBeanDefinitionNames()).toString() + "\n"
                + Arrays.asList(applicationContext.getParent().getBeanDefinitionNames()).toString();
    }

    @RequestMapping
    public String home() {
        return "redirect:/user";
    }
}

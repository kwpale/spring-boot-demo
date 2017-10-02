package com.demo.web.controller;

import com.demo.base.UserType;
import com.demo.entity.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * User Controller
 *
 * @version Revision History
 * <pre>
 * Author   Version     Date            Changes
 * pankplee  1.0         9/20/2017         Created
 * </pre>
 * @since 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @ModelAttribute("allUserTypes")
    public List<UserType> populateTypes() {
        return Arrays.asList(UserType.ALL);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/create/{id:\\d*}", method = RequestMethod.GET)
    @ResponseBody
    public String create(@PathVariable Long id) {
        User user = userService.create("Lee."+ id+"@demo.com", "demo", UserType.USER);
        return user.toString();
    }

    @RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
    public String show(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "user/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String postForm(@Valid User user, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "user/create";
        }
        userService.create(user);
        model.clear();
        return "redirect:/user";
    }

    @RequestMapping(value = "/delete/{id:\\d*}")
    public String delete(@PathVariable Long id, ModelMap model) {
        userService.remove(id);
        model.clear();
        return "redirect:/user";
    }
}

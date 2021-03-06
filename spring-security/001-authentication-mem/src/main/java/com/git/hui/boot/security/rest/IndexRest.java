package com.git.hui.boot.security.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 20:30 19/12/22.
 */
@RestController
public class IndexRest {

    public String getUserV2() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * 全部都可以访问
     *
     * @return
     */
    @GetMapping(path = {"/"})
    public String index() {
        return "hello this is index! welcome " + getUserV2();
    }

    /**
     * 要求登录，且属于三个角色之一的才能访问
     *
     * @return
     */
    @GetMapping(path = "guest")
    @PreAuthorize("hasAnyRole('guest', 'admin', 'manager')")
    public String guestHello() {
        return "guest hello: " + getUserV2();
    }

    @GetMapping(path = "manager")
    @PreAuthorize("hasAnyRole('admin', 'manager')")
    public String managerHello() {
        return "manager hello: " + getUserV2();
    }

    @GetMapping(path = "admin")
    @PreAuthorize("hasAnyRole('admin')")
    public String adminHello() {
        return "admin hello: " + getUserV2();
    }
}

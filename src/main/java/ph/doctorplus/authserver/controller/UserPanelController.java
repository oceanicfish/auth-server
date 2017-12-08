package ph.doctorplus.authserver.controller;

import org.springframework.web.bind.annotation.*;
import ph.doctorplus.authserver.bean.CustomUserDetails;
import ph.doctorplus.authserver.bean.JsonResponse;
import ph.doctorplus.authserver.service.CustomUserDetailsService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserPanelController {

    CustomUserDetailsService customUserDetailsService;

    @RequestMapping(value = "info")
    @ResponseBody
    public Principal getUserInfo(Principal principal) {
        return principal;
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @ResponseBody
    public String haveATry() {
        return "you are testing me";
    }
}

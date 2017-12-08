package ph.doctorplus.authserver.controller;

import org.springframework.web.bind.annotation.*;
import ph.doctorplus.authserver.bean.CustomUserDetails;
import ph.doctorplus.authserver.bean.JsonResponse;
import ph.doctorplus.authserver.service.CustomUserDetailsService;

@RestController
@RequestMapping(value = "signup")
public class SignUpController {

    CustomUserDetailsService customUserDetailsService;


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse signup(@RequestBody CustomUserDetails userDetails) {
        JsonResponse jsonResponse = customUserDetailsService.createUser(userDetails);
        return jsonResponse;
    }
}

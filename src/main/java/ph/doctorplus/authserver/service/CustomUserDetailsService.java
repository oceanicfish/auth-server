package ph.doctorplus.authserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ph.doctorplus.authserver.bean.CustomUserDetails;
import ph.doctorplus.authserver.bean.JsonResponse;
import ph.doctorplus.authserver.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails userDetails = userRepository.findOneByUsername(username);
		if (userDetails == null) {
			throw new UsernameNotFoundException("Username or Password is not correct");
		}
		return userDetails;
	}


	public JsonResponse createUser(CustomUserDetails userDetails) {

		JsonResponse jsonResponse = new JsonResponse();

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

		try {
			userRepository.save(userDetails);
			jsonResponse.setSuccess(true);
		}catch (Exception e) {
			jsonResponse.setSuccess(false);
			jsonResponse.setErrorMessage(e.getMessage());
		}

		return jsonResponse;
	}
}
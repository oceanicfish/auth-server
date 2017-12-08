package ph.doctorplus.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.doctorplus.authserver.bean.CustomUserDetails;

@Repository
public interface UserRepository extends JpaRepository<CustomUserDetails, Long> {

    CustomUserDetails findOneByUsername(String username);
}
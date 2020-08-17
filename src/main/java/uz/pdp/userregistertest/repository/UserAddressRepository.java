package uz.pdp.userregistertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.userregistertest.entity.UserAddress;

/**
 * created by Muhammad
 * on 14.08.2020
 */

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

}

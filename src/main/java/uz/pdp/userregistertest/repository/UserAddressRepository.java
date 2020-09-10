package uz.pdp.userregistertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.userregistertest.entity.Book;
import uz.pdp.userregistertest.entity.UserAddress;

import java.util.List;

/**
 * created by Muhammad
 * on 14.08.2020
 */

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {
    @Query(nativeQuery = true, value = "select * from user_address o where user_id=:id")
    UserAddress getMyRegionId(@Param("id") Integer id);

}

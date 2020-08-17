package uz.pdp.userregistertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.userregistertest.entity.District;

import java.util.List;

/**
 * created by Muhammad
 * on 12.08.2020
 */

@Repository
public interface DistrictRepo extends JpaRepository<District, Integer> {


    List<District> getByRegionId(Integer id);



}

package uz.pdp.userregistertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.userregistertest.entity.Region;

/**
 * created by Muhammad
 * on 10.08.2020
 */

@Repository
public interface RegionRepo extends JpaRepository<Region, Integer> {
    Region findByName(String name);
}

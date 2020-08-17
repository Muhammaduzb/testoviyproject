package uz.pdp.userregistertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.userregistertest.entity.DefaultImage;

/**
 * created by Muhammad
 * on 16.08.2020
 */
@Repository
public interface DefaultImageRepos extends JpaRepository<DefaultImage, Integer> {
}

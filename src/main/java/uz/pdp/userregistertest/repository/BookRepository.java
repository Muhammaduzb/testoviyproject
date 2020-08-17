package uz.pdp.userregistertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.userregistertest.entity.Book;

import java.util.List;

/**
 * created by Muhammad
 * on 13.08.2020
 */
@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query(nativeQuery = true, value = "select * from book o where user_id=:id")
    List<Book> getMyBooks(@Param("id") Integer id);
}

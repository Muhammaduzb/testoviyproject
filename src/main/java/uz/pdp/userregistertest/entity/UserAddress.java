package uz.pdp.userregistertest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * created by Muhammad
 * on 14.08.2020
 */


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAddress {
    @Id
    private Integer userId;
    private Integer region;
    private Integer district;
}

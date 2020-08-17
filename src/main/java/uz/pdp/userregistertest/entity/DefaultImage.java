package uz.pdp.userregistertest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * created by Muhammad
 * on 16.08.2020
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DefaultImage {
    @Id
    private Integer id;
    @Column(name = "picByte", length = 1000)
    private byte[] picByte;
}

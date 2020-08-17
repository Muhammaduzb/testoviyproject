package uz.pdp.userregistertest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * created by Muhammad
 * on 08.08.2020
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String author;
    private String language;
    private String comment;
    private Timestamp timestamp;
    @Column(name = "picByte", length = 1000)
    private byte[] picByte;
    private Integer userId;
}

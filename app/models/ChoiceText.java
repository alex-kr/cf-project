package models;


import javax.persistence.*;

/**
 * Created by Victor Dichko on 14.09.14.
 */
@Entity
public class ChoiceText {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Column(name="text")
    public String text;
}

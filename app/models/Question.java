package models;

import javax.persistence.*;

/**
 * Created by Victor Dichko on 14.09.14.
 */
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Column(name="question_text")
    public String questionText;
}

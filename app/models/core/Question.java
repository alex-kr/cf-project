package models.core;


import javax.persistence.*;
import java.util.List;

/**
 * Created by Victor Dichko on 14.09.14.
 */
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @Column(name="question_text")
    public String questionText;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    public List<Choice> choices;
}

package models;

import javax.persistence.*;

/**
 * Created by Victor Dichko on 14.09.14.
 */
@Entity
public class Choice {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name="question_id")
    public Question question;

    @ManyToOne
    @JoinColumn(name="choice_text_id")
    public ChoiceText choiceText;

    @Column(name="correct")
    public boolean correct;
}

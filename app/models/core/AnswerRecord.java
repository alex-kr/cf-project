package models.core;


import javax.persistence.*;

/**
 * Created by Victor Dichko on 14.09.14.
 */
@Entity
@Table(name = "answer_record")
public class AnswerRecord {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name="question_id")
    public Question question;

    @ManyToOne
    @JoinColumn(name="choice_id")
    public Choice choice;

    public boolean correct;

    public User getUser() {
        return user;
    }
}

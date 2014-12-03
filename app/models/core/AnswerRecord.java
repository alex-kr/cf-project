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
    @JoinColumn(name="account_id")
    public Account account;

    @ManyToOne
    @JoinColumn(name="question_id")
    public Question question;

    @ManyToOne
    @JoinColumn(name="choice_id")
    public Choice choice;

    public boolean correct;

    public Account getAccount() {
        return account;
    }
}

package models.core;

import controllers.Factory;

import javax.persistence.*;


/**
 * Created by Victor Dichko on 14.09.14.
 */
@Entity
@Table(name = "choice")
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

    public Choice() {}

    public Choice(Question question, ChoiceText choiceText, boolean correct) {
        this.question = question;
        this.choiceText = choiceText;
        this.correct = correct;
    } 

    public void save() {
        try {
            Factory.getInstance().getChoiceDAO().addChoice(this);
        } catch (Exception e) {
            System.err.println("Smth wrong with saving choice into DB");
        }
    }
}

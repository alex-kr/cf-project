package models.core;

import controllers.Factory;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name="rule_id")
    public Rule rule;

    public void save() {
    	try {
			Factory.getInstance().getQuestionDAO().addQuestion(this);
		} catch (Exception e) {
			System.err.println("Smth wrong with saving question into DB");
		}
    }
}

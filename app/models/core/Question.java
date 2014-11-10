package models.core;

import controllers.Factory;

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

    public void save() {
    	try {
			Factory.getInstance().getQuestionDAO().addQuestion(this);
		} catch (Exception e) {
			System.err.println("Smth wrong with saving question into DB");
		}
    }
}

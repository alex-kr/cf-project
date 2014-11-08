package models.core;


import controllers.Factory;

import javax.persistence.*;

/**
 * Created by Victor Dichko on 14.09.14.
 */
@Entity
@Table(name="choice_text")
public class ChoiceText {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Column(name="text")
    public String text;

    public ChoiceText() {}

    public ChoiceText(String text) {
        this.text = text;
    } 

    public void save() {
    	try {
			Factory.getInstance().getChoiceTextDAO().addChoiceText(this);
		} catch (Exception e) {
			System.err.println("Smth wrong with saving choice_text into DB");
		}
    }
}

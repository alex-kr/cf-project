package models.core;

import controllers.Factory;

import javax.persistence.*;



@Entity
@Table(name = "rule")
public class Rule {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @Column(name="rule_text")
    public String ruleText;

    public void save() {
    	try {
			Factory.getInstance().getRuleDAO().addRule(this);
		} catch (Exception e) {
			System.err.println("Smth wrong with saving rule into DB");
		}
    }
}

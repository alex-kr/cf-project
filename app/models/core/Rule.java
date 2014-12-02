package models.core;

import controllers.Factory;

import models.core.Rule;

import javax.persistence.*;

import java.util.Map;
import java.util.LinkedHashMap;



@Entity
@Table(name = "rule")
public class Rule {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @Column(name="rule_text")
    public String ruleText;

    @ManyToOne
    @JoinColumn(name="topic_id")
    public Topic topic;

    public void save() {
    	try {
			Factory.getInstance().getRuleDAO().addRule(this);
		} catch (Exception e) {
			System.err.println("Smth wrong with saving rule into DB");
		}
    }

    public static Map<String, String> getMap() {
        LinkedHashMap<String, String> options = new LinkedHashMap<String,String>();
        try {
            for (Rule rule: Factory.getInstance().getRuleDAO().getAllRules()) {
                options.put(rule.id.toString(), rule.ruleText);
            }
        } catch (Exception e) {
            System.err.println("Smth wrong with getting rule from DB");
        }
        return options;
    }
}

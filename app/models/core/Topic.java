package models.core;

import controllers.Factory;

import javax.persistence.*;


@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @Column(name="topic_text")
    public String topicText;

    public void save() {
    	try {
			Factory.getInstance().getTopicDAO().addTopic(this);
		} catch (Exception e) {
			System.err.println("Smth wrong with saving topic into DB");
		}
    }
}
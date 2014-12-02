package models.core;

import controllers.Factory;

import javax.persistence.*;

import java.util.Map;
import java.util.LinkedHashMap;


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

    public static Map<String, String> getMap() {
        LinkedHashMap<String, String> options = new LinkedHashMap<String,String>();
        try {
            for (Topic topic: Factory.getInstance().getTopicDAO().getAllTopics()) {
                options.put(topic.id.toString(), topic.topicText);
            }
        } catch (Exception e) {
            System.err.println("Smth wrong with getting topic from DB");
        }
        return options;
    }
}
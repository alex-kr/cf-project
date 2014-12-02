package dao;

import models.core.Topic;

import java.sql.SQLException;
import java.util.List;



public interface TopicDAO {
    public void addTopic(Topic topic) throws SQLException;
    public void updateTopic (Long id, Topic topic) throws SQLException;
    public Topic getTopicById(Long id) throws SQLException;
    public List<Topic> getAllTopics() throws SQLException;
    public void deleteTopic(Topic topic) throws SQLException;
}
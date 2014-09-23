package util;

import models.core.ChoiceText;
import models.core.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import play.Logger;
import play.Logger.ALogger;

/**
 * Created by Victor Dichko on 22.09.14.
 */
public class HibernateUtil {

    private static final ALogger logger = Logger.of(HibernateUtil.class);

    private static final SessionFactory sessionFactory;
    static {
        try {
            Configuration config = new Configuration().configure();
            config.addAnnotatedClass(User.class);
            config.addAnnotatedClass(ChoiceText.class);
            sessionFactory = config.buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

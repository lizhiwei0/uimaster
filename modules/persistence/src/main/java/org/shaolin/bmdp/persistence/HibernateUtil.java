package org.shaolin.bmdp.persistence;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.shaolin.bmdp.runtime.AppContext;
import org.shaolin.bmdp.runtime.internal.AppServiceManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

	private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
	
	// thread control session factory by out side.
	private static final ThreadLocal<Session> sessionFactoryTL = new ThreadLocal<Session>();
	
	private static final ThreadLocal<AtomicLong> refCounterTL = new ThreadLocal<AtomicLong>();
	
	@SuppressWarnings("deprecation")
	private synchronized static SessionFactory buildSessionFactory() {
		try {
			Configuration config = new Configuration();
			List<String> loadedPaths = new ArrayList<String>();
			Enumeration<URL> urls1 = AppContext.get().getAppClassLoader().getResources("hbm/");
			while (urls1.hasMoreElements()) {
				URL url = urls1.nextElement();
				String strPath = url.toString();
				if (loadedPaths.contains(strPath)) {
					continue;
				}
				if (logger.isInfoEnabled()) {
					logger.info("loading hibernate mapping file folder: " + strPath);
				}
				loadedPaths.add(strPath);
				
				File path = new File(url.toURI());
				final String files[] = path.list();
				for (final String file : files) {
					config.addResource("hbm/" + file);
				}
			}
			config.configure();
			((AppServiceManagerImpl)AppContext.get()).setHibernateConfiguration(config);
			// Create the SessionFactory from hibernate.cfg.xml
			return config.buildSessionFactory();
		} catch (Throwable ex) {
			logger.error("Error occurrs while initializing the hibernate configuration!!!", ex);
			// Make sure you log the exception, as it might be swallowed
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Configuration getConfiguration() {
		return (Configuration)AppContext.get().getHibernateConfiguration();
	}
	
	public static Session getSession() {
		if (sessionFactoryTL.get() != null) {
			return sessionFactoryTL.get();
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		sessionFactoryTL.set(session);
		return session;
	}
	
	public static void releaseSession(Session session, boolean isCommit) {
		if (sessionFactoryTL.get() != null) {
			 sessionFactoryTL.set(null);
		}
		
		if (!session.isOpen()) {
			return;
		}
		
		if (isCommit) {
			session.getTransaction().commit();
		} else {
			session.getTransaction().rollback();
		}
	}
	
	public static SessionFactory getSessionFactory() {
		if (AppContext.get().getHibernateSessionFactory() == null) {
			((AppServiceManagerImpl)AppContext.get()).setHibernateSessionFactory(buildSessionFactory());
		}
		return (SessionFactory)AppContext.get().getHibernateSessionFactory();
	}

}

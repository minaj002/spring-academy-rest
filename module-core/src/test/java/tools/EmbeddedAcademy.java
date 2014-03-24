package tools;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

public class EmbeddedAcademy {

	public void start() throws Exception {

		Server server = new Server(8080);

		WebAppContext webAppContext = new WebAppContext();

		webAppContext.setResourceBase("src/main/webapp");
		webAppContext.setContextPath("/");
		webAppContext
				.setConfigurations(new Configuration[] { new FixedAnnotationConfig() });
		webAppContext.setParentLoaderPriority(true);

		server.setHandler(webAppContext);
		server.start();
		server.join();

	}

	public static void main(String[] args) throws Exception {
		
		EmbeddedAcademy academy = new EmbeddedAcademy();
		academy.start();
		
	}

}

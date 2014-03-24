package tools;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.annotation.HandlesTypes;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.ClassInheritanceHandler;
import org.eclipse.jetty.annotations.ContainerInitializerAnnotationHandler;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.webapp.WebAppContext;

import com.academy.config.WebAppInitializer;

public class FixedAnnotationConfig extends AnnotationConfiguration {
	private static final Logger LOG = Log.getLogger(AnnotationConfiguration.class);
	
	
	 @Override
	    public void postConfigure(WebAppContext context) throws Exception
	    {
		 
		   ClassInheritanceMap classMap = (ClassInheritanceMap)context.getAttribute(CLASS_INHERITANCE_MAP);
	        List<ContainerInitializer> initializers = (List<ContainerInitializer>)context.getAttribute(CONTAINER_INITIALIZERS);
	        
	        context.removeAttribute(CLASS_INHERITANCE_MAP);
	        if (classMap != null)
	            classMap.clear();
	        
	        context.removeAttribute(CONTAINER_INITIALIZERS);
	        if (initializers != null)
	            initializers.clear();
	        
	        if (_discoverableAnnotationHandlers != null)
	            _discoverableAnnotationHandlers.clear();

	        _classInheritanceHandler = null;
	        if (_containerInitializerAnnotationHandlers != null)
	            _containerInitializerAnnotationHandlers.clear();

	        if (_parserTasks != null)
	        {
	            _parserTasks.clear();
	            _parserTasks = null;
	        }
	        
	        super.postConfigure(context);
	    }
	    
	
	
	
	
	@Override
   public void createServletContainerInitializerAnnotationHandlers (WebAppContext context, List<ServletContainerInitializer> scis)
   throws Exception
   {
       if (scis == null || scis.isEmpty())
           return; // nothing to do

       List<ContainerInitializer> initializers = new ArrayList<ContainerInitializer>();
       context.setAttribute(CONTAINER_INITIALIZERS, initializers);

       for (ServletContainerInitializer service : scis)
       {
           HandlesTypes annotation = service.getClass().getAnnotation(HandlesTypes.class);
           ContainerInitializer initializer = null;
           if (annotation != null)
           {    
               //There is a HandlesTypes annotation on the on the ServletContainerInitializer
               Class[] classes = annotation.value();
               if (classes != null)
               {
                   initializer = new ContainerInitializer(service, classes);
                   initializer.addApplicableTypeName(WebAppInitializer.class.getCanonicalName());

                   //If we haven't already done so, we need to register a handler that will
                   //process the whole class hierarchy to satisfy the ServletContainerInitializer
                   if (context.getAttribute(CLASS_INHERITANCE_MAP) == null)
                   {
                       //MultiMap<String> map = new MultiMap<>();
                       ConcurrentHashMap<String, ConcurrentHashSet<String>> map = new ClassInheritanceMap();
                       context.setAttribute(CLASS_INHERITANCE_MAP, map);
                       _classInheritanceHandler = new ClassInheritanceHandler(map);
                   }

                   for (Class c: classes)
                   {
                       //The value of one of the HandlesTypes classes is actually an Annotation itself so
                       //register a handler for it
                       if (c.isAnnotation())
                       {
                           if (LOG.isDebugEnabled()) LOG.debug("Registering annotation handler for "+c.getName());
                          _containerInitializerAnnotationHandlers.add(new ContainerInitializerAnnotationHandler(initializer, c));
                       }
                   }
               }
               else
               {
                   initializer = new ContainerInitializer(service, null);
                   if (LOG.isDebugEnabled()) LOG.debug("No classes in HandlesTypes on initializer "+service.getClass());
               }
           }
           else
           {
               initializer = new ContainerInitializer(service, null);
               if (LOG.isDebugEnabled()) LOG.debug("No annotation on initializer "+service.getClass());
           }
           
           initializers.add(initializer);
       }
       
       
       //add a bean to the context which will call the servletcontainerinitializers when appropriate
       ServletContainerInitializersStarter starter = (ServletContainerInitializersStarter)context.getAttribute(CONTAINER_INITIALIZER_STARTER);
       if (starter != null)
           throw new IllegalStateException("ServletContainerInitializersStarter already exists");
       starter = new ServletContainerInitializersStarter(context);
       context.setAttribute(CONTAINER_INITIALIZER_STARTER, starter);
       context.addBean(starter, true);
   }
}


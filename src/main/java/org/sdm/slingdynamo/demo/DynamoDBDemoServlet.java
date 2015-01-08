package org.sdm.slingdynamo.demo;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.Servlet;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(immediate = true, metatype = true)
@Service(value = Servlet.class)
@Properties({@Property(name = "sling.servlet.resourceTypes",value = "/apps/dynamodb/demo2")
})
public class DynamoDBDemoServlet extends SlingAllMethodsServlet {
	public static final Logger LOGGER = LoggerFactory.getLogger(DynamoDBDemoServlet.class);
    @Override
    protected void doGet(SlingHttpServletRequest request,
        SlingHttpServletResponse response) throws IOException {
        Resource resource = request.getResourceResolver().getResource("/content/dynamodb/data");
        Iterator<Resource> children = resource.getChildren().iterator();
        while(children.hasNext()){
        	Resource child = children.next();
        	LOGGER.info(">>>>>>>>>>" + child);
        }
    }

}
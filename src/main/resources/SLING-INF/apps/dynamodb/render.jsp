<%@ page session="false"%>
<%@ page
	import="javax.jcr.*,org.apache.sling.api.resource.Resource"%>
<%@ taglib prefix="sling"
	uri="http://sling.apache.org/taglibs/sling/1.0"%>

<sling:defineObjects />
<title><%=resource.toString()%></title>
<%=resource.getResourceMetadata()%>
render.jsp rendered this
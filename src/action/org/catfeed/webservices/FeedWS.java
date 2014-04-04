package org.catfeed.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.catfeed.dao.PostDAO;
import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Post;

@Path("/feed")
public class FeedWS
{
	private PostDAO postDAO = new PostDAO();
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON})
    public void imprimirFeed(@JsonProperty("accessToken") String accessToken)
	{
		 String stringAccessToken = transformarJSONEmString(accessToken, "accessToken");
		 
		 FacebookClient facebookClient = new DefaultFacebookClient(stringAccessToken);
			
		 Connection<Post> filteredFeed = facebookClient.fetchConnection("me/home", Post.class, Parameter.with("limit", 10));
		 
		 System.out.println(filteredFeed);
    }

	private String transformarJSONEmString(String json, String parametro)
	{
		 JsonElement je = new JsonParser().parse(json);
		 String string = je.getAsJsonObject().get(parametro).getAsString();
		 
		 return string;
	}
}

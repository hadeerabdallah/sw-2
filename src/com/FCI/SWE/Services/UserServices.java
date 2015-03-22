package com.FCI.SWE.Services;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.UserEntity;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class UserServices {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("id", user.getId());
		}
		return object.toString();

	}
	@POST
	@Path("/logout")
	public String SignOut() {
		User.setCurrentActiveUser();
		JSONObject object = new JSONObject();
		object.put("status","ok");
		return object.toString();

	}

@POST
    @Path("/SendFriendRequest")
    public String SendFriendRequest(@FormParam("email") String userMail,@FormParam("password") String pass,
			@FormParam("friendMial") String friendemail) {
		JSONObject object = new JSONObject();
		System.out.print(userMail+pass+friendemail);
		if (!UserEntity.SendFriendRequest(userMail,friendemail)) {
			object.put("Status", "Failed");

		} else {
			object.put("usermail",userMail );
			object.put("friendmail", friendemail);
		}
		
		return object.toJSONString();
		
	

		
}
	/*@POST
	@Path("/SendFriendRequest")
	// @Produces("text/html")
	public Response SendFriendRequest(@FormParam("FriendMail") String FMail) {
	String urlParameters = "FriendMail=" + FMail;
	String retJson = Connection.connect(
	"http://localhost:8888/rest/SendFriendRequest", urlParameters,
	"POST", "application/x-www-form-urlencoded;charset=UTF-8");
	JSONParser parser = new JSONParser();
	Object obj; 
	try {
	obj = parser.parse(retJson);
	JSONObject object = (JSONObject) obj;
	if (object.get("Status").equals("Failed"))
	return null;
	return Response.ok(new Viewable("/jsp/home")).build(); 
	} catch (ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	
	// UserEntity user = new UserEntity(uname, email, pass);
	// user.saveUser(); return uname;
	
	return null;
	}*/
@POST
@Path("/ConfirmFriendRequest")
	public String ConfirmFriendRequest(@FormParam("email") String userMail,@FormParam("password") String pass,
		@FormParam("friendMial") String friendemail) {
	JSONObject object = new JSONObject();
	
	if (!UserEntity.ConfirmFriendRequest(userMail,friendemail)) {
		object.put("Status", "Failed");
		object.put("Status", "Failed");

	} else {
		
	
		object.put("usermail",userMail );
		object.put("friendmail", friendemail);
	}
	return object.toJSONString();
}
}
/**
 *    © Copyright 2016 IBM Corp.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.sample;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import com.ibm.mfp.adapter.api.ConfigurationAPI;
import com.ibm.mfp.adapter.api.OAuthSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Tag(name = "Sample Adapter Resource")
@Path("/resource")
public class MySpringXmlAdapterResource {
	/*
	 * For more info on JAX-RS see
	 * https://jax-rs-spec.java.net/nonav/2.0-rev-a/apidocs/index.html
	 */

	// Define logger (Standard java.util.Logger)
	static Logger logger = Logger.getLogger(MySpringXmlAdapterResource.class.getName());

	// Inject the MFP configuration API:
	@Context
	ConfigurationAPI configApi;

	/*
	 * Path for method:
	 * "<server address>/mfp/api/adapters/mySpringXmlAdapter/resource"
	 */

	@Operation(summary = "Returns 'Hello from resource'", description = "A basic example of a resource returning a constant string.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Hello message returned") })
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getResourceData() {
		// log message to server log
		logger.info("Logging info message...");

		return "Hello from resource";
	}

	/*
	 * Path for method:
	 * "<server address>/mfp/api/adapters/mySpringXmlAdapter/resource/greet/{name}"
	 */

	@Operation(summary = "Query Parameter Example", description = "Example of passing query parameters to a resource. Returns a greeting containing the name that was passed in the query parameter.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Greeting message returned") })
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/greet")
	public String helloUser(
			@Parameter(description = "Name of the person to greet", required = true) @QueryParam("name") String name) {
		return "Hello " + name + "!";
	}

	/*
	 * Path for method:
	 * "<server address>/mfp/api/adapters/mySpringXmlAdapter/resource/{path}/"
	 */

	@Operation(summary = "Multiple Parameter Types Example", description = "Example of passing parameters using 3 different methods: path parameters, headers, and form parameters. A JSON object containing all the received parameters is returned.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "A JSON object containing all the received parameters returned.") })
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{path}")
	public Map<String, String> enterInfo(
			@Parameter(description = "The value to be passed as a path parameter", required = true) @PathParam("path") String path,
			@Parameter(description = "The value to be passed as a header", required = true) @HeaderParam("Header") String header,
			@Parameter(description = "The value to be passed as a form parameter", required = true) @FormParam("form") String form) {
		Map<String, String> result = new HashMap<String, String>();

		result.put("path", path);
		result.put("header", header);
		result.put("form", form);

		return result;
	}

	/*
	 * Path for method:
	 * "<server address>/mfp/api/adapters/mySpringXmlAdapter/resource/prop"
	 */

	@Operation(summary = "Configuration Example", description = "Example usage of the configuration API. A property name is read from the query parameter, and the value corresponding to that property name is returned.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Property value returned."),
			@ApiResponse(responseCode = "404", description = "Property value not found.") })
	@GET
	@Path("/prop")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getPropertyValue(
			@Parameter(description = "The name of the property to lookup", required = true) @QueryParam("propertyName") String propertyName) {
		// Get the value of the property:
		String value = configApi.getPropertyValue(propertyName);
		if (value != null) {
			// return the value:
			return Response
					.ok("The value of " + propertyName + " is: " + value)
					.build();
		} else {
			return Response.status(Status.NOT_FOUND)
					.entity("No value for " + propertyName + ".").build();
		}

	}

	/*
	 * Path for method:
	 * "<server address>/mfp/api/adapters/mySpringXmlAdapter/resource/unprotected"
	 */

	@Operation(summary = "Unprotected Resource", description = "Example of an unprotected resource, this resource is accessible without a valid token.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "A constant string is returned") })
	@GET
	@Path("/unprotected")
	@Produces(MediaType.TEXT_PLAIN)
	@OAuthSecurity(enabled = false)
	public String unprotected() {
		return "Hello from unprotected resource!";
	}

	/*
	 * Path for method:
	 * "<server address>/mfp/api/adapters/mySpringXmlAdapter/resource/protected"
	 */

	@Operation(summary = "Custom Scope Protection", description = "Example of a resource that is protected by a custom scope. To access this resource a valid token for the scope 'myCustomScope' must be acquired.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "A constant string is returned") })
	@GET
	@Path("/protected")
	@Produces(MediaType.TEXT_PLAIN)
	@OAuthSecurity(scope = "myCustomScope")
	public String customScopeProtected() {
		return "Hello from a resource protected by a custom scope!";
	}


	@Autowired
	HelloService helloService;

	@Path("/hello")
	@GET
	@OAuthSecurity(enabled = false)
	public String sayHello(){
		return helloService.getMessage();
	}

}

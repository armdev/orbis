package io.project.app.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "RoutingEngine")
@RequestMapping("/")
public class RoutingEngine {

	@ApiOperation(value = "Health Check", notes = "Health Check")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful") })
	@CrossOrigin
	@GetMapping
	public String getHealthStatus() {
		return "Routing Engine is UP and Running, Response from ZUUL " ;
	}
}

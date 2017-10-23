package com.statestringservice.controller;

import com.statestringservice.model.AddChar;
import com.statestringservice.model.User;
import com.statestringservice.model.UserResult;
import com.statestringservice.service.UserService;
import com.statestringservice.utils.StateStringUtils;
import com.statestringservice.exception.StateStringException;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Scope("session")
public class StateStringController {
	@Autowired
	private UserService userService;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final String userId = StateStringUtils.getUUID();

	@Autowired
	public StateStringController(UserService userService) {
		logger.trace("Entered Constructor of Controller {}", userId);
		this.userService = userService;
		User user = new User();
		user.setUserId(userId);
		user.setStateString("");
		userService.addUser(user);
		logger.trace("Added Empty entry of stateString for the user in the table {}", userId);
	}

	@RequestMapping(value = "/addState", method = {RequestMethod.POST}, produces = "application/json")
	public ResponseEntity<User> setStateString(
			@ApiParam(value = "set the state for the userId)")
			@RequestParam(value = "stateString")
					String stateString
	) {
		logger.trace("Entered /addState of Controller {}", userId);
		if(stateString.length() <= 200) {
			User user = new User();
			user.setUserId(userId);
			user.setStateString(stateString);
			userService.addUser(user);
			logger.trace("Added  entry of stateString for the user in the table {} {}", userId,stateString);
		}
		return new ResponseEntity<User>(userService.getStateString(userId), HttpStatus.OK);
	}
	@RequestMapping(value = "/state", method = {RequestMethod.GET}, produces = "application/json")
	public ResponseEntity<User> getStateString() {
		logger.trace("Entered /state of Controller {}", userId);
		return new ResponseEntity<User>(userService.getStateString(userId), HttpStatus.OK);
	}

	@RequestMapping(value = "/sum", method = {RequestMethod.GET}, produces = "application/json")
	public UserResult getSumStateString() {
		logger.trace("Entered /sum of Controller {}", userId);
		return new UserResult(userId,
				StateStringUtils.addNumbersInString(userService.getStateString(userId).getStateString()));
	}

	@RequestMapping(value = "/chars", method = {RequestMethod.GET}, produces = "application/json")
	public UserResult getCharsStateString() {
		logger.trace("Entered /chars - GET  of Controller {} ", userId);
		return new UserResult(userId, StateStringUtils.getCharsInString(userService.getStateString(userId).getStateString()));
	}

	@RequestMapping(value = "/chars", method = {RequestMethod.POST}, produces = "application/json")
	public ResponseEntity<User> setCharsStateString(
			@ApiParam(value = "Character and the number of times it needs to be appended to the stateString")
			@RequestBody
					AddChar addChar
	) {
		logger.trace("Entered /chars - POST  of Controller {} ", userId);
		User user = new User();
		user.setUserId(userId);
		try {
			user.setStateString(StateStringUtils.addCharInString(addChar.getCharacter(), addChar.getAmount(),
					userService.getStateString(userId).getStateString()));
		}catch (StateStringException exception){
			logger.error("Exception thrown /chars - POST  of Controller {} {}", userId, exception.getMessage());
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		userService.addUser(user);
		logger.trace("Updated StateString by /chars - POST  of Controller {} ", userId);
		return new ResponseEntity<User>(userService.getStateString(userId), HttpStatus.OK);
	}

	@RequestMapping(value = "/chars", method = {RequestMethod.DELETE}, produces = "application/json")
	public ResponseEntity<User> deleteCharStateString(
			@ApiParam(value = "Character whose last occurrence to be deleted in stateString)")
			@RequestParam(value = "character")
					Character character
	) {
		logger.trace("Entered /chars - DELETE  of Controller {} ", userId);
		User user = new User();
		user.setUserId(userId);
		user.setStateString(StateStringUtils.deleteCharInString(character, userService.getStateString(userId).getStateString()));
		userService.addUser(user);
		logger.trace("Updated StateString by /chars - DELETE  of Controller {} ", userId);
		return new ResponseEntity<User>(userService.getStateString(userId), HttpStatus.OK);
	}
}
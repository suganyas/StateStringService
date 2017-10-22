package com.statestringservice.controller;

import com.statestringservice.model.AddChar;
import com.statestringservice.model.User;
import com.statestringservice.model.UserResult;
import com.statestringservice.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RunWith(MockitoJUnitRunner.class)
public class StateStringControllerTest {

    @Mock
    private UserService service = new UserService() {
        @Override
        public User addUser(User user) {
            return new User();
        }

        @Override
        public User getStateString(String userId) {
            return new User();
        }
    };

    @InjectMocks
    private StateStringController controller = new StateStringController(service);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getStateStringTest() throws Exception {
        ResponseEntity<User> result = controller.getStateString();
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getStateStringSumTest() throws Exception {
        User user = new User();
        user.setUserId("1234");
        user.setStateString("abcd12dg4");
        when(service.getStateString(any())).thenReturn(user);
        UserResult result = controller.getSumStateString();
        Assert.assertEquals(result.getresult(), "16");
    }

    @Test
    public void getCharsStateStringSum() throws Exception {
        User user = new User();
        user.setUserId("1234");
        user.setStateString("abcd12dg4");
        when(service.getStateString(any())).thenReturn(user);
        UserResult result = controller.getCharsStateString();
        Assert.assertEquals(result.getresult(), "abcddg");
    }

    @Test
    public void setCharsStateStringTest() throws Exception {
        User user = new User();
        user.setUserId("1234");
        user.setStateString("abcd12dg4");
        AddChar addChar = new AddChar();
        addChar.setAmount(2);
        addChar.setCharacter('d');
        when(service.getStateString(any())).thenReturn(user);
        ResponseEntity<User> result = controller.setCharsStateString(addChar);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void setCharsStateStringBadReqTest() throws Exception {
        User user = new User();
        user.setUserId("1234");
        user.setStateString("abcd12dg4");
        AddChar addChar = new AddChar();
        addChar.setAmount(289);
        addChar.setCharacter('d');
        when(service.getStateString(any())).thenReturn(user);
        ResponseEntity<User> result = controller.setCharsStateString(addChar);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void setCharsStateStringInvalidCharTest() throws Exception {
        User user = new User();
        user.setUserId("1234");
        user.setStateString("abcd12dg4");
        AddChar addChar = new AddChar();
        addChar.setAmount(3);
        addChar.setCharacter('!');
        when(service.getStateString(any())).thenReturn(user);
        ResponseEntity<User> result = controller.setCharsStateString(addChar);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deleteCharStateStringTest() throws Exception {
        when(service.getStateString(any())).thenReturn(new User());
        ResponseEntity<User> result = controller.deleteCharStateString('a');
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }
}

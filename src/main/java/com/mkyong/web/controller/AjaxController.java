package com.mkyong.web.controller;

import java.util.ArrayList;
import java.util.List;

import com.mkyong.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.mkyong.web.jsonview.Views;
import com.mkyong.web.model.AjaxResponseBody;
import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.User;

@RestController
public class AjaxController {

    @Autowired
    private UserService userService;

    List<User> users = iniDataForTesting();

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, limited the json data display to client.
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/search/api/getSearchResult")
    public AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria search) {

        AjaxResponseBody result = new AjaxResponseBody();
        System.out.println("===============================result ----" +  result);

        if (isValidSearchCriteria(search)) {
            List<User> users = userService.findByCriteria(search);


            if (users.size() > 0) {
                result.setCode("200");
                result.setMsg("");
                result.setResult(users);
            } else {
                result.setCode("204");
                result.setMsg("No user!");
            }

        } else {
            result.setCode("400");
            result.setMsg("Search criteria is empty!");
        }
        //AjaxResponseBody will be converted into json format and send back to client.
        return result;
    }

    private boolean isValidSearchCriteria(SearchCriteria search) {
        System.out.println("======SearchCriteria ----" +  search);

        boolean valid = true;

        if (search == null) {
            valid = false;
        }

        if ((StringUtils.isEmpty(search.getUsername())) && (StringUtils.isEmpty(search.getEmail())) && (StringUtils.isEmpty(search.getAddress()))) {
            valid = false;
        }

        return valid;
    }

    private List<User> iniDataForTesting() {
        users = new ArrayList<User>();

        User user1 = new User("mkyong", "pass123", "mkyong@yahoo.com", "012-1234567", "address 123");
        User user2 = new User("yflow", "pass456", "yflow@yahoo.com", "016-7654321", "address 456");
        User user3 = new User("laplap", "pass789", "mkyong@yahoo.com", "012-111111", "address 789");
        User user4 = new User("Serhii", "pass12345", "manser@yahoo.com", "503-808-557", "Ramzesa 8");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        return users;
    }

    // Simulate the search function
    private List<User> findByUserNameOrEmailorAddress(String username, String email, String address) {

        List<User> result = new ArrayList<User>();

        for (User user : users) {
            System.out.println("User ----" +  user);

            if ((!StringUtils.isEmpty(username)) && (!StringUtils.isEmpty(email)) && (!StringUtils.isEmpty(address))) {

                if (username.equals(user.getUsername()) && email.equals(user.getEmail()) && address.equals(user.getAddress())) {
                    result.add(user);
                    continue;
                } else {
                    continue;
                }

            }
            if (!StringUtils.isEmpty(username)) {
                if (username.equals(user.getUsername())) {
                    result.add(user);
                    continue;
                }
            }

            if (!StringUtils.isEmpty(email)) {
                if (email.equals(user.getEmail())) {
                    result.add(user);
                    continue;
                }
            }

            if (!StringUtils.isEmpty(address)) {
                if (address.equals(user.getAddress())) {
                    result.add(user);
                    continue;
                }
            }

        }

        return result;

    }
}

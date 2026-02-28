package com.eazybytes.backend.controller;

import com.eazybytes.backend.dto.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dummy/users")
public class UserController {

    @GetMapping({"{userId}/posts/{postId}", "{userId}"})
    public ResponseEntity<String> searchUserPostWithMultiPathVariable(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "postId", required = false) Long postId
    ) {
        String response;
        if (postId == null) {
            response = "Fetched user with id: " + userId;
        } else {
            response = "Fetched user with id: " + userId + " and post id: " + postId;
        }
//        return response;
//        return ResponseEntity.ok(response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping({"{userId}/orders/{orderId}"})
    public String searchUserOrderWithMultiPathVariable(
            @PathVariable(name = "userId") Long customerId,
            @PathVariable(name = "orderId") Long orderId
    ) {
        return "Fetched user with id: " + customerId + " and order id: " + orderId ;
    }

    @GetMapping({"{userId}/address/{addressId}"})
    public String searchUserAddressWithMultiPathVariable(
            @PathVariable Map<String, String> pathVariableMap
    ) {
        String userId = pathVariableMap.get("userId");
        String addressId = pathVariableMap.get("addressId");

        return "Fetched user with id: " + userId + " and address id: " + addressId;
    }

    @GetMapping("/search")
    public String searchUserWithQueryParams(
            @RequestParam(name = "name", required = false, defaultValue = "Guest") String name,
            @RequestParam(name = "gender") String sex
    ) {
        return "Fetched user with query params : " + name + " and gender: " + sex;
    }

    /**
     * This method fetches a user based on the query parameters provided.
     * The query parameters should include the user's name and gender.
     *
     * @param requestParams the query parameters as a map
     * @return a string containing the fetched user's name and gender
     */
    @GetMapping("/search/map")
    public String searchUserWithMapQueryParams(
            @RequestParam Map<String, String> requestParams
    ) {
        return "Fetched user with query params : " + requestParams.get("name") + " and gender: " + requestParams.get("gender");
    }

    @GetMapping("/headers")
    public String readRequestHeaders(
            @RequestHeader("User-Agent") String userAgent,
            @RequestHeader(name = "User-Location", required = false, defaultValue = "Hyderabad") String userLocation
    ) {
        return "Received: " + userAgent + " " + userLocation;
    }

    @GetMapping("/headers/map")
    public String readRequestHeadersWithMap(@RequestHeader Map<String, String> requestHeaders) {
        return "Received: " + requestHeaders.get("User-Agent") + " " + requestHeaders.get("User-Location");
    }

    @GetMapping("/headers/http-headers")
    public String readRequestHeadersWithHttpHeaders(@RequestHeader HttpHeaders requestHeaders) {
        return "Received: " + requestHeaders.get("User-Agent") + " " + requestHeaders.get("User-Location");
    }

    @PostMapping
    public String createUser(@RequestBody UserDto userDto) {
        return "Created User with the data: " + userDto.toString();
    }

    @PostMapping("request-entity")
    public ResponseEntity<String> createUserWithRequestEntity(
            RequestEntity<UserDto> requestEntity
    ) {
        HttpHeaders httpHeaders = requestEntity.getHeaders();
        UserDto userDto = requestEntity.getBody();
        String queryParam = requestEntity.getUrl().getQuery();
        String pathVariables = requestEntity.getUrl().getPath();

        // return "Created User with the data: " + userDto.toString();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Custom-Header", "ExampleValue")
                .body("Created User with the data: " + userDto.toString());
    }
}

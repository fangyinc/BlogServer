package com.chengfy.blog.controller;

import com.chengfy.blog.domain.User;
import com.chengfy.blog.security.JwtUser;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {

    protected static final String PAGE_SIZE = "5";

    protected static final String ROLE_WRITE_ARTICLES = "ROLE_WRITE_ARTICLES";

    protected static final String ROLE_ADMINISTER = "ROLE_ADMINISTER";

    protected static final String ROLE_VISITOR = "ROLE_VISITOR";


    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // get success 200
    protected static ResponseEntity<?> restGetOk(Object object, String message){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Entity(HttpStatus.OK.name(), message, object));
    }
    // post/put success 201
    protected static ResponseEntity<?> restPostOk(Object object, String message){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Entity(HttpStatus.CREATED.name(), message, object));
    }

    // delete success 204
    protected static ResponseEntity<?> restDeleteOk(){
        return ResponseEntity.noContent().build();
    }
    protected static ResponseEntity<?> restBadRequest(String message){
        return ResponseEntity.badRequest()
                .body(new Entity(HttpStatus.BAD_REQUEST.name(), message, null));
    }

    protected static ResponseEntity<?> restUnauthorized(String message){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new Entity(HttpStatus.UNAUTHORIZED.name(), message, null));
    }

    protected static ResponseEntity<?> restForbidden(String message){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).
                body(new Entity(HttpStatus.FORBIDDEN.name(), message, null));

    }
    protected static ResponseEntity<?> restNotFound(String message){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new Entity(HttpStatus.NOT_FOUND.name(), message, null));

    }
    protected static ResponseEntity<?> restServerError(String message){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(new Entity(HttpStatus.INTERNAL_SERVER_ERROR.name(), message, null));

    }

    protected User getUserFromContext(){
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        logger.info(jwtUser.getUsername() + "Authorities: " + jwtUser.getAuthorities());

        User user = jwtUser.getUser();
        if(user == null){
            logger.warn("user is null");
        }
        return user;
    }
    @Data
    private static class Entity{
        private String status;
        private String message;
        private Object body;
        public Entity(String status, String message, Object body) {
            this.status = status;
            this.message = message;
            this.body = body;
        }
    }
}

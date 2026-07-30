package com.assignment.cards.dto;

import java.util.List;

import com.assignment.cards.external.JsonPlaceholderPost;

public class EmployeePostsResponse {

    private EmployeeResponse employee;
    private List<JsonPlaceholderPost> posts;

    public EmployeePostsResponse() {
    }

    public EmployeeResponse getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeResponse employee) {
        this.employee = employee;
    }

    public List<JsonPlaceholderPost> getPosts() {
        return posts;
    }

    public void setPosts(List<JsonPlaceholderPost> posts) {
        this.posts = posts;
    }
}
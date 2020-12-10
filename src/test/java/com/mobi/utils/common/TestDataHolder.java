package com.mobi.utils.common;

import com.mobi.service.models.PostDetail;

import java.util.List;

public class TestDataHolder {
    private List<PostDetail> postDetails;

    public void setPostDetails(List<PostDetail> postDetails) {
        this.postDetails = postDetails;
    }

    public List<PostDetail> getPostDetails() {
        return postDetails;
    }
}

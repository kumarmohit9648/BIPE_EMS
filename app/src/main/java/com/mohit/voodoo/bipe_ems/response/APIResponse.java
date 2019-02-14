package com.mohit.voodoo.bipe_ems.response;

public interface APIResponse {
    public void OnResponseAPI(Object object);
    public void OnErrorAPI(String error);
}

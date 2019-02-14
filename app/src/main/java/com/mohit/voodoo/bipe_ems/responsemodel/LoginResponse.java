package com.mohit.voodoo.bipe_ems.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("error_code")
    @Expose
    public String errorCode;
    @SerializedName("response_string")
    @Expose
    public String responseString;
    @SerializedName("user_data")
    @Expose
    public UserData userData;

    public class UserData {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("token_id")
        @Expose
        public String tokenId;

        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("passsword")
        @Expose
        public String passsword;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("role")
        @Expose
        public String role;
        @SerializedName("user_role")
        @Expose
        public String userRole;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }

        public String getPasssword() {
            return passsword;
        }

        public String getPhone() {
            return phone;
        }

        public String getRole() {
            return role;
        }

        public String getUserRole() {
            return userRole;
        }

        public String getTokenId() {
            return tokenId;
        }
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getResponseString() {
        return responseString;
    }

    public UserData getUserData() {
        return userData;
    }
}

package com.example.auth.duo

import com.duosecurity.client.Http
import com.example.auth.User
import com.squareup.okhttp.Response
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod

class DuoAdminService {
    private static final String DUO_ADMIN_USER_PATH = "/admin/v1/users"

    DuoAdminCredentials duoAdminCredentials

    @Value('${duo.env}')
    private String usernamePrefix

    boolean createUserInDuo(User updatedUser) {
        Response result

        if(!duoAdminCredentials.isCredentialsProvided()) {
            log.error("DUO Admin API credentials were not provided")
            return false
        }

        try {
            Http request = new Http(
                HttpMethod.POST.name(),
                duoAdminCredentials.getHostname(),
                DUO_ADMIN_USER_PATH
            )

            request.addParam("username", prefixedUsername(updatedUser.id))
            request.addParam("realname", updatedUser.username)
            request.addParam("email", updatedUser.username)
            request.addParam("status", "active")

            // Make sure signRequest is always called just before execute because
            // params and headers should all be set before calling signRequest
            request.signRequest(duoAdminCredentials.ikey, duoAdminCredentials.skey)
            result = request.executeHttpRequest()

            log.info("DUO Create User response status for " + updatedUser.username + " : " + result.code())
            log.info("DUO Create User response body for " + updatedUser.username + " : " + result.message())

            return result.isSuccessful()
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported Encoding Exception caught", e)
            throw e
        } catch (Exception ex) {
            log.error("Exception caught while updating user calling DUO Admin API", ex)
            throw ex
        }
    }

    private String retrieveDuoUserIdFromUsername(Long userId) {
        JSONObject result = null

        if(!duoAdminCredentials.isCredentialsProvided()) {
            log.error("DUO Admin API credentials were not provided")
            return ""
        }

        try {
            Http request = new Http(
                HttpMethod.GET.name(),
                duoAdminCredentials.getHostname(),
                DUO_ADMIN_USER_PATH
            )

            request.addParam("username", prefixedUsername(userId))

            request.signRequest(duoAdminCredentials.ikey, duoAdminCredentials.skey)
            JSONArray resultArray = (JSONArray) request.executeRequest()

            if(resultArray) {
                result = resultArray.getJSONObject(0)
            }

            return result != null ? result.getString("user_id") : ""

        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported Encoding Exception", e)
            throw e
        } catch (Exception ex) {
            log.error("Exception caught while getting user by username while calling DUO Admin API", ex)
            throw ex
        }
    }

    private String prefixedUsername(Long userId) {
        return String.join("-", usernamePrefix, String.valueOf(userId))
    }
}

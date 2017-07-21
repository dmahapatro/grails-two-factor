package com.example.auth.duo

import com.duosecurity.duoweb.DuoWeb
import com.duosecurity.duoweb.DuoWebException
import grails.web.api.ServletAttributes
import org.springframework.beans.factory.annotation.Value
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

class DuoService implements ServletAttributes {
    DuoCredentials duoCredentials

    @Value('${duo.env}')
    private String usernamePrefix

    DuoSignResponse signRequest(String username) {
        String sigRequest = DuoWeb.signRequest(
            duoCredentials.ikey,
            duoCredentials.skey,
            duoCredentials.akey,
            "${usernamePrefix}-$username"
        )

        return new DuoSignResponse(
            duoCredentials.getHostname(),
            sigRequest,
            getPostCallbackUrl()
        )
    }

    String verifyResponse(String sigResponse) {
        String authUser = null

        try {
            authUser = DuoWeb.verifyResponse(
                duoCredentials.ikey,
                duoCredentials.skey,
                duoCredentials.akey,
                sigResponse
            )
        } catch (DuoWebException | NoSuchAlgorithmException | InvalidKeyException | IOException e) {
            log.error("Exception from DUO", e)
        }

        return authUser
    }

    private String getPostCallbackUrl() {
        String http = request.getRequestURL().toString().contains("localhost") ? "http" : "https"
        "${http}://${request.getServerName()}:${request.getServerPort()}${request.getContextPath()}/duo/verify"
    }
}

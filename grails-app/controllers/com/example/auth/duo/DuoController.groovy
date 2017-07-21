package com.example.auth.duo

import com.example.auth.User
import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured

@Secured("isAuthenticated()")
class DuoController {
    SpringSecurityService springSecurityService
    DuoService duoService

    def login() {
        User user = springSecurityService.currentUser

        DuoSignResponse duoDigResponse = duoService.signRequest(user.username)

        [duoSigResponse: duoDigResponse]
    }

    def verify() {
        String sigResponse = request.getParameter('sig_response')
        String authUser = duoService.verifyResponse(sigResponse);

        if(authUser) {
            redirect controller: 'sample'
        } else {
            render status: HttpStatus.UNAUTHORIZED, text: "Failed DUO Authentication"
        }
    }
}

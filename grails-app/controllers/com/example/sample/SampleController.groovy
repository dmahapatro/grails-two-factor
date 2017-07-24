package com.example.sample

import org.springframework.security.access.annotation.Secured

@Secured("isAuthenticated()")
class SampleController {

    def index() {
        redirect uri: '/landing'
    }
}

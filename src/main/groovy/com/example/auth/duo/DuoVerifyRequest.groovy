package com.example.auth.duo

import groovy.transform.CompileStatic

@CompileStatic
class DuoVerifyRequest {
    String accessToken
    String sigResponse
}

package com.example.auth.duo

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor

@CompileStatic
@TupleConstructor
class DuoSignResponse {
    String hostname
    String sigRequest
    String postCallbackUrl
}

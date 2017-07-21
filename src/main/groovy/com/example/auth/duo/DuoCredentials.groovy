package com.example.auth.duo

import groovy.transform.CompileStatic
import org.apache.commons.codec.digest.DigestUtils

@CompileStatic
class DuoCredentials {
    String hostname
    String ikey
    String skey
    String akey = DigestUtils.sha1Hex("gr8conf")
}

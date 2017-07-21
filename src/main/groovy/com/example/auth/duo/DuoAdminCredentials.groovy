package com.example.auth.duo

import groovy.transform.CompileStatic
import org.apache.commons.lang3.StringUtils

@CompileStatic
class DuoAdminCredentials {
    String hostname
    String ikey
    String skey

    boolean isCredentialsProvided() {
        StringUtils.isNoneEmpty(hostname, ikey, skey)
    }
}

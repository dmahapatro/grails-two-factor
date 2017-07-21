import com.example.auth.UserPasswordEncoderListener
import com.example.auth.duo.DuoAdminCredentials
import com.example.auth.duo.DuoCredentials

// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener, ref('hibernateDatastore'))

    duoAdminCredentials(DuoAdminCredentials) {
        hostname = grailsApplication.config.getProperty('duo.host')
        ikey = grailsApplication.config.getProperty('duo.admin.ikey')
        skey = grailsApplication.config.getProperty('duo.admin.skey')
    }

    duoCredentials(DuoCredentials) {
        hostname = grailsApplication.config.getProperty('duo.host')
        ikey = grailsApplication.config.getProperty('duo.websdk.ikey')
        skey = grailsApplication.config.getProperty('duo.websdk.skey')
    }
}

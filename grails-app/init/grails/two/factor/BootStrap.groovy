package grails.two.factor

import com.example.auth.Role
import com.example.auth.User
import com.example.auth.UserRole

class BootStrap {

    def init = { servletContext ->
        //auth init
        def adminRole = new Role(authority: 'ROLE_ADMIN').save flush: true, failOnError: true
        def userRole = new Role(authority: 'ROLE_USER').save flush: true, failOnError: true

        def adminUser = new User(username: 'admin@sample.org', fullName: "John Doe", password: 'admin')
        def dmahapatro = new User(username: 'dhiraj.mahapatro@gmail.com', fullName: "Dhiraj Mahapatro", password: 'admin')
        def normalUser = new User(username: 'simple@sample.org', fullName: "Jack Miller", password: 'simple')
        [adminUser, normalUser, dmahapatro]*.save flush: true, failOnError: true

        UserRole.create(adminUser, adminRole, true)
        UserRole.create(dmahapatro, adminRole, true)
        UserRole.create(normalUser, userRole, true)

        assert User.count() == 3
        assert Role.count() == 2
        assert UserRole.count() == 3
    }
    def destroy = {
    }
}

package grails.two.factor

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/landing"(view: "/index")
        "/"(controller: "login")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

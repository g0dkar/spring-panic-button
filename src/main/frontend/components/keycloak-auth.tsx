"use client"

import Keycloak from "keycloak-js"

const keycloak = new Keycloak({
    url: "http://localhost:18080",
    realm: "spring-panic-button",
    clientId: "spring-panic-button-backend",
})

export default keycloak

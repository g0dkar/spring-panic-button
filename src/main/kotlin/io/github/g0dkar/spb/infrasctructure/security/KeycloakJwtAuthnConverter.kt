package io.github.g0dkar.spb.infrasctructure.security

import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component

@Component
@Suppress("UNCHECKED_CAST")
class KeycloakJwtAuthnConverter : Converter<Jwt, AbstractAuthenticationToken> {
    companion object {
        private val log = LoggerFactory.getLogger(KeycloakJwtAuthnConverter::class.java)
        const val REALM_ACCESS_CLAIM = "realm_access"
        const val RESOURCE_ACCESS_CLAIM = "resource_access"
        const val ROLES_ENTRY = "roles"
    }

    override fun convert(source: Jwt): AbstractAuthenticationToken? {
        val realmAccess: Collection<String> = source.getClaim<Map<String, Any>?>(REALM_ACCESS_CLAIM)
            ?.get(ROLES_ENTRY) as Collection<String>?
            ?: emptyList()
        val resourceAccess: Collection<String> = source.getClaim<Map<String, Any>?>(RESOURCE_ACCESS_CLAIM)
            ?.flatMap { (key, value) ->
                if (value is Map<*, *>) {
                    val rolesList: Collection<String>? = value[ROLES_ENTRY] as Collection<String>?

                    if (!rolesList.isNullOrEmpty()) {
                        rolesList.map { "$key.$it" }
                    } else {
                        emptyList()
                    }
                } else {
                    emptyList()
                }
            }
            ?: emptyList()

        val allRolesSet = (realmAccess + resourceAccess).toSet()
        val allRoles = allRolesSet.mapTo(mutableSetOf()) { role -> GrantedAuthority { role } }

        log.debug("Roles gathered from Keycloak JWT: {}", allRolesSet)

        return JwtAuthenticationToken(source, allRoles)
    }
}

"use client"

import React, {useEffect, useState} from "react"
import keycloak from "@/components/keycloak-auth"
import {Button} from "@/components/ui/button"

const HomePage = () => {
    const [authenticated, setAuthenticated] = useState(false)

    useEffect(() => {
        keycloak.init()
            .then((response) => {
                console.log("Init Response:", response, "(type =", typeof response, ")")
                setAuthenticated(response)
            })
            .catch((err) => {
                console.log("Error initializing:", err)
                setAuthenticated(false)
            })
    }, [])

    const login = () => {
        keycloak.login()
            .then((it) => console.log("Login done:", it))
            .catch((err) => console.log("Login error:", err))
    }

    const logout = () => {
        keycloak.logout()
            .then((it) => console.log("Logout done:", it))
            .catch((err) => console.log("Logout error:", err))
    }

    return <section className="bg-gray-50 dark:bg-gray-900">
        <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
            <h1 className="text-center mb-6 text-2xl font-bold text-gray-900 dark:text-white">
                Panic Button
            </h1>
            <div
                className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                    <h2 className="text-xl font-semibold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                        Sign in to your account
                    </h2>

                    {authenticated ? <p>Hello User!</p> : <Button onClick={login}>Login</Button>}

                    <Button onClick={logout}>Logout</Button>
                </div>
            </div>
        </div>
    </section>
}

export default HomePage

"use client"

import {Inter, JetBrains_Mono} from "next/font/google"
import "./globals.css"
import React from "react"
import {cn, useTheme} from "@/lib/utils"
import {SidebarProvider} from "@/components/ui/sidebar"

const interSans = Inter({
    variable: "--font-inter-sans",
    subsets: ["latin"],
})

const jetbrainsMono = JetBrains_Mono({
    variable: "--font-jetbrains-mono",
    subsets: ["latin"],
})

const RootLayout = ({children}: Readonly<{ children: React.ReactNode }>) => {
    const [theme, setTheme] = useTheme()

    return <html lang="en" className={theme}>
    <body
        className={cn(interSans.className, interSans.variable, jetbrainsMono.variable, "antialiased bg-gray-50 dark:bg-gray-950")}>
    <SidebarProvider>
        {children}
    </SidebarProvider>
    </body>
    </html>
}

export default RootLayout

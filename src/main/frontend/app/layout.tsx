"use client"

import {Inter, JetBrains_Mono} from "next/font/google"
import "./globals.css"
import React from "react"
import {ThemeProvider} from "@/components/theme-provider"
import {cn} from "@/lib/utils"

const interSans = Inter({
    variable: "--font-inter-sans",
    subsets: ["latin"],
})

const jetbrainsMono = JetBrains_Mono({
    variable: "--font-jetbrains-mono",
    subsets: ["latin"],
})

const RootLayout = ({children}: Readonly<{ children: React.ReactNode }>) => {
    return <html lang="en">
    <body
        className={cn(interSans.className, interSans.variable, jetbrainsMono.variable, "min-h-screen bg-background font-sans antialiased h-full p-4")}>
    <ThemeProvider attribute="class" defaultTheme="dark">
        {children}
    </ThemeProvider>
    </body>
    </html>
}

export default RootLayout

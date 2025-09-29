"use client"

import {Inter, JetBrains_Mono} from "next/font/google"
import "../styles/globals.css"
import React from "react"
import {cn} from "@/lib/utils"

const interSans = Inter({
    variable: "--font-inter-sans",
    subsets: ["latin"],
})

const jetbrainsMono = JetBrains_Mono({
    variable: "--font-jetbrains-mono",
    subsets: ["latin"],
})

const RootLayout = ({children}: Readonly<{ children: React.ReactNode }>) =>
    <html lang="en">
    <body className={cn(interSans.className, interSans.variable, jetbrainsMono.variable, "antialiased")}>
    {children}
    </body>
    </html>

export default RootLayout

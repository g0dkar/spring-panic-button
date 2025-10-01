"use client"

import React from "react"
import {
    Sidebar,
    SidebarContent,
    SidebarGroup,
    SidebarGroupContent,
    SidebarGroupLabel,
    SidebarMenu,
    SidebarMenuButton,
    SidebarMenuItem,
} from "@/components/ui/sidebar"
import {DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger} from "@/components/ui/dropdown-menu"
import {ChevronDown} from "lucide-react"
import {useTheme} from "@/lib/utils"

const HomePage = () => {
    const [theme, setTheme] = useTheme()

    return <section className="relative z-10 flex min-h-svh flex-row w-4xl m-auto">
        <div className="flex flex-col">
            <Sidebar>
                <SidebarContent>
                    <SidebarGroup>
                        <SidebarGroupLabel>Panic Button</SidebarGroupLabel>
                        <SidebarGroupContent>
                            <SidebarMenu>
                                <SidebarMenuItem>
                                    <SidebarMenuButton asChild>
                                        <a href="#">Current Status</a>
                                    </SidebarMenuButton>
                                    <SidebarMenuButton asChild>
                                        <a href="#all-statuses">All Statuses</a>
                                    </SidebarMenuButton>
                                    <DropdownMenu>
                                        <DropdownMenuTrigger asChild>
                                            <SidebarMenuButton className="text-xs">
                                                Theme
                                                <ChevronDown className="ml-auto"/>
                                            </SidebarMenuButton>
                                        </DropdownMenuTrigger>
                                        <DropdownMenuContent>
                                            <DropdownMenuItem className={theme == "light" ? "font-bold" : "font-normal"}
                                                              onClick={() => setTheme("light")}>
                                                <span>Light</span>
                                            </DropdownMenuItem>
                                            <DropdownMenuItem className={theme == "dark" ? "font-bold" : "font-normal"}
                                                              onClick={() => setTheme("dark")}>
                                                <span>Dark</span>
                                            </DropdownMenuItem>
                                        </DropdownMenuContent>
                                    </DropdownMenu>
                                </SidebarMenuItem>
                            </SidebarMenu>
                        </SidebarGroupContent>
                    </SidebarGroup>
                </SidebarContent>
            </Sidebar>
        </div>
        <div className="flex flex-1 flex-col p-2">
            <div
                className="w-full bg-background rounded-lg shadow dark:border p-4 dark:bg-gray-800 dark:border-gray-700">
                asd
            </div>
        </div>
        {/*<div className="flex flex-col items-center px-6 py-8 mx-auto md:h-screen lg:py-0 bg-red-300">*/}
        {/*    <div className="grid grid-cols-3 gap-1">*/}
        {/*        <div className="columns-1">Nav</div>*/}
        {/*        <div className="columns-1">*/}
        {/*            <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">*/}
        {/*                Page*/}
        {/*            </div>*/}
        {/*        </div>*/}
        {/*    </div>*/}
        {/*    <h1 className="text-center mb-6 text-2xl font-bold text-gray-900 dark:text-white bg-blue-300">*/}
        {/*        Panic Button*/}
        {/*    </h1>*/}
        {/*    <div*/}
        {/*        className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">*/}
        {/*        <div className="p-6 space-y-4 md:space-y-6 sm:p-8">*/}
        {/*            <h2 className="text-xl font-semibold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">*/}
        {/*                Sign in to your account*/}
        {/*            </h2>*/}
        {/*        </div>*/}
        {/*    </div>*/}
        {/*</div>*/}
    </section>
}

export default HomePage

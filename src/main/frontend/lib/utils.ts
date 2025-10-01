import {type ClassValue, clsx} from "clsx"
import {twMerge} from "tailwind-merge"
import {Dispatch, SetStateAction, useEffect, useState} from "react"

export function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs))
}

let theme: "light" | "dark" | null = null
let setTheme: Dispatch<SetStateAction<"light" | "dark">> | null = null

export function useTheme(): ["light" | "dark", Dispatch<SetStateAction<"light" | "dark">>] {
    if (theme == null) {
        const [themeValue, setThemeValue] = useState<"light" | "dark">("light")

        console.log("[useTheme] init...")

        theme = themeValue
        setTheme = setThemeValue

        useEffect(() => {
            const localStorageTheme = localStorage.getItem("theme") ?? "light"

            console.log("[useTheme] init theme: ", localStorageTheme)

            if (localStorageTheme === "light" || localStorageTheme === "dark") {
                setTheme!(localStorageTheme)
            } else {
                setTheme!("light")
            }
        }, [])

        useEffect(() => {
            const newTheme = theme ?? "light"
            console.log("[useTheme] setting theme: ", newTheme)
            localStorage.setItem("theme", newTheme)
        }, [theme])
    }

    return [theme, setTheme!]
}

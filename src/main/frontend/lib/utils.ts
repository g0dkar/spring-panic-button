import {type ClassValue, clsx} from "clsx"
import {twMerge} from "tailwind-merge"
import useSWR, {Fetcher} from "swr"
import {PanicStatus, PanicStatusList} from "@/lib/spb-model"

export function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs))
}

export function apiBaseUrl(env: string = process.env.NODE_ENV): string {
    return env === "production" ? "" : "http://localhost:8080"
}

export function metadataAsString(object?: any) {
    return JSON.stringify(object ?? {}, null, 2)
}

export async function apiGet(url: string, auth?: string): Promise<Response> {
    return fetch(url, {
        method: "GET",
        headers: auth == null ? {} : {"Authorization": `Bearer ${auth}`},
    })
}

export async function apiPost(url: string, data: any, auth?: string): Promise<Response> {
    const headers: HeadersInit = {"Content-Type": "application/json"}

    if (auth != null) {
        headers["Authorization"] = `Bearer ${auth}`
    }

    return fetch(url, {
        method: "POST",
        headers: headers,
        body: JSON.stringify(data),
    })
}

export function urlFetcher<T>(): Fetcher<T, string> {
    return (url) => apiGet(url).then(it => it.json() as Promise<T>)
}

export function usePanicStatus(baseUrl: string = apiBaseUrl()): {
    panicStatus?: PanicStatus,
    loading: boolean,
    error?: Error
} {
    const fullUrl = `${baseUrl}/api/v1/panic`

    const {data, error, isLoading} = useSWR(fullUrl, urlFetcher<PanicStatus>())

    return {
        panicStatus: data,
        loading: isLoading,
        error: error,
    }
}

export function usePanicStatusList(baseUrl: string = apiBaseUrl()): {
    panicStatusList?: PanicStatusList,
    loadingList: boolean,
    errorList?: Error
} {
    const fullUrl = `${baseUrl}/api/v1/panic/all`

    const {data, error, isLoading} = useSWR(fullUrl, urlFetcher<PanicStatusList>())

    return {
        panicStatusList: data,
        loadingList: isLoading,
        errorList: error,
    }
}

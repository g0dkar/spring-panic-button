import {type ClassValue, clsx} from "clsx"
import {twMerge} from "tailwind-merge"
import useSWR, {Fetcher} from "swr"
import {PanicStatus} from "@/lib/spb-model"

export function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs))
}

export async function apiGet(url: string): Promise<Response> {
    return fetch(url, {method: "GET"})
}

export function urlFetcher<T>(): Fetcher<T, string> {
    return (url) => apiGet(url).then(it => it.json() as Promise<T>)
}

export function usePanicStatus(baseUrl: string): { panicStatus?: PanicStatus, loading: boolean, error: Error } {
    const fullUrl = `${baseUrl}/api/v1/panic`

    const {data, error, isLoading} = useSWR(fullUrl, urlFetcher<PanicStatus>())

    return {
        panicStatus: data,
        loading: isLoading,
        error: error,
    }
}

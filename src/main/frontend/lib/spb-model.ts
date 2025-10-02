import {z} from "zod"

export const PanicStatuses = z.enum(["OK", "WARNING", "OUTAGE", "MAJOR_OUTAGE"])

export const PanicStatusesVisual = {
    "OK": "bg-emerald-100 dark:bg-emerald-950",
    "WARNING": "bg-amber-100 dark:bg-amber-950",
    "OUTAGE": "bg-rose-100 dark:bg-rose-950",
    "MAJOR_OUTAGE": "bg-red-200 dark:bg-red-800",
}

export const KeycloakUserSchema = z.object({
    id: z.uuid(),
    username: z.string(),
    first_name: z.string(),
    last_name: z.string().default(""),
    avatar: z.string().url(),
    created: z.string().datetime(),
    active: PanicStatuses,
    link_list_count: z.number().default(0),
    link_count: z.number().default(0),
    view_count: z.number().default(0),
})

import {z} from "zod"

export const PanicStatuses = z.enum(["OK", "WARNING", "OUTAGE", "MAJOR_OUTAGE"])

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

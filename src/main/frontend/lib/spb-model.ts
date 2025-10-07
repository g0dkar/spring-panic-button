import {z} from "zod"

export const PanicStatuses = z.enum(["OK", "WARNING", "OUTAGE", "MAJOR_OUTAGE"])

export const PanicStatusSchema = z.object({
    id: z.uuidv7().optional(),
    status: PanicStatuses,
    metadata: z.object().optional(),
    timestamp: z.iso.datetime({offset: true}),
    declared_by: z.string(),
    declared_by_id: z.string().optional(),
})

export type PanicStatus = z.infer<typeof PanicStatusSchema>

export const PanicStatusRequestSchema = z.object({
    status: PanicStatuses,
    metadata: z.string().optional(),
})

export type PanicStatusRequest = z.infer<typeof PanicStatusRequestSchema>

export const ListingResultSchema = z.object({
    page: z.number(),
    page_size: z.number(),
    total_item_count: z.number(),
    offset: z.number(),
    total_pages: z.number(),
    page_item_count: z.number(),
})

export const PanicStatusListSchema = ListingResultSchema.extend({
    items: PanicStatusSchema.array(),
})

export type PanicStatusList = z.infer<typeof PanicStatusListSchema>

export const EMPTY_PANIC_STATUS = PanicStatusSchema.parse({
    "status": "OK",
    "metadata": {},
    "timestamp": "2025-09-29T22:24:20.323832Z",
    "declared_by": "Init",
})

export const PanicStatusesVisual = {
    "LOADING": "bg-accent animate-pulse",
    "OK": "bg-emerald-100 dark:bg-emerald-950",
    "WARNING": "bg-amber-100 dark:bg-amber-950",
    "OUTAGE": "bg-rose-100 dark:bg-rose-950",
    "MAJOR_OUTAGE": "bg-red-200 dark:bg-red-800",
}

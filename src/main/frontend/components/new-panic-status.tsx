import {PanicStatus, PanicStatusRequest, PanicStatusRequestSchema} from "@/lib/spb-model"
import {
    Dialog,
    DialogClose,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog"
import {Button} from "@/components/ui/button"
import React, {useState} from "react"
import {Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form"
import {useForm} from "react-hook-form"
import {zodResolver} from "@hookform/resolvers/zod"
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import {Textarea} from "@/components/ui/textarea"
import {Spinner} from "@/components/ui/shadcn-io/spinner"
import {apiBaseUrl, apiPost, metadataAsString} from "@/lib/utils"

const NewPanicStatusDialog = ({panicStatus}: { panicStatus: PanicStatus }) => {
    const [open, setOpen] = React.useState(false)
    const [working, setWorking] = useState(false)

    const form = useForm<PanicStatusRequest>({
        resolver: zodResolver(PanicStatusRequestSchema),
        defaultValues: {
            status: panicStatus.status,
            metadata: metadataAsString(panicStatus.metadata),
        },
    })

    const onSubmit = (data: PanicStatusRequest) => {
        return new Promise<void>((resolve, reject) => {
            setWorking(true)

            const requestData = {
                status: panicStatus.status,
                metadata: data.metadata != null ? JSON.parse(data.metadata) : null,
            }

            apiPost(`${apiBaseUrl()}/api/v1/panic`, requestData)
                .then((res) => {
                    if (res.ok) {
                        setOpen(false)
                        resolve()
                    } else {
                        reject(res)
                    }
                })
                .catch(reject)
                .finally(() => setWorking(false))
        })
    }

    return <Dialog open={open} onOpenChange={setOpen}>
        <DialogTrigger asChild>
            <Button variant="outline" className="m-auto cursor-pointer shadow">Declare New Status</Button>
        </DialogTrigger>

        <DialogContent className="sm:max-w-[425px]">
            <DialogHeader>
                <DialogTitle>Declare New Status</DialogTitle>
                <DialogDescription>
                    This will set the current Panic Status as below.
                </DialogDescription>
            </DialogHeader>
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)} className="grid gap-4">
                    <div className="grid gap-3">
                        <FormField
                            control={form.control}
                            name="status"
                            render={({field}) => (
                                <FormItem>
                                    <FormLabel>Status</FormLabel>
                                    <Select onValueChange={field.onChange} defaultValue={field.value}
                                            disabled={working}>
                                        <FormControl>
                                            <SelectTrigger className="w-full">
                                                <SelectValue placeholder="Select the new Panic Status"/>
                                            </SelectTrigger>
                                        </FormControl>
                                        <SelectContent>
                                            <SelectGroup>
                                                <SelectLabel>All Good</SelectLabel>
                                                <SelectItem value="OK"
                                                            className="text-emerald-500 font-bold">OK</SelectItem>
                                            </SelectGroup>
                                            <SelectGroup>
                                                <SelectLabel>Not so Good :(</SelectLabel>
                                                <SelectItem value="WARNING"
                                                            className="text-orange-500 font-bold">Warning</SelectItem>
                                            </SelectGroup>
                                            <SelectGroup>
                                                <SelectLabel>This is fine...</SelectLabel>
                                                <SelectItem value="OUTAGE"
                                                            className="text-rose-500 font-bold">Outage</SelectItem>
                                                <SelectItem value="MAJOR_OUTAGE" className="text-red-500 font-bold">Major
                                                    Outage</SelectItem>
                                            </SelectGroup>
                                        </SelectContent>
                                    </Select>
                                    <FormDescription className="text-xs">
                                        It might take up to 3 seconds to have the new status reflected.
                                    </FormDescription>
                                    <FormMessage/>
                                </FormItem>
                            )}
                        />
                    </div>
                    <div className="grid gap-3">
                        <FormField
                            control={form.control}
                            name="metadata"
                            render={({field}) => (
                                <FormItem>
                                    <FormLabel>Metadata</FormLabel>
                                    <Textarea onChange={field.onChange} defaultValue={field.value} className="font-mono"
                                              disabled={working}/>
                                    <FormMessage/>
                                </FormItem>
                            )}
                        />
                    </div>
                    <DialogFooter>
                        <DialogClose asChild>
                            <Button variant="outline" className="cursor-pointer" disabled={working}>Cancel</Button>
                        </DialogClose>
                        <Button type="submit" className="cursor-pointer" disabled={working}>
                            {working ? <>Declare Status... <Spinner variant="ring"/></> : <>Declare Status</>}
                        </Button>
                    </DialogFooter>
                </form>
            </Form>
        </DialogContent>
    </Dialog>
}

export default NewPanicStatusDialog

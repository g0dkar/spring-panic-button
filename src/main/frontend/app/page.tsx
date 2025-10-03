"use client"

import React, {useEffect, useState} from "react"
import {Card, CardAction, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from "@/components/ui/card"
import {
    BundledLanguage,
    CodeBlock,
    CodeBlockBody,
    CodeBlockContent,
    CodeBlockCopyButton,
    CodeBlockFilename,
    CodeBlockFiles,
    CodeBlockHeader,
    CodeBlockItem,
} from "@/components/ui/shadcn-io/code-block"
import {Button} from "@/components/ui/button"
import {PanicStatus, PanicStatusesVisual} from "@/lib/spb-model"
import {Spinner} from "@/components/ui/shadcn-io/spinner"
import {usePanicStatus} from "@/lib/utils"

const Timestamp = ({timestamp}: { timestamp: string | undefined }) => {
    if (timestamp !== undefined) {
        const date = new Date(timestamp)
        return <time dateTime={timestamp}>
            <span>{date.toLocaleString()}</span>
            <span
                className="opacity-50 text-[.8em] block text-right">({Intl.DateTimeFormat().resolvedOptions().timeZone})</span>
        </time>
    } else {
        return <></>
    }
}

const LoadingCardContent = () =>
    <CardContent>
        <Spinner className="m-auto" size={64} variant="ring"/>
    </CardContent>

const PanicStatusCardContent = ({panicStatus}: { panicStatus: PanicStatus }) => {
    const [code, setCode] = useState([
        {
            language: "json",
            filename: "Metadata",
            code: `{"test": 123}`,
        },
    ])

    useEffect(() => {
        setCode([
            {
                language: "json",
                filename: "Metadata",
                code: JSON.stringify(panicStatus.metadata, null, 2),
            },
        ])
    }, [panicStatus])

    return <>
        <CardHeader>
            <CardTitle>Status: {panicStatus.status}</CardTitle>
            <CardDescription className="text-sm">Declared By: {panicStatus.declared_by}</CardDescription>
            <CardAction className="text-xs">Since: <Timestamp
                timestamp={panicStatus.timestamp}/></CardAction>
        </CardHeader>
        <CardContent>
            <CodeBlock data={code} defaultValue={code[0].language}>
                <CodeBlockHeader>
                    <CodeBlockFiles>
                        {(item) => (
                            <CodeBlockFilename key={item.language} value={item.language}>
                                {item.filename}
                            </CodeBlockFilename>
                        )}
                    </CodeBlockFiles>
                    <CodeBlockCopyButton/>
                </CodeBlockHeader>
                <CodeBlockBody>
                    {(item) => (
                        <CodeBlockItem key={item.language} value={item.language}>
                            <CodeBlockContent language={item.language as BundledLanguage}>
                                {item.code}
                            </CodeBlockContent>
                        </CodeBlockItem>
                    )}
                </CodeBlockBody>
            </CodeBlock>
        </CardContent>
        <CardFooter>
            <Button className="w-full cursor-pointer">Declare New Status</Button>
        </CardFooter>
    </>
}

const HomePage = () => {
    const {panicStatus, loading, error} = usePanicStatus("http://localhost:8080")

    return <section className="relative z-10 flex min-h-svh flex-row w-2xl m-auto">
        <div className="flex flex-1 flex-col p-2">
            <div
                className="w-full bg-neutral-50 border-neutral-100 rounded-lg shadow dark:border p-4 dark:bg-neutral-900 dark:border-neutral-800">
                <h1 className="text-3xl pb-4">Current Panic Status</h1>
                <Card className={PanicStatusesVisual[panicStatus?.status ?? "LOADING"]}>
                    {(loading || panicStatus == null) ? <LoadingCardContent/> :
                        <PanicStatusCardContent panicStatus={panicStatus}/>}
                </Card>

                <h2 className="text-xl pt-8 pb-4">Last 50 Panic Statuses</h2>
                <div>[loading={loading}, panicStatus={JSON.stringify(panicStatus)}]</div>
            </div>
        </div>
    </section>
}

export default HomePage

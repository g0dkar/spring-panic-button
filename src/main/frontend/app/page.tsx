"use client"

import React from "react"
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

const HomePage = () => {
    const code = [
        {
            language: "json",
            filename: "Metadata",
            code: `{"test": 123}`,
        },
    ]

    return <section className="relative z-10 flex min-h-svh flex-row w-2xl m-auto">
        <div className="flex flex-1 flex-col p-2">
            <div
                className="w-full bg-neutral-50 border-neutral-100 rounded-lg shadow dark:border p-4 dark:bg-neutral-900 dark:border-neutral-800">
                <h1 className="text-3xl pb-4">Current Panic Status</h1>
                <Card className="bg-emerald-50 dark:bg-emerald-950">
                    <CardHeader>
                        <CardTitle>Status: MAJOR_INCIDENT</CardTitle>
                        <CardDescription className="text-sm">Declared By: Panic Status Service</CardDescription>
                        <CardAction className="text-xs">Since: 2025-01-02 03:04:05 GMT</CardAction>
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
                                <CodeBlockCopyButton
                                    onCopy={() => console.log("Copied code to clipboard")}
                                    onError={() => console.error("Failed to copy code to clipboard")}
                                />
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
                </Card>

                <h2 className="text-xl pt-8 pb-4">All Panic Statuses</h2>
                <div>[list]</div>
            </div>
        </div>
    </section>
}

export default HomePage

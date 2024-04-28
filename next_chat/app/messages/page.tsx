'use client'
import {useEffect, useState} from "react";
import {Message} from "@/data/Message";
import {fetchMessages} from "@/data/fetch/fetchData";
import FloatingButton from "@/ui/button/floating";
import Link from "next/link";


export default function Page() {
    const [messages, setMessages] = useState<Message[]>([]);

    useEffect(() => {
        const loadMessages = async () => {
            try {
                const fetchedMessages = await fetchMessages();
                setMessages(fetchedMessages);
            } catch (error) {
                console.error('Failed to fetch messages:', error);
            }
        };

        loadMessages();
    }, []);
    return (
        <main className="p-8">
            <FloatingButton />
            <div className="flex items-center flex-row justify-between w-full px-16">
                <Link href="/" className="text-blue-600">Back</Link>
                <h1 className=" text-3xl font-bold">Your Messages</h1>
                <Link href="/account" className="text-blue-600">Account</Link>
            </div>
            <div className="flex flex-row w-full justify-center pb-32">
                <div className="flex flex-col w-[90vw]">
                    {
                        messages.length > 0 ? (
                            <ul>
                                {messages.map((msg, index) => (
                                    <li className="border-gray-200 border-2 rounded-2xl p-4 my-2 w-full" key={index}>
                                        <div className="font-bold mb-2">
                                            {msg.createdAt.toLocaleString()}
                                        </div>
                                        <div>
                                            {msg.content}
                                        </div>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p>No messages :(</p>
                        )
                    }

                </div>
            </div>
        </main>
    )
}
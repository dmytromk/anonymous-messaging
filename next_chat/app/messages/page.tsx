'use client'
import {useEffect, useState} from "react";
import {Message} from "@/data/Message";
import {fetchMessages} from "@/data/fetch/fetchData";
import FloatingButton from "@/ui/button/floating";


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
            <h1 className="flex items-center flex-col w-full text-3xl font-bold">Your Messages</h1>
            <div className="flex flex-row w-full justify-center">
                <div className="flex flex-col w-[90vw]">
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
                </div>
            </div>
        </main>
    )
}
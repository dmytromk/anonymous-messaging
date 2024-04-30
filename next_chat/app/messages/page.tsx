'use client'
import {useEffect, useState} from "react";
import {Message, User} from "@/data/defines";
import {fetchMessages, fetchUser} from "@/data/fetch/fetchData";
import FloatingButton from "@/ui/button/floating";
import Link from "next/link";


export default function Page() {
    const [messages, setMessages] = useState<Message[]>([]);
    const [user, setUser] = useState<User | null>(null)
    const [error, setError] = useState<String>("");
    const [error2, setError2] = useState<String>("");
    useEffect(() => {
        const loadMessages = async () => {
            try {
                const fetchedMessages = await fetchMessages();
                setMessages(fetchedMessages);
            } catch (error) {
                console.error('Failed to fetch messages:', error);
                setError("Error loading messages!")
            }
        };
        const loadUser = async () => {
            try {
                const fetchedUser = await fetchUser();
                setUser(fetchedUser);
            } catch (error) {
                console.error('Failed to fetch user:', error);
                setError2("Error loading user!")
            }
        }

        loadMessages();
        loadUser();
    }, []);
    if (error || error2) {
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
                        <p className="text-red-600" >{error}</p>
                        <p className="text-red-600" >{error2}</p>
                    </div>
                </div>
            </main>
        )
    }
    return (
        <main className="p-8">
            <FloatingButton />
            <div className="flex items-center flex-row justify-between w-full px-16">
                <Link href="/" className="text-blue-600">Back</Link>
                <div>
                    <h1 className=" text-3xl font-bold">Your Messages</h1>
                    <p>{user?.name}</p>
                </div>
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
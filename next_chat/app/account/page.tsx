'use client'
import {useState} from "react";
import Link from "next/link";

export default function Page() {

    const [username, setUsername] = useState('User');
    const handleLogout = async () => {
        const response = await fetch('/api/auth/logout');
        if (response.redirected) {
            window.location.href = response.url;
        }
    };

    return (
        <main className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Account Settings</h1>
            <p className="text-xl">Hello, {username}. What is on your mind?</p>
            <div>
                <button
                    onClick={handleLogout}
                    className="w-32 my-4 h-12 text-center text-white font-bold bg-red-500 hover:bg-red-600 rounded flex justify-center items-center">
                    Log out
                </button>
                <Link href="/messages">
                    <div className="w-32 my-4 h-12 text-center text-white font-bold bg-blue-500 hover:bg-blue-600 rounded flex justify-center items-center">
                        Back to messages
                    </div>
                </Link>
            </div>
        </main>
    );
}
'use client'
import {useState} from "react";
import Link from "next/link";

export default function Page() {

    const [registrationType, setRegistrationType] = useState('local');
    const [username, setUsername] = useState('User');

    return (
        <main className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Account Settings</h1>
            <p className="text-xl">Hello, {username}. What is on your mind?</p>
            <div>
                {registrationType === 'local' ? (
                    <button className="w-32 h-12 text-center text-white font-bold bg-blue-500 hover:bg-blue-600 rounded flex justify-center items-center my-4">
                        Change Password
                    </button>
                ) : (
                    <p className="my-4 text-lg h-12 flex items-center">
                        You logged in using {registrationType}
                    </p>
                )}
                <button className="w-32 my-4 h-12 text-center text-white font-bold bg-red-500 hover:bg-red-600 rounded flex justify-center items-center">
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
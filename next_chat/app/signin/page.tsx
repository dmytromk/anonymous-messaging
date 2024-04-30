'use client'
import Link from "next/link";
import Image from 'next/image';
import {signIn } from "next-auth/react";
export default function Page() {
    const handleGoogle = async () => {
        signIn('google')
    };

    const handleAnon = async () => {
        signIn('keycloak')
    }
    const handleIncognito = async () => {
        const response = await fetch('/api/auth/incognito');
        if (response.redirected) {
            window.location.href = response.url;
        }
    };

    return (
            <main className="flex flex-col w-full h-[80vh] items-center justify-center mx-auto p-4 ">
                <h1 className="text-3xl font-bold mb-4">Sign in</h1>
                <div className="flex flex-col">
                    <div className="flex flex-row">
                        <div className="w-16 h-16 border-2 border-black rounded m-4 p-2">
                            <button onClick={handleGoogle}>
                            <Image
                                src="logo-google.svg"
                                alt="Google"
                                width={100}
                                height={60}
                                layout="fixed"
                                className="bg-white"
                            />
                            </button>
                        </div>
                        <div className="w-16 h-16 border-2 border-black rounded m-4 p-2">
                            <button onClick={handleAnon}>
                                <Image
                                    src="/logo-anonmes.png"
                                    alt="Anonmes"
                                    width={100}
                                    height={60}
                                    layout="fixed"
                                    className="bg-white"
                                />
                            </button>
                        </div>
                    </div>
                    <div className="mx-4">
                        <button className="w-full h-12 border-2 border-black rounded flex justify-center items-center font-bold"
                        onClick={handleIncognito}>
                            Incognito Mode
                        </button>
                    </div>
                </div>
                <div className="pt-16 text-blue-500">
                    <Link href="/signup">
                        Sign up
                    </Link>
                </div>

            </main>
    );
}

'use client'
import {useState} from "react";
import Link from "next/link";
import Image from 'next/image';
export default function Page() {
    return (
        <main className="flex flex-col w-full h-[80vh] items-center justify-center mx-auto p-4 ">
            <h1 className="text-3xl font-bold mb-4">Sign up</h1>
            <div className="flex flex-col">
                <div className="flex flex-row">
                    <div className="w-16 h-16 border-2 border-black rounded m-4 p-2">
                        <Image
                            src="logo-google.svg"
                            alt="Google"
                            width={100}
                            height={60}
                            layout="fixed"
                            className="bg-white"
                        />
                    </div>
                    <div className="w-16 h-16 border-2 border-black rounded m-4 p-2">
                        <Image
                            src="/logo-anonmes.png"
                            alt="Google"
                            width={100}
                            height={60}
                            layout="fixed"
                            className="bg-white"
                        />
                    </div>
                </div>
                <div className="mx-4">
                <div className="w-full h-12 border-2 border-black rounded flex justify-center items-center font-bold">
                    Incognito Mode
                </div>
                </div>
            </div>
            <div className="pt-16 text-blue-500">
                <Link href="/signin">
                Sign in
                </Link>
            </div>

        </main>
    );
}
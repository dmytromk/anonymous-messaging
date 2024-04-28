'use client'
import {useState} from "react";
import Link from "next/link";
import Image from 'next/image';
import axios from "axios";
import Cookies from 'js-cookie';
export default function Page() {
    const handleGoogle = async () => {
        try {
            const response = await axios.get('http://localhost:8080/login/google');
            if (response.status === 200) {
                const jwt = response.data.jwt; // Adjust this path based on your actual response structure
                Cookies.set('annon_jwt', jwt, { expires: 7 }); // Cookie expires in 7 days
                console.log('JWT stored in cookies');
            }
        } catch (error) {
            console.error('Login failed:', error);
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
                        <Image
                            src="/logo-anonmes.png"
                            alt="Anonmes"
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
                <Link href="/signup">
                    Sign up
                </Link>
            </div>

        </main>
    );
}
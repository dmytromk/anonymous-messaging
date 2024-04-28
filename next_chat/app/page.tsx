import Image from "next/image";
import Link from "next/link";

export default function Home() {
  return (
    <main className="flex flex-col w-full h-[80vh] items-center justify-center">
        <div className="flex flex-col items-center">
            <Image
                src="/anonmes.png"
                alt="Logo"
                width={350}
                height={400}
                className="max-w-64 pb-4"
                layout="responsive"
            />
            <h2 className="text-2xl font-bold">Welcome to Anonmes!</h2>
            <p className="p-4">Anonymous chat, where you never know who is talking to you 🤫</p>
            <Link href="/messages">
                <div className="w-32 h-12 text-center text-white font-bold bg-blue-500 rounded flex flex-col justify-center">
                    Get started!
                </div>
            </Link>
        </div>
    </main>
  );
}

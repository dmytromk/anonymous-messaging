'use client'
import Image from 'next/image';
import { useRouter } from 'next/navigation';

const FloatingButton = () => {
    const router = useRouter();

    const navigateToCompose = () => {
        router.push('/messages/compose');
    };

    return (
        <button
            onClick={navigateToCompose}
            className="fixed bottom-10 right-10 w-16 h-16 rounded-full bg-blue-500 flex items-center justify-center shadow-lg
            hover:bg-blue-600 focus:outline-none"
            aria-label="Compose"
        >
            <Image src="/pencil.svg" alt="Compose" width={24} height={24} />
        </button>
    );
};

export default FloatingButton;
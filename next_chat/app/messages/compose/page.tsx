'use client'
import {SendMessage} from "@/data/Message";
import {FormEvent, useState} from "react";
import axios from "axios";

export default function Page() {
    const [toEmail, setToEmail] = useState('');
    const [content, setContent] = useState('');

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault();
        const messageData: SendMessage = {
            toEmail,
            content
        };

        try {
            const response = await axios.post('http://localhost:8080/messages/', messageData);
            console.log('Message sent:', response.data);
            // Optionally, clear the form or give user feedback
            setToEmail('');
            setContent('');
            alert('Message sent successfully!');
        } catch (error) {
            console.error('Failed to send message:', error);
            alert('Failed to send message.');
        }
    };

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4 text-center">Compose Message</h1>
            <form onSubmit={handleSubmit} className="max-w-md mx-auto">
                <div className="mb-4">
                    <label htmlFor="toEmail" className="block text-sm font-medium text-gray-700">To Email</label>
                    <input
                        type="email"
                        id="toEmail"
                        required
                        value={toEmail}
                        onChange={(e) => setToEmail(e.target.value)}
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                    />
                </div>
                <div className="mb-4">
                    <label htmlFor="content" className="block text-sm font-medium text-gray-700">Message Content</label>
                    <textarea
                        id="content"
                        required
                        rows={4}
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                    />
                </div>
                <button
                    type="submit"
                    className="w-full py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                    Send Message
                </button>
            </form>
        </div>
    );
}
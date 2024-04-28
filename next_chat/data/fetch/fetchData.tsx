import axios from 'axios';
import {Message} from "@/data/Message";

export async function fetchMessages(): Promise<Message[]> {
    if (1) {

        const msg1 = {
            id: 1,
            toId: 1,
            content: "Hello",
            createdAt: new Date(Date.now())
        }

        const msg2 = {
            id: 2,
            toId: 1,
            content: "You don't know me, but I know you",
            createdAt: new Date(Date.now())
        }

        const msg3 = {
            id: 3,
            toId: 1,
            content: "This is nice!",
            createdAt: new Date(Date.now())
        }
        return [msg1, msg2, msg3];

    }
    try {
        const response = await axios.get<Message[]>('http://localhost:8080/messages/1');
        const messages = response.data.map(msg => ({
            ...msg,
            createdAt: new Date(msg.createdAt)
        }));
        console.log('Data retrieved:', messages);
        return messages;
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
}
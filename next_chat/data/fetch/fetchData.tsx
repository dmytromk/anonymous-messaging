import axios from 'axios';
import {Message, User} from "@/data/defines";

export async function fetchMessages(): Promise<Message[]> {
    try {
        const response = await axios.get<Message[]>('http://localhost:8080/messages',
            { withCredentials: true });
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


export async function fetchUser(): Promise<User> {
    try {
        const response = await axios.get<User>('http://localhost:8080/users/current',
            { withCredentials: true });
        const user = {...response.data, createdAt: new Date(response.data.createdAt)}
        console.log('Data retrieved:', user);
        return user;
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
}
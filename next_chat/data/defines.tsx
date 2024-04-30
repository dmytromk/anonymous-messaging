export interface Message {
    id: number,
    toId: number,
    content: string,
    createdAt: Date
}

export interface SendMessage {
    toEmail: string,
    content: string
}

export interface User {
    id: number,
    name: string,
    email: string,
    createdAt: Date
}
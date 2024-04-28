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
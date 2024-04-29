import NextAuth from "next-auth"
import GoogleProvider from "next-auth/providers/google"


const googleClient = process.env.GOOGLE_CLIENT_ID || "ERR: not defined"
const googleSecret = process.env.GOOGLE_CLIENT_SECRET || "ERR: not defined"
export default NextAuth({
    providers: [
        GoogleProvider({
            clientId: googleClient,
            clientSecret: googleSecret
        })
    ],
    callbacks: {
        async jwt({ token, account }) {
            if (account) {
                token.accessToken = account.access_token;
            }
            return token;
        }
    },
    secret: process.env.ANON_SECRET,
});
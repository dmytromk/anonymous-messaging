import NextAuth from "next-auth"
import GoogleProvider from "next-auth/providers/google"


const googleClient = process.env.GOOGLE_CLIENT_ID!
const googleSecret = process.env.GOOGLE_CLIENT_SECRET!
export default NextAuth({
    providers: [
        GoogleProvider({
            clientId: googleClient,
            clientSecret: googleSecret
        })
    ],
    session: {
      strategy: 'jwt'
    },
    callbacks: {
        async jwt({ token, account, user }) {
            if (account && user) {
                token.accessToken = account.access_token;
                token.refreshToken = account.refresh_token!;
                token.user = {
                    id: user.id,
                    name: user.name,
                    email: user.email,
                    image: user.image
                };
            }
            console.log("Token:" + JSON.stringify(token))
            return token;
        },
    },

    cookies: {
        sessionToken: {
            name: `next-auth.session-token`,
            options: {
                httpOnly: true,
                sameSite: 'lax',
                path: '/',
                secure: process.env.NODE_ENV === 'production',
            }
        }
    },
    secret: process.env.NEXTAUTH_SECRET,
});
import NextAuth from "next-auth"
import GoogleProvider from "next-auth/providers/google"
import {NextApiRequest, NextApiResponse} from "next";
import {serialize} from "cookie";
const googleClient = process.env.GOOGLE_CLIENT_ID!
const googleSecret = process.env.GOOGLE_CLIENT_SECRET!

export default (req: NextApiRequest, res: NextApiResponse) => {
    return NextAuth(req, res, {
    providers: [
        GoogleProvider({
            clientId: googleClient,
            clientSecret: googleSecret
        }),
    ],
    session: {
      strategy: 'jwt'
    },
    callbacks: {
        async jwt({ token, account, user }) {
            if (account && user) {
                token.accessToken = account.access_token;
                token.refreshToken = account.refresh_token;
                token.user = {
                    id: user.id,
                    name: user.name,
                    email: user.email,
                    image: user.image
                };

                res.setHeader('Set-Cookie',
                    serialize('anon_access_token', String(account.access_token), {
                        httpOnly: true,
                        secure: process.env.NODE_ENV !== 'development',
                        maxAge: 30 * 24 * 60 * 60 * 1000, // 30 days in milliseconds
                        path: '/'
                    }));
                res.redirect('/messages');
            }

            return token;
        },
    },
    secret: process.env.NEXTAUTH_SECRET,
}
)};




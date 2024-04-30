import NextAuth, {Account, User} from "next-auth"
import GoogleProvider from "next-auth/providers/google"
import KeycloakProvider from "next-auth/providers/keycloak";
import {NextApiRequest, NextApiResponse} from "next";
import {serialize} from "cookie";
import jwt from 'jsonwebtoken'

const googleClient = process.env.GOOGLE_CLIENT_ID!
const googleSecret = process.env.GOOGLE_CLIENT_SECRET!

const keycloakClient = process.env.KEYCLOAK_CLIENT_ID!
const keycloakSecret = process.env.KEYCLOAK_SECRET!

const jwtSecret = process.env.JWT_SECRET!;

export default (req: NextApiRequest, res: NextApiResponse) => {
    return NextAuth(req, res, {
    providers: [
        GoogleProvider({
            clientId: googleClient,
            clientSecret: googleSecret,
            authorization: {
                params: {
                    scope: 'openid email profile',
                    access_type: 'offline',
                    prompt: 'consent'
                }
            }
        }),
        KeycloakProvider({
            clientId: keycloakClient,
            clientSecret: keycloakSecret,
            issuer: "http://localhost:8888/realms/anonmes",
            authorization: {
                params: {
                    access_type: 'offline',
                }
            }
        }),
    ],
    session: {
      strategy: 'jwt'
    },
    callbacks: {
        async jwt({ token}) {
            return token;
        },
    },
    secret: process.env.NEXTAUTH_SECRET,
    events: {
        signIn: async ({ user, account }) => {
            const customTokenStr = createToken(user, account)
            if (account && user) {
                res.setHeader('Set-Cookie', [
                        serialize('anon_access_token', customTokenStr, {
                        httpOnly: true,
                        secure: process.env.NODE_ENV !== 'development',
                        maxAge: 30 * 24 * 60 * 60 * 1000, // 30 days in milliseconds
                        path: '/'
                    }), serialize('anon_incognito', '', {
                        httpOnly: true,
                        secure: process.env.NODE_ENV !== 'development',
                        expires: new Date(0),
                        path: '/'
                    })]);

                res.redirect('/messages');
            }
        }
    }
}
)};

function createToken(user: User, account: Account | null) {
    if (!account) throw new Error("Account information is missing");
    let expTime = account.expires_at ? account.expires_at : Math.floor(Date.now() / 1000) + (30 * 24 * 60 * 60);
    const customToken = {
        sub: user.id,
        iss: "anon_chat",
        exp: expTime,
        payload: {
            provider: account.provider,
            sub: user.id,
            name: user.name || '',
            email: user.email || '',
            access_token: account.access_token,
            refresh_token: account.refresh_token
        }
    };
    return  jwt.sign(customToken, jwtSecret);
}


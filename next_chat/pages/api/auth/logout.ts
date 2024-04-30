import { NextApiRequest, NextApiResponse } from "next";
import { serialize } from 'cookie';

export default (req: NextApiRequest, res: NextApiResponse) => {
    res.setHeader('Set-Cookie', [
        serialize('anon_access_token', '', {
            httpOnly: true,
            secure: process.env.NODE_ENV !== 'development',
            expires: new Date(0),
            path: '/'
        }),
        serialize('anon_incognito', '', {
            httpOnly: true,
            secure: process.env.NODE_ENV !== 'development',
            expires: new Date(0),
            path: '/'
        })
    ]);

    res.redirect('/');
};
import {NextApiRequest, NextApiResponse} from "next";
import {serialize} from "cookie";

export default (req: NextApiRequest, res: NextApiResponse) => {
    res.setHeader('Set-Cookie', [
        serialize('anon_incognito', String(true), {
            httpOnly: true,
            secure: process.env.NODE_ENV !== 'development',
            maxAge:  24 * 60 * 60 * 1000, // 1 day
            path: '/'
        }),
    ]);

    res.redirect('/messages');
};
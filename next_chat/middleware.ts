import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

// List of protected routes
const protectedRoutes = ['/account', '/messages', "/messages/compose"];

export function middleware(req: NextRequest) {
    const { nextUrl: { pathname }, cookies } = req;

    // 1) Check if route is not protected
    if (!protectedRoutes.includes(pathname)) {
        return NextResponse.next();
    }

    // 2) Check for anon_incognito cookie
    if (cookies.has("anon_incognito")) {
        return NextResponse.next();
    }

    // 3) Check for anon_access_token and validate it
    if (cookies.has("anon_access_token")) {
        const cookie = cookies.get("anon_access_token");

        if (cookie) return validateToken(cookie.value).then(result => {
            if (result.isValid) {
                const response = NextResponse.next();
                // Migrate headers if any Set-Cookie headers are present
                migrateHeaders(result.headers, response);
                return response;
            } else {
                return NextResponse.redirect('http:localhost:3000/signin');
            }
        });
    }

    // 4) If no cookies are present that grant access, deny access
    return NextResponse.redirect('http:localhost:3000/signin');
}

async function validateToken(token: string) {
    try {
        const response = await fetch('http://localhost:8080/validate', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ token })
        });
        const data = await response.json();
        return {
            isValid: data && data.status !== "Token is invalid",
            headers: response.headers  // Pass headers for further processing
        };
    } catch (error) {
        console.error('Error validating token:', error);
        return { isValid: false, headers: new Headers() };
    }
}

function migrateHeaders(sourceHeaders: Headers, response: NextResponse) {
    const setCookie = sourceHeaders.get('Set-Cookie');
    if (setCookie) {
        response.headers.set('Set-Cookie', setCookie);
    }
}
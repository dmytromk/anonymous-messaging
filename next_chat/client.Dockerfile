# Stage 1: install dependencies
FROM node:18-alpine AS deps

WORKDIR /app

COPY package*.json ./

ARG NODE_ENV
ENV NODE_ENV $NODE_ENV

RUN npm install
# If prod then use this
# RUN npm ci --omit=dev

# Stage 2: build
FROM node:18-alpine AS builder

WORKDIR /app

COPY --from=deps /app/node_modules ./node_modules
COPY app ./app
COPY data ./data
COPY public ./public
COPY ui ./ui
COPY package.json next.config.mjs tsconfig.json postcss.config.js postcss.config.mjs tailwind.config.js ./

RUN npm run build

# Stage 3: run
FROM node:18-alpine

WORKDIR /app

COPY --from=builder /app/.next ./.next
COPY --from=builder /app/public ./public
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/package.json ./

ENTRYPOINT ["npm", "run", "start"]
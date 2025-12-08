# ---- Build stage ----
# Use Debian-based image (glibc) and install build tools so node-sass can compile on arm64
FROM node:16-bullseye AS build

WORKDIR /app

# Install deps first (better layer caching)
COPY package*.json ./
# Install build prerequisites for node-gyp/node-sass
RUN apt-get update && apt-get install -y --no-install-recommends \
    python3 make g++ \
  && rm -rf /var/lib/apt/lists/*
# Ensure npm uses python3
RUN npm config set python /usr/bin/python3
# Install node modules
RUN npm ci

# Copy source
COPY . .

# Optionally inject API_URL at build-time (used by dotenv-webpack)
ARG API_URL
RUN if [ -n "$API_URL" ]; then echo "API_URL=$API_URL" > .env; fi

# Build static assets with correct public path for root hosting
# Using local webpack v3 from node_modules
RUN ./node_modules/.bin/webpack --env.PUBLIC_PATH=/


# ---- Runtime stage ----
FROM nginx:1.25-alpine

# Clean default site
RUN rm -rf /usr/share/nginx/html/* /etc/nginx/conf.d/default.conf || true

# Copy built assets
COPY --from=build /app/e4l.frontend.docker/web/dist /usr/share/nginx/html

# Nginx config for SPA routing and long-term caching of assets
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]



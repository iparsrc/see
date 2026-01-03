#
# Multi-stage build: compile the React/webpack frontend with Node, serve via Nginx.
#
# Build:
#   docker build -t e4l-frontend ./frontend
# Run:
#   docker run --rm -p 8081:80 e4l-frontend
#

FROM node:22-bullseye AS build

WORKDIR /app

# Native deps for packages like node-sass (if it needs to compile)
RUN apt-get update \
  && apt-get install -y --no-install-recommends python3 make g++ \
  && rm -rf /var/lib/apt/lists/*

COPY package.json package-lock.json ./
RUN npm ci --legacy-peer-deps

COPY . .
# docker-compose passes `build.args.API_URL`; webpack uses dotenv-webpack (reads `.env`)
ARG API_URL
RUN if [ -n "${API_URL:-}" ]; then echo "API_URL=${API_URL}" > .env; fi
RUN npm run build


FROM nginx:1.25-alpine AS runtime

COPY nginx.conf /etc/nginx/conf.d/default.conf

# `webpack.config.js` outputs to: e4l.frontend.docker/web/dist
COPY --from=build /app/e4l.frontend.docker/web/dist/ /usr/share/nginx/html/

EXPOSE 80

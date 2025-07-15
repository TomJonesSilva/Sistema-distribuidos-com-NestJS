# Stage 1: Build all services
FROM node:20-alpine AS builder

# Set working directory inside the container
WORKDIR /app

# Copy root package.json and package-lock.json if they exist
# COPY package.json ./
# COPY package-lock.json ./

# Install root dependencies (if any)
# RUN npm install --silent --no-progress || echo "No root dependencies to install"

# Copy all project files
COPY . .

# --- Build API Gateway Service ---
WORKDIR /app/api-gateway
RUN npm ci --silent --no-progress
RUN npm run build
# Prune development dependencies for smaller production image
RUN npm prune --production

# --- Build Cardapio Service ---
WORKDIR /app/cardapio
RUN npm ci --silent --no-progress
RUN npm run build
# Prune development dependencies for smaller production image
RUN npm prune --production

# --- Build Cruds-Ticket Service ---
WORKDIR /app/cruds-ticket
RUN npm ci --silent --no-progress
RUN npm run build
# Prune development dependencies for smaller production image
RUN npm prune --production

# --- Build Cruds-Usuario Service ---
WORKDIR /app/cruds-usuario
RUN npm ci --silent --no-progress
RUN npm run build
# Prune development dependencies for smaller production image
RUN npm prune --production

# --- Build Cruds-Opcoes Service ---
WORKDIR /app/cruds-opcoes
RUN npm ci --silent --no-progress
RUN npm run build
# Prune development dependencies for smaller production image
RUN npm prune --production

# --- Build Relatorio Service ---
WORKDIR /app/relatorio
RUN npm ci --silent --no-progress
RUN npm run build
# Prune development dependencies for smaller production image
RUN npm prune --production

# Stage 2: Create production-ready images for each service
# This stage will be used by docker-compose to run individual services

# --- API Gateway Production Image ---
FROM node:20-alpine AS api-gateway-runner
WORKDIR /app/api-gateway
COPY --from=builder /app/api-gateway/dist ./dist
COPY --from=builder /app/api-gateway/node_modules ./node_modules
COPY --from=builder /app/api-gateway/package.json ./package.json
EXPOSE 3000
CMD ["npm", "run", "start:prod"]

# --- Cardapio Production Image ---
FROM node:20-alpine AS cardapio-runner
WORKDIR /app/cardapio
COPY --from=builder /app/cardapio/dist ./dist
COPY --from=builder /app/cardapio/node_modules ./node_modules
COPY --from=builder /app/cardapio/package.json ./package.json
EXPOSE 3001
CMD ["npm", "run", "start:prod"]

# --- Cruds-Ticket Production Image ---
FROM node:20-alpine AS cruds-ticket-runner
WORKDIR /app/cruds-ticket
COPY --from=builder /app/cruds-ticket/dist ./dist
COPY --from=builder /app/cruds-ticket/node_modules ./node_modules
COPY --from=builder /app/cruds-ticket/package.json ./package.json
EXPOSE 3002
CMD ["npm", "run", "start:prod"]

# --- Cruds-Usuario Production Image ---
FROM node:20-alpine AS cruds-usuario-runner
WORKDIR /app/cruds-usuario
COPY --from=builder /app/cruds-usuario/dist ./dist
COPY --from=builder /app/cruds-usuario/node_modules ./node_modules
COPY --from=builder /app/cruds-usuario/package.json ./package.json
EXPOSE 3003
CMD ["npm", "run", "start:prod"]

# --- Cruds-Opcoes Production Image ---
FROM node:20-alpine AS cruds-opcoes-runner
WORKDIR /app/cruds-opcoes
COPY --from=builder /app/cruds-opcoes/dist ./dist
COPY --from=builder /app/cruds-opcoes/node_modules ./node_modules
COPY --from=builder /app/cruds-opcoes/package.json ./package.json
EXPOSE 3004
CMD ["npm", "run", "start:prod"]

# --- Relatorio Production Image ---
FROM node:20-alpine AS relatorio-runner
WORKDIR /app/relatorio
COPY --from=builder /app/relatorio/dist ./dist
COPY --from=builder /app/relatorio/node_modules ./node_modules
COPY --from=builder /app/relatorio/package.json ./package.json
EXPOSE 3005
CMD ["npm", "run", "start:prod"]

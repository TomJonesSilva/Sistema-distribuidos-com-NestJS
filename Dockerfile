# Stage 1: Build all services
FROM node:18-alpine AS builder

# Set working directory inside the container
WORKDIR /app

# Copy root package.json and package-lock.json if they exist
# This is useful for monorepo tools like Lerna or Nx, or if you have shared dependencies at root
# If you don't have a root package.json, you can skip these two lines.
# COPY package.json ./
# COPY package-lock.json ./

# Install root dependencies (if any)
# RUN npm install --silent --no-progress || echo "No root dependencies to install"

# Copy all project files
COPY . .

# --- Build API Gateway Service ---
# Set working directory to the api-gateway service
WORKDIR /app/api-gateway
# Install dependencies for api-gateway service
RUN npm ci --silent --no-progress
# Build the api-gateway service
RUN npm run build

# --- Build Cardapio Service ---
# Set working directory to the cardapio service
WORKDIR /app/cardapio
# Install dependencies for cardapio service
RUN npm ci --silent --no-progress
# Build the cardapio service
RUN npm run build

# --- Build Cruds-Ticket Service ---
# Set working directory to the cruds-ticket service
WORKDIR /app/cruds-ticket
# Install dependencies for cruds-ticket service
RUN npm ci --silent --no-progress
# Build the cruds-ticket service
RUN npm run build

# --- Build Cruds-Usuario Service ---
# Set working directory to the cruds-usuario service
WORKDIR /app/cruds-usuario
# Install dependencies for cruds-usuario service
RUN npm ci --silent --no-progress
# Build the cruds-usuario service
RUN npm run build

# --- Build Cruds-Opcoes Service ---
# Set working directory to the cruds-opcoes service
WORKDIR /app/cruds-opcoes
# Install dependencies for cruds-opcoes service
RUN npm ci --silent --no-progress
# Build the cruds-opcoes service
RUN npm run build

# --- Build Relatorio Service ---
# Set working directory to the relatorio service
WORKDIR /app/relatorio
# Install dependencies for relatorio service
RUN npm ci --silent --no-progress
# Build the relatorio service
RUN npm run build

# Stage 2: Create production-ready images for each service
# This stage will be used by docker-compose to run individual services

# --- API Gateway Production Image ---
FROM node:18-alpine AS api-gateway-runner
WORKDIR /app/api-gateway
# Copy only the necessary build output and node_modules from the builder stage
COPY --from=builder /app/api-gateway/dist ./dist
COPY --from=builder /app/api-gateway/node_modules ./node_modules
COPY --from=builder /app/api-gateway/package.json ./package.json
EXPOSE 3000 
CMD ["npm", "run", "start:prod"]

# --- Cardapio Production Image ---
FROM node:18-alpine AS cardapio-runner
WORKDIR /app/cardapio
# Copy only the necessary build output and node_modules from the builder stage
COPY --from=builder /app/cardapio/dist ./dist
COPY --from=builder /app/cardapio/node_modules ./node_modules
COPY --from=builder /app/cardapio/package.json ./package.json
EXPOSE 3001 
CMD ["npm", "run", "start:prod"]

# --- Cruds-Ticket Production Image ---
FROM node:18-alpine AS cruds-ticket-runner
WORKDIR /app/cruds-ticket
# Copy only the necessary build output and node_modules from the builder stage
COPY --from=builder /app/cruds-ticket/dist ./dist
COPY --from=builder /app/cruds-ticket/node_modules ./node_modules
COPY --from=builder /app/cruds-ticket/package.json ./package.json
EXPOSE 3002 
CMD ["npm", "run", "start:prod"]

# --- Cruds-Usuario Production Image ---
FROM node:18-alpine AS cruds-usuario-runner
WORKDIR /app/cruds-usuario
# Copy only the necessary build output and node_modules from the builder stage
COPY --from=builder /app/cruds-usuario/dist ./dist
COPY --from=builder /app/cruds-usuario/node_modules ./node_modules
COPY --from=builder /app/cruds-usuario/package.json ./package.json
EXPOSE 3003 
CMD ["npm", "run", "start:prod"]

# --- Cruds-Opcoes Production Image ---
FROM node:18-alpine AS cruds-opcoes-runner
WORKDIR /app/cruds-opcoes
# Copy only the necessary build output and node_modules from the builder stage
COPY --from=builder /app/cruds-opcoes/dist ./dist
COPY --from=builder /app/cruds-opcoes/node_modules ./node_modules
COPY --from=builder /app/cruds-opcoes/package.json ./package.json
EXPOSE 3004 
CMD ["npm", "run", "start:prod"]

# --- Relatorio Production Image ---
FROM node:18-alpine AS relatorio-runner
WORKDIR /app/relatorio
# Copy only the necessary build output and node_modules from the builder stage
COPY --from=builder /app/relatorio/dist ./dist
COPY --from=builder /app/relatorio/node_modules ./node_modules
COPY --from=builder /app/relatorio/package.json ./package.json
EXPOSE 3005 
CMD ["npm", "run", "start:prod"]

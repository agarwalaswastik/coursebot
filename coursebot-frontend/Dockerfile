# Use a Node.js base image for building the frontend
FROM node:18-alpine

# Set the working directory
WORKDIR /frontend

# Copy the package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the frontend code
COPY . .

# Expose the port the frontend will run on
EXPOSE 5173

# Start Server
CMD ["npm", "run", "dev"]
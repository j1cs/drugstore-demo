FROM node:14-alpine as build
WORKDIR /usr/src/app
COPY package.json ./
RUN npm install
COPY . .
ENTRYPOINT ["npm", "run", "build"]

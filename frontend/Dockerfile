FROM node:14-alpine as build
WORKDIR /usr/src/app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build

FROM alpine:3
WORKDIR /usr/src/app
COPY --from=build /usr/src/app/build /usr/src/app/build
ENTRYPOINT ["uname", "-arm"]
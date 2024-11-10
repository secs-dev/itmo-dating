FROM node:alpine

WORKDIR /miniapp

COPY package*.json ./

RUN npm install

COPY dist ./dist

COPY vite.config.ts .

EXPOSE 443

CMD ["npm", "run", "preview"]
